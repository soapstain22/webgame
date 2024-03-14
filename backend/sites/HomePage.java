package backend.sites;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import backend.MasterServer;

public class HomePage implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn : Perma.navbarLoggedOut + Perma.loginForum);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        
        
    }
}
