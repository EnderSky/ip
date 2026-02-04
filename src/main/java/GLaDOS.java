import java.time.LocalDateTime;

/**
 * Main application class for GLaDOS task manager.
 */
public class Glados {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Runs the main application loop.
     */
    public void run() {
        this.ui.showWelcomeMessage();

        String input;

        loop: while (true) {
            input = this.ui.getUserInput();
            Ui.showLine();

            Command cmd = Command.fromInput(input);

            switch (cmd) {
                case BYE -> {
                    break loop;
                }
                case LIST -> {
                    if (this.taskList.getSize() == 0) {
                        this.ui.showErrorEmptyList();
                    } else {
                        this.ui.showTasks(this.taskList.toString());
                    }
                }
                case MARK -> {
                    if (!input.matches("mark \\d+")) {
                        this.ui.showErrorIncorrectNumberFormat("mark");
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    int listSize = this.taskList.getSize();
                    if (taskNumber > 0 && taskNumber <= listSize) {
                        Task t = this.taskList.markTaskAsDone(taskNumber - 1);
                        this.ui.showSuccessMark(taskNumber, t.toString());
                    } else {
                        this.ui.showErrorInvalidTaskNumber("mark", listSize);
                    }
                }
                case UNMARK -> {
                    if (!input.matches("unmark \\d+")) {
                        this.ui.showErrorIncorrectNumberFormat("mark");
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    int listSize = this.taskList.getSize();
                    if (taskNumber > 0 && taskNumber <= listSize) {
                        Task t = this.taskList.unmarkTaskAsNotDone(taskNumber - 1);
                        this.ui.showSuccessUnmark(taskNumber, t.toString());
                    } else {
                        this.ui.showErrorInvalidTaskNumber("unmark", listSize);
                    }
                }
                case TODO -> {
                    if (!input.matches("todo .*")) {
                        this.ui.showErrorIncorrectCommandFormat("todo", "todo <description>");
                        continue;
                    }
                    String description = input.substring(4).trim();
                    if (description.isEmpty()) {
                        this.ui.showErrorEmpty("Description of a todo");
                        continue;
                    }
                    Task t = new Todo(description);
                    this.addTask(t);
                }
                case DEADLINE -> {
                    if (!input.matches("deadline .* /by .*")) {
                        this.ui.showErrorIncorrectCommandFormat("deadline",
                                "deadline <description> /by <date/time>");
                        continue;
                    }
                    String[] parts = input.substring(8).split(" /by ", 2);
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    if (description.isEmpty()) {
                        this.ui.showErrorEmpty("Description of a deadline");
                        continue;
                    }
                    if (by.isEmpty()) {
                        this.ui.showErrorEmpty("Deadline time");
                        continue;
                    }

                    // If by is in datetime format, store in a java.time.LocalDateTime object
                    // Accepts formats: DD/MM/YYYY HH:mm am/pm, DD MMM YYYY HH:mm am/pm, YYYY-MM-DD
                    // HH:mm am/pm
                    LocalDateTime dateTime;

                    try {
                        dateTime = DateTimeParser.parseToLocalDateTime(by.toLowerCase());
                    } catch (IllegalArgumentException e) {
                        this.ui.showErrorInvalidDateTimeFormat(by);
                        continue;
                    }

                    Deadline t = new Deadline(description, dateTime);
                    this.addTask(t);
                }
                case EVENT -> {
                    if (!input.matches("event .* /from .* /to .*")) {
                        this.ui.showErrorIncorrectCommandFormat("event",
                                "event <description> /from <from> /to <to>");
                        continue;
                    }
                    String[] parts = input.substring(6).split(" /from | /to ", 3);
                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    if (description.isEmpty()) {
                        this.ui.showErrorEmpty("Description of an event");
                        continue;
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        this.ui.showErrorEmpty("From and to times of an event");
                        continue;
                    }
                    Event t = new Event(description, from, to);
                    this.addTask(t);
                }
                case DELETE -> {
                    if (!input.matches("delete \\d+")) {
                        this.ui.showErrorIncorrectNumberFormat("delete");
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    int listSize = this.taskList.getSize();
                    if (taskNumber > 0 && taskNumber <= listSize) {
                        Task removedTask = this.taskList.removeTask(taskNumber - 1);
                        this.ui.showSuccessDelete(taskNumber, removedTask.toString(), listSize);
                    } else {
                        this.ui.showErrorInvalidTaskNumber("delete", listSize);
                    }
                }
                case UNKNOWN -> {
                    this.ui.showErrorUnknownCommand();
                }
            }

            Ui.showLine();
        }

        this.ui.showGoodbyeMessage();
    }

    private void addTask(Task t) {
        this.taskList.addTask(t);
        this.ui.showAddTaskMessage(t.toString(), this.taskList.getSize());
    }

    /**
     * Constructor for GLaDOS application.
     * Initializes UI, Storage, and TaskList components.
     * 
     * @param filePath File path for storing tasks.
     * @param logo Logo string to display on startup.
     */
    public Glados(String filePath, String logo) {
        this.ui = new Ui(logo);
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(this.storage);
    }

    /**
     * Main method to start the GLaDOS application.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        String filePath = "../../../data/tasks.txt";
        String logo = "  ____ _          ____   ___  ____  \r\n" + //
                " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n" + //
                "| |  _| |   / _` | | | | | | \\___ \\ \r\n" + //
                "| |_| | |__| (_| | |_| | |_| |___) |\r\n" + //
                " \\____|_____\\__,_|____/ \\___/|____/ \r\n" + //
                "                                       ";

        Glados app = new Glados(filePath, logo);
        app.run();
    }
}
