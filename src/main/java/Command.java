/**
 * Parent class for all commands.
 */
public abstract class Command {    
    /**
     * Executes the command.
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
