package glados.command;

import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Represents the bye command which exits the program.
 */
public class CommandBye extends Command {

    /**
     * Executes the bye command.
     *
     * @param tasks the task list
     * @param ui    the user interface
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        ui.showGoodbyeMessage();
    }

    /**
     * Indicates that this command exits the program.
     *
     * @return true to indicate exit
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
