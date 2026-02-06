package glados.command;

import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

public class CommandList extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        if (tasks.getSize() == 0) {
            throw new GladosException(Ui.getErrorEmptyList());
        } else {
            ui.showTasks(tasks.toString());
        }
    }
}
