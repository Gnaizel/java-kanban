package model;

import service.HistoryManager;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

public class Managers {
    public static TaskManager getInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
