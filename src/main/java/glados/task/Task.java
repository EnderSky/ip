package glados.task;

/**
 * Represents a generic Task with a description and completion status.
 */
public class Task {

    protected String description;
    protected boolean isDone;
    protected char tag;

    /**
     * Constructs a Task.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tag = ' ';
    }

    /**
     * Gets the status icon of the task.
     * 
     * @return "X" if done, " " if not done.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the string representation of the Task.
     */
    public String toString() {
        if (this.tag == ' ') {
            return "[" + getStatusIcon() + "] " + description;
        }
        return "[" + this.tag + "][" + getStatusIcon() + "] " + description;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkAsNotDone() {
        this.isDone = false;
    }

    /**
     * Gets the type of the task.
     * 
     * @return Character representing the task type.
     */
    public char getType() {
        return this.tag;
    }

    /**
     * Checks if the task is done.
     * 
     * @return true if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Checks if the task description contains the given keyword.
     * 
     * @param keyword The keyword to search for.
     * @return true if the description contains the keyword, false otherwise.
     */
    public boolean containsKeyword(String keyword) {
        return this.description.contains(keyword);
    }

    /**
     * Gets the description of the task.
     * 
     * @return Description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets additional information about the task.
     * 
     * @return Additional info string, or null if none.
     */
    public String getAdditionalInfo() {
        return null;
    }
}
