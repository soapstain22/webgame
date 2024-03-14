package backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


public class SimpleServer implements HttpHandler {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new SimpleServer());
        server.start();
        System.out.println("Server started at http://localhost:8080/");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            sendHtmlResponse(exchange);
        } else if (requestMethod.equals("POST")) {
            handlePostRequest(exchange);
        } else {
            exchange.sendResponseHeaders(405, 0); // Method Not Allowed
        }
        exchange.close();
    }

    private void sendHtmlResponse(HttpExchange exchange) throws IOException {
        String html = "<html><body>"
                + "<form method=\"post\" action=\".\">"
                + "<input type=\"text\" name=\"message\" />"
                + "<input type=\"text\" name=\"a\" />"
                + "<input type=\"text\" name=\"ss\" />"
                + "<button type=\"submit\">Send</button>"
                + "</form>"
                + "</body></html>";
        exchange.sendResponseHeaders(200, html.length());
        OutputStream os = exchange.getResponseBody();
        os.write(html.getBytes());
        os.close();
    }

    private void handlePostRequest(HttpExchange exchange) throws IOException {
        Map<String, String> params = parseFormData(exchange);
        String message = params.get("message");
        String aString = params.get("a");
        String shot = params.get("ss");
        // Process the received message (e.g., print it)
        System.out.println("Received message: " + message + aString + shot);
        String response = "Message received: " + message;
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> params = new HashMap<>();
        for (String param : requestBody.split("&")) {
            String[] keyValue = param.split("=");
            params.put(keyValue[0], keyValue[1]);
        }
        return params;
    }
}