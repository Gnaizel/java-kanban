package service;

import model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class TaskManagerTest {

    TaskManager manager = null;

    @BeforeEach
    void addManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldReturnTrueIfCreateAllView() {
        manager.add(new Task(Status.IN_PROGRESS, "Name1", "Description1", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Epic("Name1", "Description1"));
        manager.add(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));
        assertSame(manager.getTask(1).getClass(), Task.class);
        assertSame(manager.getEpic(1).getClass(), Epic.class);
        assertSame(manager.getSubtask(1).getClass(), Subtask.class);
        assertEquals(1, manager.getAllTasks().size());
        assertNotNull(manager.getTask(1));
        assertNotNull(manager.getEpic(1));
        assertNotNull(manager.getSubtask(1));
    }

    @Test
    void checkCorrectWorkEpics() {
        manager.add(new Epic("Name1", "Description1"));
        manager.add(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));

        Subtask subtask = manager.getEpic(1).getSubTasks().getFirst();
        assertEquals(manager.getSubtask(1), subtask);
    }

    @Test
    void addNewTask() {
        manager.add(new Task(Status.NEW, "Test addNewTask", "Test addNewTask description", Duration.ZERO, LocalDateTime.now()));

        final int taskId = manager.getTask(1).getID();
        final Task savedTask = manager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(manager.getTask(1), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTask(1), tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void shouldReturnTrueIfEquivalentTests() {
        manager.add(new Task(Status.IN_PROGRESS, "Name1", "Description1", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.NEW, "Name2", "Description2", Duration.ZERO, LocalDateTime.now()));

        assertEquals(manager.getTask(1), manager.getTask(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getTask(1), manager.getTask(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

    @Test
    void shouldReturnTrueIfEquivalentHearsTest() {
        manager.add(new Epic("Name1", "Description1"));
        manager.add(new Epic("Name2", "Description2"));

        manager.add(new Subtask(Status.IN_PROGRESS, "Name1Sub", "Description1Sub"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));

        manager.add(new Subtask(Status.IN_PROGRESS, "Name2Sub", "Description2Sub"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));

        assertEquals(manager.getEpic(1), manager.getEpic(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getEpic(1), manager.getEpic(2));
    }

    @Test
    void subTaskRm() {
        TaskManager manager = new InMemoryTaskManager();
        manager.add(new Epic("N", "D"));
        manager.add(new Subtask(Status.NEW, "Name", "D", manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));
        manager.add(new Subtask(Status.NEW, "Name", "D", manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));
        manager.add(new Subtask(Status.NEW, "Name", "D", manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));
        assertNotNull(manager.getSubtask(1));
        assertNotNull(manager.getSubtask(2));
        assertNotNull(manager.getSubtask(3));

        manager.deleteTask(manager.getEpic(1));
        assertNull(manager.getEpic(1));

    }

}