import java.util.ArrayList;

/**
 * Manages the list of tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;
    private Storage storage;

    private Task getTask(int index) {
        return this.tasks.get(index);
    }

    public int getSize() {
        return this.tasks.size();
    }

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
     * Adds a task to the task list and saves it to storage.
     * 
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
        this.storage.saveTasks(this.tasks);
    }

    /** 
     * Removes a task from the task list by index and saves the updated list to storage.
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
     * Marks a task as done by index and saves the updated list to storage.
     * 
     * @param index The index of the task to be marked as done.
     * @return The Task object that was marked as done.
     */
    public Task markTaskAsDone(int index) {
        Task t = this.getTask(index);
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
        Task t = this.getTask(index);
        t.unmarkAsNotDone();
        this.storage.saveTasks(this.tasks);
        return t;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.tasks.size(); i++) {
            sb.append((i + 1) + ". " + this.getTask(i).toString() + "\n");
        }
        return sb.toString().trim();
    }
}