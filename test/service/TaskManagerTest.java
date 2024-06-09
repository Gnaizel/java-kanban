package service;

import model.*;
import service.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskManagerTest {

    TaskManager manager;

    @BeforeEach
    void createTaskManager() {manager = new InMemoryTaskManager();}


    @Test
    void addNewTask() {
        manager.createTask(new Task(Status.NEW,"Test addNewTask", "Test addNewTask description"));
        final int taskId = manager.getTaskById(1).getID();

        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(manager.getTaskById(1), savedTask, "Задачи не совпадают.");

        final List<Task> tasks = manager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(manager.getTaskById(1), tasks.get(0), "Задачи не совпадают.");
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
                , manager.getEpicById(1)));
        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Name2Sub", "Description2Sub"
                , manager.getEpicById(2)));

        assertEquals(manager.getEpicById(1), manager.getEpicById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        assertNotEquals(manager.getEpicById(1), manager.getEpicById(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

//  @Test
//    void shouldNotWorkIfEpicNotInEpic() {
//        manager.createEpic(new Epic("Name1", "Description1"));
//        assertNull(manager.getEpicById(1).addSubTask(manager.getEpicById(1)));
//    }
//
//    @Test
//    void shouldNotWorkIfSubTaskNotInThisSubtaskEpic() {
//        manager.createSubtask(new Subtask(Status.IN_PROGRESS, "Subtask", "Descreption"
//                , new Epic("Name1", "Description1")));
//        assertNull(manager.getSubtaskById(1).setEpic(manager.getSubtaskById(1)));
//    }

//    проверьте, что экземпляры класса Task равны друг другу, если равен их id; --
//    проверьте, что наследники класса Task равны друг другу, если равен их id;
//    проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
//    проверьте, что объект Subtask нельзя сделать своим же эпиком;
//    убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
//    проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
//    проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
//    создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
//    убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.


}