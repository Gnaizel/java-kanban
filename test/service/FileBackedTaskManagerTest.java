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
    void addManager() {
        manager = new FileBackedTaskManager();
    }

    @Test
    void saveTest() {
        manager.add(new Task(Status.NEW, "Таск #1", "Таск созданый при первом запуске", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.NEW, "Таск #2", "Таск созданый при первом запуске", Duration.ZERO, LocalDateTime.now()));
        manager.add(new Task(Status.NEW, "Таск #3", "Таск созданый при первом запуске", Duration.ZERO, LocalDateTime.now()));

        manager.add(new Epic("Эпик #1", "Эпик созданый при первом запуске"));
        manager.add(new Epic("Эпик #2", "Эпик созданый при первом запуске"));
        manager.add(new Epic("Эпик #3", "Эпик созданый при первом запуске"));

        manager.add(new Subtask(Status.IN_PROGRESS, "SubTask#1"
                , "SubTask созданый при первом запуске || Относится к Epic id - 1"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));

        manager.add(new Subtask(Status.IN_PROGRESS, "SubTask#2"
                , "SubTask созданый при первом запуске || Относится к Epic id - 1"
                , manager.getEpic(1), Duration.ZERO, LocalDateTime.now()));

        manager.add(new Subtask(Status.IN_PROGRESS, "SubTask#3"
                , "SubTask созданый при первом запуске || Относится к Epic id - 2"
                , manager.getEpic(2), Duration.ZERO, LocalDateTime.now()));

//         Проверка id тасков на ожидаемые
        assertEquals(manager.getTask(1).getTaskName(), "Таск #1", "id не совпадают (Таск #1)");
        assertEquals(manager.getTask(3).getTaskName(), "Таск #3", "id не совпадают (Таск #3)");

        assertEquals(manager.getEpic(1).getTaskName(), "Эпик #1", "id не совпадают (Эпик #1)");
        assertEquals(manager.getEpic(3).getTaskName(), "Эпик #3", "id не совпадают (Эпик #3)");

        assertNull(manager.getTask(4), "Проблема работы id || Таск с id 4 недолжно существовать");
        assertNull(manager.getEpic(4), "Проблема работы id || Эпик с id 4 недолжен существовать");
        assertNull(manager.getSubtask(4), "Проблема работы id || Сабтаск с id 4 недолжен существовать");

        assertEquals(manager.getTask(1).getTaskName(), "Таск #1"
                , "id не совпадают (Таск #1) || 2 запуск");
        assertEquals(manager.getTask(3).getTaskName(), "Таск #3"
                , "id не совпадают (Таск #3) || 2 запуск");

        assertEquals(manager.getEpic(1).getTaskName(), "Эпик #1"
                , "id не совпадают (Эпик #1) || 2 запуск");
        assertEquals(manager.getEpic(3).getTaskName(), "Эпик #3"
                , "id не совпадают (Эпик #3) || 2 запуск");

        assertEquals(manager.getSubtask(1).getTaskName(), "SubTask#1");
        assertEquals(manager.getSubtask(3).getTaskName(), "SubTask#3");
        assertEquals(manager.getSubtask(2).getTaskName(), "SubTask#2");
    }
}