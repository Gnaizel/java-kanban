package service;

import model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDTest {

    TaskManager manager;

    @BeforeEach
    void createManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void addNewTaskForIdTest() {
        manager.add(new Task(Status.NEW, "Test addNewTask", "Test addNewTask description", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.IN_PROGRESS, "Test addNewTask1", "Test addNewTask description1", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.DONE, "Test addNewTask2", "Test addNewTask description2", Duration.ZERO, LocalDateTime.now()));

        List<Task> tasks;
        tasks = manager.getAllTasks();

        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTask(1), tasks.get(0), "Задачи не совпадают.");
        assertEquals(manager.getTask(3), tasks.get(2), "Задачи не совпадают.");
    }

    @Test
    void checkID() {
        TaskManager manager = new InMemoryTaskManager();
        manager.add(new Task(Status.NEW, "Test checkID", "Test checkID description", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.IN_PROGRESS, "Test checkID1", "Test checkID description1", Duration.ZERO, LocalDateTime.now()));
        assertNull(manager.getTask(3));

        manager.add(new Task(Status.DONE, "Test checkID2", "Test checkID description2", Duration.ZERO, LocalDateTime.now()));
        assertNotNull(manager.getTask(3));
    }

}