package com.learn.balanceload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import com.learn.balanceload.handler.CorsHandler;
import com.learn.balanceload.handler.RequestHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {
    private static List<String> backendServers = new ArrayList<>();
    private static int currentIndex = 0;

    public static void main(String[] args) throws IOException {

        backendServers.add("http://localhost:8080");
        backendServers.add("http://localhost:8081");

        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);
        // Register CorsHandler to handle CORS-related requests
        server.createContext("/", new CorsHandler());

        // Register RequestHandler to handle actual requests
        server.createContext("/api", new RequestHandler());

        // Set a default executor
        server.setExecutor(null);

        // Start the server
        server.start();

        System.out.println("Load balancer is running on port 8082");
    }
}