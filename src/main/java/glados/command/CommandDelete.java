package glados.command;

import glados.task.Task;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to delete a task from the task list.
 */
public class CommandDelete extends Command {
    
    private int taskNumber;

    /**
     * Constructs a CommandDelete with the specified task number.
     *
     * @param taskNumber The 1-based index of the task to delete.
     */
    public CommandDelete(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the delete command, removing the specified task from the task list.
     * 
     * @param tasks The task list from which to delete the task.
     * @param ui The user interface for displaying messages.
     * @throws GladosException If the task number is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        int listSize = tasks.getSize();
        int taskIndex = this.taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= listSize) {
            throw new GladosException(Ui.getErrorInvalidTaskNumber("delete", listSize));
        }

        Task task = tasks.removeTask(taskIndex);
        ui.showSuccessDelete(this.taskNumber, task.toString(), listSize - 1);
    }
}
