package backend.sites;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import backend.MasterServer;
import backend.objects.Post;
import backend.objects.User;

public class PostsPage implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("ProfilePage.handle()");
        String response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn : Perma.navbarLoggedOut);
        for (int i = 0; i < MasterServer.getTotalposts().size(); i++) {
            if (MasterServer.getTotalposts().get(i) != null) {
                Post post = MasterServer.getTotalposts().get(i);
                System.out.println(post.getMessage());
                System.out.println(post.getSubject());
            response+="<div><post><p class=\"user\">"+post.getUsername().getUsername()+"</p><p class= subject>"+post.getSubject()+"</p><p class= msg>"+post.getMessage()+"</p><br><p class= date>"+post.getPostdate()+"</post></div>";

            }
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}