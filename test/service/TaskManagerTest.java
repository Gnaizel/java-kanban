package service;

import adapter.DurationAdapter;
import adapter.EpicAdapter;
import adapter.LocalDataTimeAdapter;
import adapter.SubtaskAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskManagerTest {

    TaskManager manager = null;

    @BeforeEach
    void createTaskManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldReturnTrueIfCreateAllView() {
        manager.createTask(new Task(Status.IN_PROGRESS, "Name1", "Description1", Duration.ZERO, LocalDateTime.now()));
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.now()));
        assertSame(manager.getTaskById(1).getClass(), Task.class);
        assertSame(manager.getEpicById(1).getClass(), Epic.class);
        assertSame(manager.getSubtaskById(1).getClass(), Subtask.class);
        assertEquals(1, manager.getAllTasks().size());
        assertNotNull(manager.getTaskById(1));
        assertNotNull(manager.getEpicById(1));
        assertNotNull(manager.getSubtaskById(1));
    }

    @Test
    void checkCorrectWorkEpics() {
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name", "description"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.now()));

        Subtask subtask = manager.getEpicById(1).getSubTasks().getFirst();
        assertEquals(manager.getSubtaskById(1), subtask);
    }

    @Test
    void addNewTask() {
        manager.createTask(new Task(Status.NEW, "Test addNewTask", "Test addNewTask description", Duration.ZERO, LocalDateTime.now()));

        final int taskId = manager.getTaskById(1).getID();
        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(manager.getTaskById(1), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTaskById(1), tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void shouldReturnTrueIfEquivalentTests() {
        manager.createTask(new Task(Status.IN_PROGRESS, "Name1", "Description1", Duration.ZERO, LocalDateTime.now()));
        manager.createTask(new Task(Status.NEW, "Name2", "Description2", Duration.ZERO, LocalDateTime.now()));

        assertEquals(manager.getTaskById(1), manager.getTaskById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getTaskById(1), manager.getTaskById(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

    @Test
    void gsonTaskTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDataTimeAdapter())
                .registerTypeAdapter(Epic.class, new EpicAdapter())
                .registerTypeAdapter(Subtask.class, new SubtaskAdapter())
                .setPrettyPrinting()
                .create();
        Epic epic = new Epic("Name1", "Description1");
        String json = gson.toJson(epic);
        System.out.println(json);
        manager.createEpic(gson.fromJson(json, Epic.class));
        Subtask subtask = new Subtask(Status.IN_PROGRESS, "Name1Sub", "Description1Sub"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.now());
        json = gson.toJson(subtask);
        System.out.println(json);
        manager.createSubtask(gson.fromJson(json, Subtask.class));
        System.out.println(gson.toJson(manager.getEpicById(1)));
    }

    @Test
    void shouldReturnTrueIfEquivalentHearsTest() {
        manager.createEpic(new Epic("Name1", "Description1"));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name1Sub", "Description1Sub"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.now()));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name2Sub", "Description2Sub"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.now()));

        manager.createEpic(new Epic("Name2", "Description2"));


        assertEquals(manager.getEpicById(1), manager.getEpicById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getEpicById(1), manager.getEpicById(2));
    }

}