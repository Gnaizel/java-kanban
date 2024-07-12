package model;

import org.junit.jupiter.api.Test;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getManagers() {
        Managers.getDefaultHistory();
        assertSame(InMemoryTaskManager.class, Managers.getDefault().getClass());
        assertSame(InMemoryHistoryManager.class, Managers.getDefaultHistory().getClass());
    }
}