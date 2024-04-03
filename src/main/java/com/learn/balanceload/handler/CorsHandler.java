package com.learn.balanceload.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
public class CorsHandler implements HttpHandler {

    private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS";
    private static final String ALLOWED_HEADERS = "Content-Type, Authorization";
    private static final String ALLOWED_ORIGIN = "http://localhost:4200";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            handleOptionsRequest(exchange);
        } else {
            // Allow requests from localhost:4200
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
            headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);

            // Process the request as usual
            // You may want to forward the request to your request handler here
            exchange.sendResponseHeaders(200, -1); // Respond with 200 OK
        }
    }

    private void handleOptionsRequest(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getResponseHeaders();
        headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        exchange.sendResponseHeaders(204, -1); // Respond with 204 No Content
    }
}