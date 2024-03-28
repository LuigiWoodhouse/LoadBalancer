package com.learn.balanceload;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import com.learn.balanceload.handler.RequestHandler;
import com.sun.net.httpserver.HttpServer;

public class Main {
    private static List<String> backendServers = new ArrayList<>();
    private static int currentIndex = 0;

    public static void main(String[] args) throws IOException {

        backendServers.add("http://backend1:8080");
        backendServers.add("http://backend2:8080");

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RequestHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Hello, World!");
    }
}