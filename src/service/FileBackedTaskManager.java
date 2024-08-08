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

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager() {
        super();
        createResourcesTxt();
    }

    public void save() {
        if (file == null) createResourcesTxt();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Task task : tasksMap.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Epic epic : epicMap.values()) {
                writer.write(epic.toString());
                writer.newLine();
            }
            for (Subtask subtask : subTaskMap.values()) {
                writer.write(subtask.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении");
        }
    }

    public void createResourcesTxt() {
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
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file, StandardCharsets.UTF_8))) {
            String line;
            reader.readLine(); // Пропускает оглав
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] split = line.split(", ");

                    if (split.length < 5) continue;
                    Task.fromString(line, this);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        System.out.println("КОМАР!!!");
    }

    public void createTaskForSaved(Task task) {
        super.createTask(task);
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    public void createEpicForSaved(Epic epic) {
        super.createEpic(epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    public void createSubtaskForSaved(Subtask subtask) {
        super.createSubtask(subtask);
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
    public void updateEpicStatus(Epic epic) throws NullPointerException {
        super.updateEpicStatus(epic);
        save();
    }

}

