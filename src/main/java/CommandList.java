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
