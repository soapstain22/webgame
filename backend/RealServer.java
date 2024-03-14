package backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import backend.objects.Post;

public class RealServer implements HttpHandler {
    static HashMap<String, String> userpass;
    static Map<String, ArrayList<Post>> postlist;
    static HashMap<InetSocketAddress, String> sessionname;
    static String style;

    static {
        
        File f = new File("web/style.css");
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                style += s.nextLine();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        userpass = new HashMap<String, String>();
        userpass.put("superpizza", "test");
        postlist = new HashMap<String, ArrayList<Post>>();
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(1000), 0);
        server.createContext("/", new RealServer());
        server.createContext("/login", PostPage());
        server.start();
        System.out.println("Server started at http://localhost:2332/");

    }

    public static Post[] getPostlist(String user) {

        ArrayList<Post> arrayList = postlist.get(sessionname.get(user));
        Post[] list = new Post[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            list[i] = arrayList.get(i);
        }
        return list;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "mrow";
        
        System.out.println("handle:");
        switch (exchange.getRequestMethod()) {
            case "POST":
                System.out.println("post:");

                if (login(exchange)) {
                    System.out.println("login:");

                    response = "YUO ARE LOGGED IN AS";
                } else {
                    System.out.println("failed login:");

                    response = "mm.. no";
                }
                break;
            case "GET":
                System.out.println("get:");
                System.out.println(style);
                response = "<html lang=\"en\">\n" + //
                        "<head>\n" + //
                        "    <meta charset=\"UTF-8\">\n" + //
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
                        "    <title>scrimblo network</title>\n" + //
                        "<style>"+
                        style+
                        "</style>"+
                        "</head>\n" + //
                        "<body>\n" + //
                        "    <h1><img src=\"cooltext453500064312893.png\"></h1>\n" + //
                        "    <nav><a href=\"posts.html\"><li>postz</li></a>"
                        + "<a href=\"login.html\"><li>login</li></a><a href=\"profile.html\"><li>profile</li></a></nav>\n"
                        + //
                        "    <main>\n" + //
                        "    <form id=\"login-form\" method=\"post\" action=\"/login\">\n" + //
                        "        <label for=\"username\">Username:</label>\n" + //
                        "        <input type=\"text\" id=\"name\" name=\"username\" required>\n" + //
                        "        <br>\n" + //
                        "        <label for=\"password\">Password:</label>\n" + //
                        "        <input type=\"password\" id=\"pass\" name=\"password\" required>\n" + //
                        "        <br>\n" + //
                        "        <button type=\"submit\">Login</button>\n" + //
                        "    </form>\n" + //
                        "    <div id=\"results\"></div> <script src=\"script.js\"></script>\n" + //
                        "      </main>\n" + //
                        "      <footer> kys!</footer>\n" + //
                        "</body>\n" + //
                        "</html>";
                break;
            default:
                System.out.println("error:");

                break;
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    boolean login(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> params = new HashMap<>();
        for (String param : requestBody.split("&")) {
            String[] keyValue = param.split("=");
            params.put(keyValue[0], keyValue[1]);
        }
        String name = params.get("username");
        String pass = params.get("password");
        System.err.println("userpass:" + userpass.get(name));
        System.err.println("name:" + name);
        System.err.println("pass:" + pass);
        if (userpass.get(name).compareTo(pass) == 0) {
            //sessionname.put(exchange.getRemoteAddress(), params.get("username"));
            System.out.println("login function: true");
            sessionname.put(exchange.getRemoteAddress(), name);
            return true;
        }
        ;
        System.out.println("login function: false");
        return false;
    }
    static HttpHandler PostPage() throws IOException{
        HttpHandler handlor = new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "<html lang=\"en\">\n" + //
                "<head>\n" + //
                "    <meta charset=\"UTF-8\">\n" + //
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + //
                "    <title>scrimblo network</title>\n" + //
                "<style>"+
                style+
                "</style>"+
                "</head>\n" + //
                "<body>\n" + //
                "    <h1><img src=\"cooltext453500064312893.png\"></h1>\n" + //
                "    <nav><a href=\"posts.html\"><li>postz</li></a>"
                + "<a href=\"login.html\"><li>login</li></a><a href=\"profile.html\"><li>profile</li></a></nav>\n"
                + //
                "    <main>\n" + //
                "    <form id=\"login-form\" method=\"post\" action=\"/login\">\n" + //
                "        <label for=\"username\">Username:</label>\n" + //
                "        <input type=\"text\" id=\"name\" name=\"username\" required>\n" + //
                "        <br>\n" + //
                "        <label for=\"password\">Password:</label>\n" + //
                "        <input type=\"password\" id=\"pass\" name=\"password\" required>\n" + //
                "        <br>\n" + //
                "        <button type=\"submit\">Login</button>\n" + //
                "    </form>\n" + //
                "    <div id=\"results\"></div> <script src=\"script.js\"></script>\n" + //
                "      </main>\n" + //
                "      <footer> kys!</footer>\n" + //
                "</body>\n" + //
                "</html>";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();    
            }
        };
        return handlor;
    }
}