package com.learn.balanceload;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.learn.balanceload.handler.CorsHandler;
import com.learn.balanceload.handler.RequestHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {

    public static void main(String[] args) throws IOException {

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