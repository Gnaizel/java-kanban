package service;

import model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList viwTaskHistory = new CustomLinkedList();

    @Override
    public void add(Task task) {
        viwTaskHistory.linkLast(task);
    }

    @Override
    public void remove(int id) {
        viwTaskHistory.removeNode(viwTaskHistory.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return viwTaskHistory.getTasks();
    }

    private static class CustomLinkedList {
        private final Map<Integer, Node> nodeMap = new HashMap<>();
        private Node head;
        private Node tail;

        private void linkLast(Task task) {
            Node element = new Node();
            element.setTask(task);
            if (nodeMap.containsKey(task.getID())) {
                removeNode(nodeMap.get(task.getID()));
            }
            if (head == null) {
                tail = element;
                head = element;
                element.setNext(null);
                element.setPrev(null);
            } else {
                element.setPrev(tail);
                element.setNext(null);
                tail.setNext(element);
                tail = element;
            }
            nodeMap.put(task.getID(), element);
        }

        private List<Task> getTasks() {
            List<Task> taskList = new ArrayList<>();
            Node element = head;
            while (element != null) {
                taskList.add(element.getTask());
                element = element.getNext();
            }
            return taskList;
        }

        private void removeNode(Node node) {
            if (node != null) {
                nodeMap.remove(node.getTask().getID());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                }
                if (tail == node) {
                    tail = node.getPrev();
                }
                if (prev != null) {
                    prev.setNext(next);
                }
                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(int id) {
            return nodeMap.get(id);
        }

        static class Node {
            private Task task;
            private Node prev;
            private Node next;

            public Task getTask() {
                return task;
            }

            public void setTask(Task task) {
                this.task = task;
            }

            public Node getPrev() {
                return prev;
            }

            public void setPrev(Node prev) {
                this.prev = prev;
            }

            public Node getNext() {
                return next;
            }

            public void setNext(Node next) {
                this.next = next;
            }
        }
    }
}

