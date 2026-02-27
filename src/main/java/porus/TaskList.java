package porus;

import porus.task.Task;

import java.util.ArrayList;

/**
 * Manages the list of tasks.
 * Responsible for all task-related operations.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) throws PorusException {
        if (index < 0 || index >= tasks.size()) {
            throw new PorusException("Invalid task number.");
        }
        return tasks.remove(index);
    }

    public Task mark(int index, boolean isDone) throws PorusException {
        if (index < 0 || index >= tasks.size()) {
            throw new PorusException("Invalid task number.");
        }
        tasks.get(index).setDone(isDone);
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }
}