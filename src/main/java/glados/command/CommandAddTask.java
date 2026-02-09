package glados.command;

import java.time.LocalDateTime;

import glados.task.Deadline;
import glados.task.Event;
import glados.task.Task;
import glados.task.TaskList;
import glados.task.TaskType;
import glados.task.Todo;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to add a task (Todo, Deadline, Event) to the task list.
 */
public class CommandAddTask extends Command {

    private TaskType type;
    private String description;
    private LocalDateTime dateTime;
    private String from;
    private String to;

    /**
     * Constructor for Todo task command.
     * 
     * @param type        TaskType.Todo
     * @param description Description of the todo task
     */
    public CommandAddTask(TaskType type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * Constructor for Deadline task command.
     * 
     * @param type        TaskType.DEADLINE
     * @param description Description of the deadline task
     * @param dateTime    Deadline date and time
     */
    public CommandAddTask(TaskType type, String description, LocalDateTime dateTime) {
        this.type = type;
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Constructor for Event task command.
     * 
     * @param type        TaskType.EVENT
     * @param description Description of the event task
     * @param from        Starting time of the event
     * @param to          Ending time of the event
     */
    public CommandAddTask(TaskType type, String description, String from, String to) {
        this.type = type;
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Executes the add task command.
     * 
     * @param tasks The task list
     * @param ui    The user interface
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        Task t;
        switch (this.type) {
            case TODO -> {
                t = new Todo(this.description);
            }
            case DEADLINE -> {
                t = new Deadline(this.description, this.dateTime);
            }
            case EVENT -> {
                t = new Event(this.description, this.from, this.to);
            }
            default -> {
                throw new GladosException("Unknown task type.");
            }
        }
        tasks.addTask(t);
        ui.showAddTaskMessage(t.toString(), tasks.getSize());
    }
}
