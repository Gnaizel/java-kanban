package main;

import adapter.DurationAdapter;
import adapter.EpicAdapter;
import adapter.LocalDataTimeAdapter;
import adapter.SubtaskAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import service.FileBackedTaskManager;
import service.TaskManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class HttpTaskServer {
    static final TaskManager taskManager = new FileBackedTaskManager();
    private static final Logger logger = Logger.getLogger(HttpTaskServer.class.getName());
    static GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Duration.class, new DurationAdapter()).registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter()).registerTypeAdapter(Epic.class, new EpicAdapter()).registerTypeAdapter(Subtask.class, new SubtaskAdapter()).setPrettyPrinting();
    static final Gson gson = gsonBuilder.create();

    public static void main(String[] args) {
        taskManager.createTask(new Task(Status.NEW, "Name2", "Description2", Duration.ZERO, LocalDateTime.now()));
        taskManager.createEpic(new Epic("N", "D"));
        taskManager.createSubtask(new Subtask(Status.NEW, "Name", "D", taskManager.getEpicById(1), Duration.ZERO, LocalDateTime.now()));
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
            server.createContext("/tasks", new TasksHandler());
            server.createContext("/subtasks", new SubtaskHandler());
            server.createContext("/epics", new EpicHandler());
            server.createContext("/history", new HistoryHandler());
            server.createContext("/prioritized", new PrioritizedHandler());

            server.start();
            logger.info("Server started \n port: " + server.getAddress().getPort());
        } catch (IOException e) {
            logger.severe("Server failed to start");
        }
    }

    static class PrioritizedHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            if (t.getRequestMethod().equals("GET") && !taskManager.getPrioritizedTasks().isEmpty()) {
                String response = gson.toJson(taskManager.getPrioritizedTasks());
                BaseHttpHandler.sendText(t, response, 200);
            }
        }
    }

    static class HistoryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String response;
            int id = BaseHttpHandler.checkId(httpExchange);

            if (id == -1 || httpExchange.getRequestMethod().equals("GET")) {
                response = gson.toJson(taskManager.getHistory());
                BaseHttpHandler.sendText(httpExchange, response, 200);
            }
            logger.warning("Непонятный id для истории");
            BaseHttpHandler.sendNotFound(httpExchange);
        }
    }

    static class EpicHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response;
            int id = BaseHttpHandler.checkId(exchange);

            switch (exchange.getRequestMethod()) {
                case "GET" -> {
                    logger.info("Обработка GET");
                    if (id == -1) {
                        response = gson.toJson(taskManager.getAllEpic());
                        BaseHttpHandler.sendText(exchange, response, 200);
                    } else {
                        if (exchange.getRequestURI().toString().split("/")[3].equals("subtasks")) {
                            BaseHttpHandler.sendText(exchange, gson.toJson(taskManager.getEpicById(id).getSubTasks()), 200);
                        } else {
                            Epic task = taskManager.getEpicById(id);
                            if (task != null) {
                                response = gson.toJson(task, Epic.class);
                                BaseHttpHandler.sendText(exchange, response, 200);
                            } else {
                                BaseHttpHandler.sendText(exchange, "такого Epic нет", 404);
                                logger.warning("Неверный id Epic");
                            }
                        }
                    }
                }
                case "POST" -> {
                    logger.info("Обработка POST");
                    if (id == -1) {
                        try (InputStream is = exchange.getRequestBody()) {
                            Epic epic = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), Epic.class);
                            if (epic != null) {
                                taskManager.createEpic(epic);
                                BaseHttpHandler.sendText(exchange, 201);
                            }
                            BaseHttpHandler.sendText(exchange, "Неверный Epic", 404);
                        } catch (IOException e) {
                            logger.severe("Ошибка обработки POST");
                        }
                    } else {
                        try (InputStream is = exchange.getRequestBody()) {
                            Epic task = gson.fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), Epic.class);
                            if (task != null) {
                                taskManager.updateEpic(task.getId(), task);
                                BaseHttpHandler.sendText(exchange, 201);
                            }
                            BaseHttpHandler.sendText(exchange, 404);
                        } catch (IOException e) {
                            logger.severe("Ошибка обработки POST");
                        }
                    }
                }
                case "DELETE" -> {
                    logger.info("Обработка DELETE");
                    if (id == -1) {
                        BaseHttpHandler.sendText(exchange, "Введите id", 404);
                    } else {
                        if (taskManager.getEpicById(id) != null) {
                            taskManager.deleteEpic(taskManager.getEpicById(id));
                            BaseHttpHandler.sendText(exchange, 201);
                        } else {
                            BaseHttpHandler.sendText(exchange, "Такого Epic нет", 404);
                        }
                    }
                }
                default -> {
                    BaseHttpHandler.sendText(exchange, 405);
                    logger.info("Неизвестный метод");
                }
            }
        }
    }

    static class SubtaskHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            int id = BaseHttpHandler.checkId(exchange);
            String response;

            switch (exchange.getRequestMethod()) {
                case "GET" -> {
                    logger.info("Обработка GET");
                    if (id == -1) {
                        response = gson.toJson(taskManager.getAllSubtask());
                        BaseHttpHandler.sendText(exchange, response, 200);
                    } else {
                        Task task = taskManager.getSubtaskById(id);
                        if (task != null) {
                            response = gson.toJson(task);
                            BaseHttpHandler.sendText(exchange, response, 200);
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "POST" -> {
                    logger.info("Обработка POST");
                    if (id == -1) {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Subtask task = gson.fromJson(taskJson, Subtask.class);
                            if (task == null) {
                                BaseHttpHandler.sendHasInteractions(exchange);
                                return;
                            }
                            taskManager.createSubtask(task);
                            BaseHttpHandler.sendText(exchange, 201);// Сделать отдельный метод с 201
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    } else {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Subtask task = gson.fromJson(taskJson, Subtask.class);
                            taskManager.updateSubtask(id, task);
                            BaseHttpHandler.sendText(exchange, 201);
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "DELETE" -> {
                    logger.info("Обработка DELETE");
                    if (id == -1) {
                        BaseHttpHandler.sendNotFound(exchange);
                    } else {
                        if (taskManager.getSubtaskById(id) != null) {
                            taskManager.deleteSubtask(taskManager.getSubtaskById(id));
                            BaseHttpHandler.sendText(exchange, "Subtask id:" + id + " удален.", 200);
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                default -> BaseHttpHandler.sendText(exchange, "Неизвестный метод запроса", 404);
            }
        }
    }

    static class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            int id = BaseHttpHandler.checkId(exchange);
            String response;

            switch (exchange.getRequestMethod()) {
                case "GET" -> {
                    logger.info("Обработка GET");
                    CheckIp.check(exchange);
                    if (id == -1) {
                        response = gson.toJson(taskManager.getAllTasks());
                        BaseHttpHandler.sendText(exchange, response, 200);
                    } else {
                        Task task = taskManager.getTaskById(id);
                        if (task != null) {
                            response = gson.toJson(task);
                            BaseHttpHandler.sendText(exchange, response, 200);
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "POST" -> {
                    logger.info("Обработка POST");
                    if (id == -1) {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Task task = gson.fromJson(taskJson, Task.class);
                            if (task == null) {
                                BaseHttpHandler.sendHasInteractions(exchange);
                                return;
                            }
                            taskManager.createTask(task);
                            BaseHttpHandler.sendText(exchange, 201);// Сделать отдельный метод с 201
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    } else {
                        try (InputStream is = exchange.getRequestBody()) {
                            String taskJson = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                            Task task = gson.fromJson(taskJson, Task.class);
                            taskManager.updateTask(id, task);
                            BaseHttpHandler.sendText(exchange, 201);
                        } catch (IOException e) {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                case "DELETE" -> {
                    logger.info("Обработка DELETE");
                    if (id == -1) {
                        BaseHttpHandler.sendNotFound(exchange);
                    } else {
                        if (taskManager.getTaskById(id) != null) {
                            taskManager.deleteTask(taskManager.getTaskById(id));
                            BaseHttpHandler.sendText(exchange, "Задача " + id + " удалена.", 200);
                        } else {
                            BaseHttpHandler.sendNotFound(exchange);
                        }
                    }
                }
                default -> BaseHttpHandler.sendText(exchange, "Неизвестный метод запроса", 405);
            }
        }

        static class CheckIp {
            public static void check(HttpExchange exchange) throws IOException {
                HttpClient client = HttpClient.newHttpClient();
                String ip = exchange.getRemoteAddress().getAddress().getHostAddress();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://ipwhois.app/json/" + ip))
                        .GET()
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(response.body());
                } catch (Exception e) {
                    BaseHttpHandler.sendNotFound(exchange);
                }
            }
        }
    }
}