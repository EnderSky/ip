package glados.command;

import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Parent class for all commands.
 */
public abstract class Command {    

    /**
     * Executes the command.
     * 
     * @param tasks The task list
     * @param ui The user interface
     * @throws GladosException If an error occurs during execution
     */
    public abstract void execute(TaskList tasks, Ui ui) throws GladosException;

    /**
     * Indicates whether this command exits the program.
     * 
     * @return false as default behavior
     */
    public boolean isExit() {
        return false;
    }
}
