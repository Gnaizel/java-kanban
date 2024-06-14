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
            /*
            Я щитаю тут не нужна проверка на null так как в TaskManager.getTaskById() Я уже сделал эту проверку
            соответственно суда не может большое попспть null
             */
    }

    @Override
    public List<Task> getHistory() {
        return viwTaskHistory;
    }
}
