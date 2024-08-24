package main;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {
    static void sendText(HttpExchange h, String text, int code) throws IOException {
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, 0);
        if (text == null || text.isEmpty() || text.isBlank()) {
            h.sendResponseHeaders(400, 0);
            h.getResponseBody().close();
        }
        try (OutputStream os = h.getResponseBody()) {
            os.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Ну пиздец");
        }
    }

    static int checkId(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        int id;
        try {
            String[] pathParts = path.split("/");
            id = Integer.parseInt(pathParts[2]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            id = -1;
        }
        return id;
    }

    static void sendText(HttpExchange h, int code) throws IOException {
        h.sendResponseHeaders(code, 0);
        try (OutputStream os = h.getResponseBody()) {}
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
