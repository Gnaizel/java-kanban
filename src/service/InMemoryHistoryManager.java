package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> viwTaskHistory = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (viwTaskHistory.size() == 10) viwTaskHistory.removeFirst();
        this.viwTaskHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return viwTaskHistory;
    }
}
