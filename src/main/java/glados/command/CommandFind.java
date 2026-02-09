package glados.command;

import glados.task.Task;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * CommandFind class to handle the 'find' command.
 */
public class CommandFind extends Command {

    private String keyword;

    /**
     * Constructor for CommandFind class.
     * 
     * @param keyword The keyword to search for in tasks.
     */
    public CommandFind(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command to search for tasks containing the keyword.
     * 
     * @param tasks The task list
     * @param ui    The user interface
     * @throws GladosException If an error occurs during execution
     */
    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        StringBuilder foundTasks = new StringBuilder();
        int count = 0;
        for (int i = 0; i < tasks.getSize(); i++) {
            Task task = tasks.getTaskByIndex(i);
            if (task.containsKeyword(this.keyword)) {
                if (count > 0) {
                    foundTasks.append("\n");
                }
                count++;
                foundTasks.append(count).append(". ").append(task.toString());
            }
        }
        ui.showFindTasksMessage(foundTasks.toString(), count, this.keyword);
    }

}
