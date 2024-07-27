package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager {// Я НЕ ПОНИМАЮ ЧТО ДЕЛАТЬ НИЧЕГО НЕ РАБОТАЕТ
    private File file;

    public FileBackedTaskManager() {
        super();
        createDirectory();
    }

    public void save() {
        if (file == null) createDirectory();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Task task : tasksMap.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Epic task : epicMap.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Subtask task : subTaskMap.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении");
        }
    }

    public void createDirectory() {
        try {
            Path currentPath = Paths.get("").toAbsolutePath();
            Path filePath = currentPath.resolve("resources.txt");
            Files.createDirectories(filePath.getParent());

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Файл resources.txt создан в директории: " + currentPath);
                this.file = filePath.toFile();
            } else {
                this.file = filePath.toFile();
                unpackFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании файла: " + e.getMessage());
        }
    }

    public void unpackFile() {
        if (file == null) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file, StandardCharsets.UTF_8))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] split = line.split(", ");

                if (split.length < 5) {
                    continue;
                }

                String type = split[1];
                switch (type) {
                    case "TASK":
                        tasksMap.put(getIdTaskForLine(line), Task.fromString(line));
                        break;
                    case "SUBTASK":
                            subTaskMap.put(getIdTaskForLine(line), Subtask.fromString(line, epicMap));
                        break;
                    case "EPIC":
                        epicMap.put(getIdTaskForLine(line), Epic.fromString(line));
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdTaskForLine(String line) {
        String[] split = line.split(", ");
        return Integer.parseInt(split[0]);
    }

    @Override
    public void clearAll() {
        deleteAllTasks();
        deleteAllEpics();
        deleteAllSubtasks();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTask(Task task) {
        super.deleteTask(task);
        save();
    }

    @Override
    public void deleteEpic(Epic epic) {
        super.deleteEpic(epic);
        save();
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        super.deleteSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        super.updateEpic(updatedEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        super.updateSubtask(updatedSubtask);
        save();
    }

    @Override
    public void setSubTaskStatus(Subtask subtask, Status status) {
        super.setSubTaskStatus(subtask, status);
        save();
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        super.updateEpicStatus(epic);
        save();
    }

}

