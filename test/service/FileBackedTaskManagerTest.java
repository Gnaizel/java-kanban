package service;

import model.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class FileBackedTaskManagerTest {

    FileBackedTaskManager manager = null;

    @BeforeEach
    void createTaskManager() {
        manager = new FileBackedTaskManager();
    }

    @Test
    void saveTest() {
        manager.createTask(new Task(Status.NEW, "Таск #1", "Таск созданый при первом запуске", Duration.ofMinutes(30), LocalDateTime.parse("2024-08-08T22:00:00")));
        manager.createTask(new Task(Status.NEW, "Таск #2", "Таск созданый при первом запуске", Duration.ofMinutes(1123123), LocalDateTime.parse("2024-08-08T22:00:15")));
        manager.createTask(new Task(Status.NEW, "Таск #3", "Таск созданый при первом запуске", Duration.ofMinutes(19), LocalDateTime.parse("2024-08-08T22:10:00")));

        manager.createEpic(new Epic("Эпик #1", "Эпик созданый при первом запуске"));
        manager.createEpic(new Epic("Эпик #2", "Эпик созданый при первом запуске"));
        manager.createEpic(new Epic("Эпик #3", "Эпик созданый при первом запуске"));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "SubTask#1"
                , "SubTask созданый при первом запуске || Относится к Epic id - 1"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.parse("2024-08-08T22:00:59")));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "SubTask#2"
                , "SubTask созданый при первом запуске || Относится к Epic id - 1"
                , manager.getEpicById(1), Duration.ZERO, LocalDateTime.parse("2024-08-08T22:01:50")));

        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "SubTask#3"
                , "SubTask созданый при первом запуске || Относится к Epic id - 2"
                , manager.getEpicById(2), Duration.ofMinutes(10), LocalDateTime.parse("2024-08-08T22:03:59")));

//         Проверка id тасков на ожидаемые
        assertEquals(manager.getTaskById(1).getTaskName(), "Таск #1", "id не совпадают (Таск #1)");
        assertEquals(manager.getTaskById(3).getTaskName(), "Таск #3", "id не совпадают (Таск #3)");

        assertEquals(manager.getEpicById(1).getTaskName(), "Эпик #1", "id не совпадают (Эпик #1)");
        assertEquals(manager.getEpicById(3).getTaskName(), "Эпик #3", "id не совпадают (Эпик #3)");

        assertNull(manager.getTaskById(4), "Проблема работы id || Таск с id 4 недолжно существовать");
        assertNull(manager.getEpicById(4), "Проблема работы id || Эпик с id 4 недолжен существовать");
        assertNull(manager.getSubtaskById(4), "Проблема работы id || Сабтаск с id 4 недолжен существовать");

        assertEquals(manager.getTaskById(1).getTaskName(), "Таск #1"
                , "id не совпадают (Таск #1) || 2 запуск");
        assertEquals(manager.getTaskById(3).getTaskName(), "Таск #3"
                , "id не совпадают (Таск #3) || 2 запуск");

        assertEquals(manager.getEpicById(1).getTaskName(), "Эпик #1"
                , "id не совпадают (Эпик #1) || 2 запуск");
        assertEquals(manager.getEpicById(3).getTaskName(), "Эпик #3"
                , "id не совпадают (Эпик #3) || 2 запуск");

        assertEquals(manager.getSubtaskById(1).getTaskName(), "SubTask#1");
        assertEquals(manager.getSubtaskById(3).getTaskName(), "SubTask#3");
        assertEquals(manager.getSubtaskById(2).getTaskName(), "SubTask#2");

        manager.getPrioritizedTasks();
    }
}