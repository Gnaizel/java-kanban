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
            for (Task task : tasks.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
                writer.newLine();
            }
            for (Subtask subtask : subTasks.values()) {
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
    public void clear() {
        super.clear();
    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();
        System.out.println("КОМАР!!!");
    }

    public void addForSaved(Task task) {
        super.add(task);
    }

    @Override
    public void add(Epic epic) {
        super.add(epic);
        save();
    }

    public void addForSaved(Epic epic) {
        super.add(epic);
    }

    @Override
    public void add(Subtask subtask) {
        super.add(subtask);
        save();
    }

    public void addForSaved(Subtask subtask) {
        super.add(subtask);
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void update(Task task) {
        super.update(task);
        save();
    }

    @Override
    public void update(Epic epic) {
        super.update(epic);
        save();
    }

    @Override
    public void update(Subtask subtask) {
        super.update(subtask);
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

