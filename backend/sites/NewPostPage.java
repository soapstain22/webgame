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

public class NewPostPage implements HttpHandler {
public void handle(HttpExchange exchange) throws IOException {
        System.out.println("PostsPage.handle()");
        String response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn + Perma.postForm: Perma.navbarLoggedOut);
        switch (exchange.getRequestMethod()) {
            case "POST":
                System.out.println("POST:");
                Map<String,String> formData = MasterServer.parseFormData(exchange);
                User username = MasterServer.getUsername(exchange.getRemoteAddress());
                System.out.println(formData.size());
                new Post(username, formData.get("subject"), formData.get("content"), null);

                break;
            case "GET":
                
                break;
            }
        /*if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
        }*/
        /*
        // Check content type for image
        String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
        if (contentType == null || !contentType.startsWith("image/")) {
            exchange.sendResponseHeaders(400, 0); // Bad Request
        } */

            //Map<String,String> formData = MasterServer.parseFormData(exchange);
            //User username = MasterServer.getUsername(exchange.getRemoteAddress());
            //new Post(username, formData.get("subject"), formData.get("content"), formData.get("file").getBytes());
        // Read image data from request body
        

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();        
        
    }
}