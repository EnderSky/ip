package glados.command;

import java.util.stream.Stream;

import glados.task.Deadline;
import glados.task.DeadlineCategory;
import glados.task.Task;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * Command to remind the user of their tasks with upcoming deadlines.
 */
public class CommandRemindMe extends Command {

    @Override
    public String execute(TaskList tasks, Ui ui) throws GladosException {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Here are your upcoming deadlines:\n");

        // Return message in the following format
        // Overdue:
        // 3. [D][X] Submit assignment (by: 02 Jan 2024 06:00 PM) - Overdue
        // 4. [D][ ] Pay bills (by: 03 Jan 2024 11:59 PM) - Overdue
        // Due Today:
        // 6. [D][ ] Attend meeting (by: 19 Jan 2026 09:00 AM) - Due Today
        // Due Within a Week:
        // 1. [D][ ] Prepare presentation (by: 22 Jan 2026 02:00 PM) - Due Within a Week
        // Due in the Future:
        // 2. [D][ ] Plan vacation (by: 20 Jan 2027)
        for (DeadlineCategory group : DeadlineCategory.values()) {
            messageBuilder.append(formatDeadlineGroup(group, tasks.getTasks().stream(), tasks)).append("\n");
        }
        return messageBuilder.toString();
    }

    private String formatDeadlineGroup(DeadlineCategory category, Stream<Task> taskStream, TaskList tasks) {
        // AI Assisted (Tool: GitHub Copilot)
        // The prompt is given below:
        // Do the following with Java streams
        // 1. Filter all tasks to only include Deadline tasks that are not marked as
        // done
        // 2. Sort the Deadline tasks by their deadline in ascending order
        // 3. Group the tasks by the following groups: overdue, today, within the next
        // week, far future
        // 4. Map the groups to a string representation
        // 5. Format the string representation of the groups into a final message to be
        // returned to the user
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(category.toString());
        tasks.getTasks().stream()
                .filter(task -> task.getType() == 'D')
                .filter(task -> !task.isDone())
                .sorted((t1, t2) -> {
                    assert t1 instanceof Deadline : "Task should be of type Deadline";
                    assert t2 instanceof Deadline : "Task should be of type Deadline";
                    return ((Deadline) t1).getBy().compareTo(((Deadline) t2).getBy());
                }).filter(task -> {
                    Deadline deadlineTask = (Deadline) task;
                    return deadlineTask.getDeadlineStatus().equals(category);
                }).forEach(task -> {
                    sb.append("\n").append(tasks.getTasks().indexOf(task) + 1).append(". ").append(task.toString());
                });
        return sb.toString();
    }
}
