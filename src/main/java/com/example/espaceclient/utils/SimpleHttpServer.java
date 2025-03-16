package com.example.espaceclient.utils;

import com.example.espaceclient.service.DonsService;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void startServer(DonsService donsService, APIContext apiContext) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/return", new ReturnHandler(donsService, apiContext));
        server.createContext("/cancel", new CancelHandler());
        server.setExecutor(null);
        server.start();
    }

    static class ReturnHandler implements HttpHandler {
        private final DonsService donsService;
        private final APIContext apiContext;

        public ReturnHandler(DonsService donsService, APIContext apiContext) {
            this.donsService = donsService;
            this.apiContext = apiContext;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String[] params = query.split("&");
            String paymentId = null;
            String payerId = null;

            for (String param : params) {
                String[] split = param.split("=");
                if (split[0].equals("paymentId")) {
                    paymentId = split[1];
                } else if (split[0].equals("PayerID")) {
                    payerId = split[1];
                }
            }

            String response;
            try {
                donsService.executePayPalPayment(paymentId, payerId, apiContext);
                response = "Payment successful";
            } catch (PayPalRESTException e) {
                e.printStackTrace();
                response = "Payment failed";
            }

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class CancelHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Payment cancelled";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}