package glados.command;

import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to list all tasks.
 */
public class CommandList extends Command {

    /**
     * Executes the list command to display all tasks.
     *
     * @param tasks The TaskList containing all tasks.
     * @param ui    The Ui instance for user interaction.
     * @throws GladosException If there are no tasks to list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui) throws GladosException {
        if (tasks.getSize() == 0) {
            throw new GladosException(Ui.getErrorEmptyList());
        } else {
            return ui.getShowTasksMessage(tasks.toString());
        }
    }
}
