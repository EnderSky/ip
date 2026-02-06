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
            
            try {
                InputParser cmd = InputParser.parseInput(input);

                switch (cmd) {
                
                    case BYE -> {
                        break loop;
                    }
                    case LIST -> {
                        if (this.taskList.getSize() == 0) {
                            throw new GladosException(Ui.getErrorEmptyList());
                        } else {
                            this.ui.showTasks(this.taskList.toString());
                        }
                    }
                    case MARK -> {
                        int taskNumber = Integer.parseInt(input.split(" ")[1]);
                        int listSize = this.taskList.getSize();
                        if (taskNumber > 0 && taskNumber <= listSize) {
                            Task t = this.taskList.markTaskAsDone(taskNumber - 1);
                            this.ui.showSuccessMark(taskNumber, t.toString());
                        } else {
                            throw new GladosException(Ui.getErrorInvalidTaskNumber("mark", listSize));
                        }
                    }
                    case UNMARK -> {
                        int taskNumber = Integer.parseInt(input.split(" ")[1]);
                        int listSize = this.taskList.getSize();
                        if (taskNumber > 0 && taskNumber <= listSize) {
                            Task t = this.taskList.unmarkTaskAsNotDone(taskNumber - 1);
                            this.ui.showSuccessUnmark(taskNumber, t.toString());
                        } else {
                            throw new GladosException(Ui.getErrorInvalidTaskNumber("unmark", listSize));
                        }
                    }
                    case TODO -> {
                        String description = input.substring(4).trim();
                        if (description.isEmpty()) {
                            throw new GladosException(Ui.getErrorEmpty("Description of a todo"));
                        }
                        Task t = new Todo(description);
                        this.addTask(t);
                    }  
                    case DEADLINE -> {
                        String[] parts = input.substring(8).split(" /by ", 2);
                        String description = parts[0].trim();
                        String by = parts[1].trim();
                        if (description.isEmpty()) {
                            throw new GladosException(Ui.getErrorEmpty("Description of a deadline"));
                        }
                        if (by.isEmpty()) {
                            throw new GladosException(Ui.getErrorEmpty("Deadline time"));
                        }

                        // If by is in datetime format, store in a java.time.LocalDateTime object
                        // Accepts formats: DD/MM/YYYY HH:mm am/pm, DD MMM YYYY HH:mm am/pm, YYYY-MM-DD
                        // HH:mm am/pm
                        LocalDateTime dateTime;

                        try {
                            dateTime = DateTimeParser.parseToLocalDateTime(by.toLowerCase());
                        } catch (IllegalArgumentException e) {
                            throw new GladosException(Ui.getErrorInvalidDateTimeFormat(by));
                        }

                        Deadline t = new Deadline(description, dateTime);
                        this.addTask(t);
                    }
                    case EVENT -> {
                        String[] parts = input.substring(6).split(" /from | /to ", 3);
                        String description = parts[0].trim();
                        String from = parts[1].trim();
                        String to = parts[2].trim();
                        if (description.isEmpty()) {
                            throw new GladosException(Ui.getErrorEmpty("Description of an event"));
                        }
                        if (from.isEmpty() || to.isEmpty()) {
                            throw new GladosException(Ui.getErrorEmpty("From and to times of an event"));
                        }
                        Event t = new Event(description, from, to);
                        this.addTask(t);
                    }
                    case DELETE -> {
                        int taskNumber = Integer.parseInt(input.split(" ")[1]);
                        int listSize = this.taskList.getSize();
                        if (taskNumber > 0 && taskNumber <= listSize) {
                            Task removedTask = this.taskList.removeTask(taskNumber - 1);
                            this.ui.showSuccessDelete(taskNumber, removedTask.toString(), listSize);
                        } else {
                            throw new GladosException(Ui.getErrorInvalidTaskNumber("delete", listSize));
                        }
                    }
                    default -> {
                        throw new GladosException(Ui.getErrorUnknownCommand());
                    }
                }
            } catch (GladosException e) {
                this.ui.showError(e.getMessage());
            } finally {
                Ui.showLine();
            }
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
