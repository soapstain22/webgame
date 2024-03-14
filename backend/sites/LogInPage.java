package backend.sites;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import backend.MasterServer;

public class LogInPage implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("LogInPage.handle()");
        String response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn +"<p> logged in </p>" : Perma.navbarLoggedOut + Perma.loginForum);
        //(exchange.getRequestMethod() =="POST" ? true : Perma.loginForum);
        //(MasterServer.isLoggedIn(exchange) ? Perma.navbar : Perma.navbarLogin)
        switch (exchange.getRequestMethod()) {
            case "POST":
                System.out.println("POST:");
                if (MasterServer.login(exchange)) {
                    response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn +"<p> logged in </p>" : Perma.navbarLoggedIn + Perma.loginForum);
                }
                break;
            case "GET":
                
                break;
                
        }
        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
    }
}