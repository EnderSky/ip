package glados.command;

import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to display the help message with a list of valid commands.
 */
public class CommandHelp extends Command {
    /**
     * Executes the help command and returns the help message.
     *
     * @param tasks the task list (not used in this command)
     * @param ui    the user interface to get the help message
     * @return the help message as a string
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws GladosException {
        return Ui.getHelpString();
    }
}
