package glados.command;

import glados.task.Task;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

public class CommandMark extends Command {
    private final int taskNumber;
    private final boolean isMark;

    public CommandMark(int taskNumber, boolean isMark) {
        this.taskNumber = taskNumber;
        this.isMark = isMark;
    }

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
