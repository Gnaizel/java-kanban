package service;

import model.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTaskManagerTest {

    TaskManager manager = null;

    @BeforeEach
    void createTaskManager() {manager = new FileBackedTaskManager();}

    @Test
    void shouldReturnTrueIfCreateAllView() {
        manager.createTask(new Task(Status.IN_PROGRESS, "Name1", "Description1"));
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpicById(1)));
        assertSame(manager.getTaskById(1).getClass(), Task.class);
        assertSame(manager.getEpicById(1).getClass(), Epic.class);
        assertSame(manager.getSubtaskById(1).getClass(), Subtask.class);
        assertEquals(1, manager.getAllTasks().size());
        assertNotNull(manager.getTaskById(1));
        assertNotNull(manager.getEpicById(1));
        assertNotNull(manager.getSubtaskById(1));
    } // не действителен

    @Test
    void checkCorrectWorkEpics() {
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpicById(1)));

        Subtask subtask = manager.getEpicById(1).getSubtasks().getFirst();
        assertEquals(manager.getSubtaskById(1), subtask);
    }

    @Test
    void addNewTask() {
        manager.createTask(new Task(Status.NEW, "Test addNewTask", "Test addNewTask description"));

        final int taskId = manager.getTaskById(1).getID();
        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(manager.getTaskById(1), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTaskById(1), tasks.getFirst(), "Задачи не совпадают.");
    } // не действителен

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
                , manager.getEpicById(1)));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name2Sub", "Description2Sub"
                , manager.getEpicById(1)));

        assertEquals(manager.getEpicById(1), manager.getEpicById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getEpicById(1), manager.getEpicById(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

}