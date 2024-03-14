package backend;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.HashMap; // For storing user credentials
import java.util.Map;

public class GhettoServer implements HttpHandler {

    private static final Map<String, String> users = new HashMap<>(); // In-memory user storage

    static {
        // Add some initial user accounts for testing
        users.put("username1", "password1");
        users.put("username2", "password2");
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        server.createContext("/", new GhettoServer());
        server.start();
        System.out.println("Server started at http://localhost:8081/");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("GhettoServer.handle()");
        if (exchange.getRequestMethod().equals("POST")) {
            // Handle login form submission
            handleLogin(exchange);
        } else {
            // Handle GET request (display login form or main page based on authentication)
            String response = generateResponse(exchange);
            sendResponse(exchange, response);
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        System.out.println("GhettoServer.handleLogin()");
        // Parse form data
        // ... (extract username and password)
        exchange.getRequestBody();
        // Authenticate user
        boolean authenticated = authenticate("username", "password");

        // Send response based on authentication status
        String response = authenticated ? "<h1>Login successful!</h1>" : "<h1>Login failed!</h1>";
        sendResponse(exchange, response);
    }

    private boolean authenticate(String username, String password) {
        System.out.println("GhettoServer.authenticate()");
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private String generateResponse(HttpExchange exchange) {
        // Check if user is authenticated
        boolean authenticated = isAuthenticated(exchange);

        String response = "";
        if (authenticated) {
            // Display the main page with protected content
            response = createMainPageContent();
        } else {
            // Display the login form
            response = createLoginForm();
        }
        return response;
    }

    private boolean isAuthenticated(HttpExchange exchange) {
        // Check for authentication information (e.g., in cookies or session)
        // ...
        return false; // Replace with actual authentication check
    }

    private String createLoginForm() {
        // Generate HTML for the login form
        return "<html><body>" +
                "<h1>Login</h1>" +
                "<form method=\"post\" action=\".\">" +
                "<input type=\"text\" name=\"username\" />" +
                "<input type=\"text\" name=\"password\" />" +
                "<button type=\"submit\">Send</button>" +
                "</form>" +
                "</body></html>";
    }

    private String createMainPageContent() {
        // Generate HTML for the main page
        return "<html><body>" +
                "<h1>Welcome to the protected area!</h1>" +
                // ... protected content
                "</body></html>";
    }

    private void sendResponse(HttpExchange exchange, String response) throws IOException {
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
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