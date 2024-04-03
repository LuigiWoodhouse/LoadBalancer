package com.learn.balanceload.balancer;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class LoadBalancer {

    public static void forwardRequest(String targetURL, HttpExchange exchange) throws IOException {

        //String response = "Hello, World!";

        // Set response headers
        //exchange.getResponseHeaders().set("Content-Type", "text/plain");
        //exchange.sendResponseHeaders(200, response.getBytes().length);

        URL url = new URL(targetURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(exchange.getRequestMethod());

        // Copy headers from the original request to the forwarded request
        exchange.getRequestHeaders().entrySet().forEach(entry -> {
            String key = entry.getKey();
            entry.getValue().forEach(value -> connection.setRequestProperty(key, value));
        });
        // Print the target URL
        System.out.println("Forwarding request to: " + targetURL);
        connection.setDoOutput(true);
        InputStream is = exchange.getRequestBody();
        OutputStream os = connection.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        is.close();
        // Print the response code received from the backend server
        System.out.println("Response code from backend: " + connection.getResponseCode());

        // Copy response from the backend server to the original response
        exchange.getResponseHeaders().putAll(connection.getHeaderFields());
        exchange.sendResponseHeaders(connection.getResponseCode(), connection.getContentLengthLong());
        OutputStream outputStream = exchange.getResponseBody();
        InputStream inputStream = connection.getInputStream();
        byte[] responseBuffer = new byte[1024];
        int length;
        while ((length = inputStream.read(responseBuffer)) != -1) {
            outputStream.write(responseBuffer, 0, length);
        }
        outputStream.close();
        inputStream.close();
        connection.disconnect();
        // Print when request processing is complete
        System.out.println("Request processed successfully.");

    }
}