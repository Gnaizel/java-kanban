package service;

import model.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;



class TaskManagerTest {

    TaskManager manager = null;

    @BeforeEach
    void createTaskManager() {manager = new InMemoryTaskManager();}

    @AfterEach
    void deleteTaskManager() {manager.removeThis();}


    @Test
    void addNewTask() {
        manager.createTask(new Task(Status.NEW, "Test addNewTask", "Test addNewTask description"));

        final int taskId = manager.getTaskById(0).getID();
        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(manager.getTaskById(0), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTaskById(0), tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void shouldReturnTrueIfEquivalentTests() {
        manager.createTask(new Task(Status.IN_PROGRESS, "Name1", "Description1"));
        manager.createTask(new Task(Status.NEW, "Name2", "Description2"));

        assertEquals(manager.getTaskById(1), manager.getTaskById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getTaskById(1), manager.getTaskById(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

    @Test
    void shouldReturnTrueIfEquivalentHearsTest() {
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createEpic(new Epic("Name2", "Description2"));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name1Sub", "Description1Sub"
                , manager.getEpicById(0)));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name2Sub", "Description2Sub"
                , manager.getEpicById(1)));

        assertEquals(manager.getEpicById(1), manager.getEpicById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getEpicById(0), manager.getEpicById(1)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }
}