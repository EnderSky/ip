package glados.task;

/**
 * Represents a Todo task with a description.
 */
public class Todo extends Task {
    
    /**
     * Constructs a Todo task.
     *
     * @param description Description of the todo task.
     */
    public Todo(String description) {
        super(description);
        this.tag = 'T';
    }
}
