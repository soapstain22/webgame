package backend.sites;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import backend.MasterServer;
import backend.objects.User;

public class ProfilePage implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("ProfilePage.handle()");
        String response = Perma.header+Perma.style+(MasterServer.isLoggedIn(exchange) ? Perma.navbarLoggedIn : Perma.navbarLoggedOut);
        User user = MasterServer.getUsername(exchange.getRemoteAddress());
        if (user!=null) {
            response+="<profile>"+user.getPfp()+"<p> karma : "+user.getKarma()+"</profile>";
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    
}