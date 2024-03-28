package com.learn.balanceload.handler;

import com.learn.balanceload.balancer.LoadBalancer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class RequestHandler implements HttpHandler {

    private static List<String> backendServers = new ArrayList<>();
    private static int currentIndex = 0;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestURI = exchange.getRequestURI().toString();
        String targetServer = getNextServer();
        String targetURL = targetServer + requestURI;

        // Forward request to backend server
        LoadBalancer.forwardRequest(targetURL, exchange);
    }
    private static synchronized String getNextServer() {
        String server = backendServers.get(currentIndex);
        currentIndex = (currentIndex + 1) % backendServers.size();
        return server;
    }
}