package com.learn.balanceload.handler;

import com.learn.balanceload.balancer.LoadBalancer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
public class RequestHandler implements HttpHandler {

    private static List<String> backendServers = new ArrayList<>();
    private static int currentIndex = 0;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        backendServers.add("http://localhost:8080");
        backendServers.add("http://localhost:8081");

        String response = "My Name is Captain Jeppo";

        // Set response headers
        exchange.getResponseHeaders().set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, response.getBytes().length);

        // Get output stream and write response
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

        String requestURI = exchange.getRequestURI().toString();
        String targetServer = getNextServer();
        String targetURL = targetServer + requestURI;

        // Print the target URL before forwarding the request
        System.out.println("Forwarding request to backend server: " + targetURL);

        // Forward request to backend server
        LoadBalancer.forwardRequest(targetURL, exchange);
    }
    private static synchronized String getNextServer() {
        //fetch instances of the service
        String server = backendServers.get(currentIndex);

        //go to next server based on the index in the list
        currentIndex = (currentIndex + 1) % backendServers.size();

        // Print the selected backend server
        System.out.println("Selected backend server: " + server);

        return server;
    }
}