package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    public final Map<Integer, Task> tasks = new HashMap<>();
    public final Map<Integer, Epic> epics = new HashMap<>();
    public final Map<Integer, Subtask> subTasks = new HashMap<>();
    private final TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparing(Task::getID));
    private final InMemoryHistoryManager history = new InMemoryHistoryManager();

    @Override
    public void add(Task task) {
        validateTask(task);
        tasks.put(task.getID(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void add(Epic epic) {
        validateEpic(epic);
        epics.put(epic.getID(), epic);
        prioritizedTasks.add(epic);
    }

    @Override
    public void add(Subtask subtask) {
        validateSubtask(subtask);
        subTasks.put(subtask.getID(), subtask);
        prioritizedTasks.add(subtask);
    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subTasks.get(id);
    }

    @Override
    public void update(Task task) {
        validateTask(task);
        tasks.put(task.getID(), task);
        prioritizedTasks.remove(task); // Удаляем старую задачу
        prioritizedTasks.add(task); // Добавляем обновленную задачу
    }

    @Override
    public void update(Epic epic) {
        validateEpic(epic);
        epics.put(epic.getID(), epic);
        prioritizedTasks.remove(epic); // Удаляем старый эпик
        prioritizedTasks.add(epic); // Добавляем обновленный эпик
    }

    @Override
    public void update(Subtask subtask) {
        validateSubtask(subtask);
        subTasks.put(subtask.getID(), subtask);
        prioritizedTasks.remove(subtask); // Удаляем старую подзадачу
        prioritizedTasks.add(subtask); // Добавляем обновленную подзадачу
    }

    @Override
    public List<Subtask> getAllSubtasksForEpic(int id) {
        return List.of();
    }

    @Override
    public void setSubTaskStatus(Subtask subtask, Status status) {

    }

    @Override
    public void updateEpicStatus(Epic epic) {

    }

    @Override
    public void deleteTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            tasks.remove(id);
            prioritizedTasks.remove(task);
            return;
        }
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subTasks.get(id);
        if (subtask != null) {
            subTasks.remove(id);
            prioritizedTasks.remove(subtask);
        }
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            epics.remove(id);
            prioritizedTasks.remove(epic);
            return;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Subtask> getSubtasksForEpic(int epicId) {
        return subTasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }
    
    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    private void validateTask(Task task) {
        validateTimeOverlap(task);
    }

    private void validateEpic(Epic epic) {
        List<Subtask> subtasksForEpic = getSubtasksForEpic(epic.getID());
        LocalDateTime startTime = subtasksForEpic.stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .min(Comparator.comparing(Subtask::getStartTime))
                .map(Subtask::getStartTime)
                .orElse(null);

        LocalDateTime endTime = subtasksForEpic.stream()
                .filter(subtask -> subtask.getEndTime() != null)
                .max(Comparator.comparing(Subtask::getEndTime))
                .map(Subtask::getEndTime)
                .orElse(null);

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);

        epic.setDuration(subtasksForEpic.stream()
                .map(Subtask::getDuration)
                .reduce(Duration.ZERO, Duration::plus));

        validateTimeOverlap(epic);
    }

    private void validateSubtask(Subtask subtask) {
        validateTimeOverlap(subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.setStartTime(LocalDateTime.MIN);
            epic.setEndTime(LocalDateTime.MAX);
            epic.setDuration(Duration.ZERO);
            validateEpic(epic);
        }
    }

    private void validateTimeOverlap(Task task) {
        if (task.getStartTime() == null) {
            return;
        }
        boolean overlaps = prioritizedTasks.stream()
                .filter(otherTask -> otherTask != task)
                .anyMatch(otherTask -> timeOverlap(task.getStartTime(), task.getEndTime(),
                        otherTask.getStartTime(), otherTask.getEndTime()));
        if (overlaps) {
            throw new IllegalArgumentException("New task overlaps with existing task.");
        }
    }

    public Task getTaskForCount(int count) { // для тестов
        return tasks.get(count);
    }

    public Epic getEpicForCount(int count) { // для тестов
        return epics.get(count);
    }

    public Subtask getSubtaskForCount(int count) { // для тестов
        return subTasks.get(count);
    }

    private boolean timeOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return (start1.isBefore(end2) || start1.isEqual(end2)) && (end1.isAfter(start2) || end1.isEqual(start2));
    }

    @Override
    public void clear() {
        tasks.clear();
        epics.clear();
        subTasks.clear();
        prioritizedTasks.clear();
    }
}
