public class CommandDelete extends Command {
    private int taskNumber;

    public CommandDelete(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    @Override
    public void execute(TaskList tasks, Ui ui) throws GladosException {
        int listSize = tasks.getSize();
        int taskIndex = this.taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= listSize) {
            throw new GladosException(Ui.getErrorInvalidTaskNumber("delete", listSize));
        }

        Task task = tasks.removeTask(taskIndex);
        ui.showSuccessDelete(this.taskNumber, task.toString(), listSize - 1);
    }
}
