package service;

import java.util.ArrayList;
import java.util.List;

import model.Status;
import model.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    HistoryManager HManager;

    @BeforeEach
    void createHistoryManager() {HManager = new InMemoryHistoryManager();}

    @Test
    void add() {
        Task task = new Task(Status.NEW, "Name", "Description");
        HManager.add(task);
        final List<Task> history = HManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void getHistory() {
        Task task = new Task(Status.NEW, "Name", "Description");
        TaskManager manager = new InMemoryTaskManager();
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(task);
        List<Task> tasksInHistory = new ArrayList<Task>();
        tasksInHistory.add(manager.getTaskById(1));

        assertEquals(tasks, tasksInHistory, "getHistory() - не робит");
    }
}