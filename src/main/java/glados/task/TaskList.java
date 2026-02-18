package glados.task;

import java.util.ArrayList;

import glados.utils.Storage;

/**
 * Manages the list of tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    /**
     * Constructor for TaskList class.
     *
     * @param storage The Storage object to load tasks from.
     */
    public TaskList(Storage storage) {
        this.storage = storage;
        this.tasks = this.storage.loadTasks();
    }

    /**
     * Gets the list of tasks.
     *
     * @return An ArrayList of Task objects.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Gets the task at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The Task object at the specified index.
     */
    public Task getTaskByIndex(int index) {
        assert index >= 0 && index < this.tasks.size() : "Index should be within the bounds of the task list";
        return this.tasks.get(index);
    }

    /**
     * Gets the size of the task list.
     *
     * @return The number of tasks in the list.
     */
    public int getSize() {
        return this.tasks.size();
    }

    /**
     * Adds a task to the task list and saves it to storage.
     *
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
        this.storage.saveTasks(this.tasks);
    }

    /**
     * Removes a task from the task list by index and saves the updated list to
     * storage.
     *
     * @param index The index of the task to be removed.
     * @return The Task object that was removed.
     */
    public Task removeTask(int index) {
        Task t = this.tasks.remove(index);
        this.storage.saveTasks(this.tasks);
        return t;
    }

    /**
     * Checks if a task is marked as done by index.
     *
     * @param index The index of the task to check.
     * @return true if the task is marked as done, false otherwise.
     */
    public Boolean isTaskMarked(int index) {
        return this.getTaskByIndex(index).isDone();
    }

    /**
     * Marks a task as done by index and saves the updated list to storage.
     *
     * @param index The index of the task to be marked as done.
     * @return The Task object that was marked as done.
     */
    public Task markTaskAsDone(int index) {
        assert index >= 0 && index < this.tasks.size() : "Index should be within the bounds of the task list";
        Task t = this.getTaskByIndex(index);
        t.markAsDone();
        this.storage.saveTasks(this.tasks);
        return t;
    }

    /**
     * Unmarks a task as not done by index and saves the updated list to storage.
     *
     * @param index The index of the task to be unmarked as not done.
     * @return The Task object that was unmarked as not done.
     */
    public Task unmarkTaskAsNotDone(int index) {
        assert index >= 0 && index < this.tasks.size() : "Index should be within the bounds of the task list";
        Task t = this.getTaskByIndex(index);
        t.unmarkAsNotDone();
        this.storage.saveTasks(this.tasks);
        return t;
    }

    /**
     * Returns the string representation of the TaskList.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            sb.append((i + 1) + ". " + this.getTaskByIndex(i).toString() + "\n");
        }
        return sb.toString().trim();
    }
}
