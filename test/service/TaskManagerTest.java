package service;

import model.*;
import service.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;

class TaskManagerTest {

    TaskManager manager;

    @BeforeEach
    void createTaskManager() {manager = new InMemoryTaskManager();}

    @Test
    void shouldReturnTrueIfEquivalentTests() {
        manager.createTask(new Task(Status.IN_PROGRESS, "Name1", "Description1"));
        manager.createTask(new Task(Status.NEW, "Name2", "Description2"));

        Assertions.assertEquals(manager.getTaskById(1), manager.getTaskById(1)
                , "Задачи с ID:1 и ID:1 не эквивалентны !!!");

        Assertions.assertNotEquals(manager.getTaskById(1), manager.getTaskById(2)
                , "Задачи с ID:1 и ID:2 эквивалентны !!!");
    }

//    проверьте, что экземпляры класса Task равны друг другу, если равен их id;
//    проверьте, что наследники класса Task равны друг другу, если равен их id;
//    проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
//    проверьте, что объект Subtask нельзя сделать своим же эпиком;
//    убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
//    проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
//    проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
//    создайте тест, в котором проверяется неизменность задачи (по всем полям) при добавлении задачи в менеджер
//    убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.


}