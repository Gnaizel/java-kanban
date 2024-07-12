package service;

import java.util.List;

import model.Status;
import model.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {

    HistoryManager HManager;

    @BeforeEach
    void createHistoryManager() {
        HManager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        Task task = new Task(Status.NEW, "Name", "Description");
        HManager.add(task);
        final List<Task> history = HManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История пустая.");
    }

    @Test
    void getHistory() {
        TaskManager manager = new InMemoryTaskManager();
        manager.createTask(new Task(Status.NEW, "Name", "Description"));
        manager.getTaskById(1);
        assertNotNull(manager.getHistory(), "История ровна нулю </>");
        List<Task> history = manager.getHistory();
        assertEquals(history, manager.getHistory(), " что ?");
    }

    @Test
    void remove() {
        TaskManager manager = new InMemoryTaskManager();
        manager.createTask(new Task(Status.NEW, "Name", "Description"));
        manager.createTask(new Task(Status.NEW, "Name", "Description"));
        manager.createTask(new Task(Status.NEW, "Name", "Description"));
        manager.getTaskById(1);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        manager.getTaskById(3);
        System.out.println(manager.getHistory());
    }

}