package main;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    static void sendText(HttpExchange h, String text) throws IOException {
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Ну пиздец");
        }
    }

    static void sendNotFound(HttpExchange h) throws IOException {
        h.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        h.sendResponseHeaders(404, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write("Not Found".getBytes(StandardCharsets.UTF_8));
        }
    }

    static void sendHasInteractions(HttpExchange h) throws IOException {
        h.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        h.sendResponseHeaders(406, 0);
        try (OutputStream os = h.getResponseBody()) {
            os.write("Задачи пеерсекаются".getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void sendBadRequest(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(400, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write("Некорректный запрос".getBytes(StandardCharsets.UTF_8));
        }
    }
}
