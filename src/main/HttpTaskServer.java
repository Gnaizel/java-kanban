package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.LocalDataTimeAdapter;
import model.Status;
import model.Task;
import model.DurationTypeAdapter;
import service.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    static final TaskManager taskManager = new FileBackedTaskManager();
    static GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(Duration.class, new DurationTypeAdapter())
            .registerTypeAdapter(LocalDataTimeAdapter.class, new LocalDataTimeAdapter())
            .setPrettyPrinting();
    static final Gson gson = gsonBuilder.create();

    public static void main(String[] args) {
        taskManager.createTask(new Task(Status.NEW, "Name2", "Description2", Duration.ZERO, LocalDateTime.now()));
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 1);
            server.createContext("/tasks", new TasksHandler());

            server.start();
            System.out.println("Server started");
        } catch (IOException e) {
            System.out.println("Ошибка HttpServer (main)");
        }
    }

    static class SubtaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

        }
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            String path = exchange.getRequestURI().getPath();
            int id;
            try {
                String[] pathParts = path.split("/");
                id = Integer.parseInt(pathParts[pathParts.length - 1]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
                id = -1;
            }

            String response;

            switch (exchange.getRequestMethod()) {
                case "GET" -> {
                    System.out.println("Обрабатываю GET");
                    if (id == -1) {
                        response = gson.toJson(taskManager.getAllTasks());
                        BaseHttpHandler.sendText(exchange, response);
                    } else {
                        Task task = taskManager.getTaskById(id);
                        if (task != null) {
                            System.out.println(gson.toJson(task));
//                            response = gson.toJson(task);
                            exchange.sendResponseHeaders(200, 0);
//                            BaseHttpHandler.sendText(exchange, response);
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "POST" -> {
                    System.out.println("Обрабатываю POST");
                    if (id == -1) {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Task task = gson.fromJson(taskJson, Task.class);
                            if (task == null) {
                                BaseHttpHandler.sendHasInteractions(exchange);
                                return;
                            }
                            taskManager.createTask(task);
                            exchange.sendResponseHeaders(201, 0);
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    } else {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Task task = gson.fromJson(taskJson, Task.class);
                            taskManager.updateTask(id, task);
                            exchange.sendResponseHeaders(201, 0);
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "DELETE" -> {
                    System.out.println("Обрабатываю DELETE");
                    if (id == -1) {
                        BaseHttpHandler.sendNotFound(exchange);
                    } else {
                        if (taskManager.getTaskById(id) != null) {
                            taskManager.deleteTask(taskManager.getTaskById(id));
                            BaseHttpHandler.sendText(exchange, "Задача " + id + " удалена.");
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                default -> BaseHttpHandler.sendText(exchange, "Неизвестный метод запроса");
            }
        }
    }
}
