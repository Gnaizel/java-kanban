package model;

import service.*;

public class Managers {

    static HistoryManager getDefault() {
        return new InMemoryHistoryManager();
    }
}
