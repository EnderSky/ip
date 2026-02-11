package glados.command;

import glados.task.Task;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to mark or unmark a task as done.
 */
public class CommandMark extends Command {

    private final int taskNumber;
    private final boolean isMark;

    /**
     * Constructs a CommandMark with the specified task number and action.
     *
     * @param taskNumber The 1-based index of the task to mark or unmark.
     * @param isMark     True to mark the task as done, false to unmark it.
     */
    public CommandMark(int taskNumber, boolean isMark) {
        this.taskNumber = taskNumber;
        this.isMark = isMark;
    }

    /**
     * Executes the mark or unmark command on the specified task.
     *
     * @param tasks The task list containing the task to be marked or unmarked.
     * @param ui    The user interface for displaying messages.
     * @throws GladosException If the task number is invalid or if the task is
     *                         already in the desired state.
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        int listSize = tasks.getSize();
        String text = isMark ? "mark" : "unmark";
        int taskIndex = this.taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= listSize) {
            throw new GladosException(Ui.getErrorInvalidTaskNumber(text, listSize));
        }

        Boolean isTaskMarked = tasks.isTaskMarked(taskIndex);
        if (isMark) {
            if (isTaskMarked) {
                throw new GladosException(Ui.getErrorTaskAlreadyMarked());
            }
            Task task = tasks.markTaskAsDone(taskIndex);
            ui.showSuccessMark(this.taskNumber, task.toString());
        } else {
            if (!isTaskMarked) {
                throw new GladosException(Ui.getErrorTaskAlreadyUnmarked());
            }
            Task task = tasks.unmarkTaskAsNotDone(taskIndex);
            ui.showSuccessUnmark(this.taskNumber, task.toString());
        }
    }

}
