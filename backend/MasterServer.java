package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;

import java.util.Scanner;

import backend.objects.Post;
import backend.objects.User;
import backend.sites.HomePage;
import backend.sites.LogInPage;
import backend.sites.LogoutPage;
import backend.sites.NewPostPage;
import backend.sites.PostsPage;
import backend.sites.ProfilePage;
import backend.sites.RegisterPage;

public class MasterServer {
    static HashMap<String, String> userpass;
    static Map<String, ArrayList<Post>> postlist;
    static HashMap<InetSocketAddress, String> sessionname;
    static HashMap<String, User> users;
    static ArrayList<Post> totalposts;
    static {
        userpass = new HashMap<String, String>();
        userpass.put("superpizza", "test");
        postlist = new HashMap<String, ArrayList<Post>>();
        sessionname = new HashMap<>();
        users = new HashMap<>();
        totalposts = new ArrayList<>();
        byte[] chinese = new byte[3];
        new Post(new User("fuck", "you"), "test", "test", chinese);

    }

    public static ArrayList<Post> getTotalposts() {
        return totalposts;
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);

        server.createContext("/home", new HomePage());
        server.createContext("/logout", new LogoutPage());
        server.createContext("/login", new LogInPage());
        server.createContext("/posts", new PostsPage());
        server.createContext("/create", new NewPostPage());
        server.createContext("/register", new RegisterPage());
        server.createContext("/profile", new ProfilePage());
        server.start();
    }

    public static boolean login(HttpExchange exchange) throws IOException {
        System.out.println("MasterServer.login()");
        Map<String, String> params = parseFormData(exchange);
        String name = params.get("username");
        String pass = params.get("password");
        System.out.println("login function: false");
        return login(name, pass, exchange.getRemoteAddress());
    }

    public static boolean login(String name, String pass, InetSocketAddress address) throws IOException {
        System.out.println("MasterServer.login()");
        if (userpass.get(name).compareTo(pass) == 0) {
            sessionname.put(address, name);
            System.out.println("LOGGED IN");
            return true;
        }
        System.out.println("NOT LOGGED IN");

        return false;
    }

    public static boolean register(HttpExchange exchange) throws IOException {
        System.out.println("MasterServer.register()");
        Map<String, String> params = parseFormData(exchange);
        String name = params.get("username");
        String pass = params.get("password");
        String pass2 = params.get("password2");
        if (!pass.equals(pass2)) {
            return false;
        }
        if (userpass.get(name) == null) {
            System.out.println("registering");
            // sessionname.put(exchange.getRemoteAddress(), params.get("username"));
            new User(name, pass);
            InetSocketAddress remoteAddress = exchange.getRemoteAddress();
            System.out.println(remoteAddress);

            return login(name, pass, remoteAddress);

        }
        return false;
    }

    public static boolean logout(HttpExchange exchange) throws IOException {
        System.out.println("MasterServer.logout()");
        sessionname.remove(exchange.getRemoteAddress());
        return false;
    }

    public static boolean isLoggedIn(HttpExchange exchange) throws IOException {
        System.out.println("MasterServer.isLoggedIn()");
        System.err.println();
        if (sessionname.get(exchange.getRemoteAddress()) != null) {
            return true;
        }
        return false;
    }

    public static Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        System.out.println("MasterServer.parseFormData()");
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> params = new HashMap<>();
        for (String param : requestBody.split("&")) {
            String[] keyValue = param.split("=");
            System.err.println(keyValue.length);
            System.out.println(param);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);

            }

            // Check if split resulted in more than one element (key-value pair)
        }
        return params;
    }

    public static User getUsername(InetSocketAddress inetSocketAddress) {
        System.out.println("MasterServer.getUsername()");
        return users.get(sessionname.get(inetSocketAddress));
    }

    public static boolean hasUser(String name) {
        System.out.println("MasterServer.hasUser(" + name + ")");
        if (userpass.get(name) != null) {
            return true;
        } else
            return false;
    }

    public static void Update(User u) {
        String tname = u.getUsername();
        if (userpass.get(tname) != u.getPassword()) {
            userpass.put(tname, u.getPassword());
        }
        if (!users.containsValue(u)) {
            users.put(tname, u);
        }
        postlist.put(tname,u.getPostArray());
    }

    public static void Update(Post post) {
        System.out.println("MasterServer.Update()");
        if(!totalposts.contains(post)){
            totalposts.add(post);
            System.out.println(totalposts.size()+ "total posts");

            System.out.println(post + "updated");
        }
        // TODO Auto-generated method stub
    }
}