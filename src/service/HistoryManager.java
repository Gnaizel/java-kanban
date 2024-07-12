package service;

import model.Task;
import java.util.List;

public interface HistoryManager {

    void remove(int id);

    void add(Task task);

    List<Task> getHistory();
}
