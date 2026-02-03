import java.util.ArrayList;
import java.time.LocalDateTime;

public class Glados {

    private ArrayList<Task> storedList;
    private Ui ui;
    private Storage storage;

    private void addTask(Task t) {
        this.storedList.add(t);
        this.ui.showAddTaskMessage(t.toString(), this.storedList.size());
        this.storage.saveTasks(this.storedList);
    }

    public Glados(String filePath, String logo) {
        this.ui = new Ui(logo);
        this.storage = new Storage(filePath);
        this.storedList = this.storage.loadTasks();
    }

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
                    if (this.storedList.size() == 0) {
                        this.ui.showErrorEmptyList();
                    } else {
                        this.ui.showTasks(this.storedList);
                    }
                }
                case MARK -> {
                    if (!input.matches("mark \\d+")) {
                        this.ui.showErrorIncorrectNumberFormat("mark");
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= this.storedList.size()) {
                        this.storedList.get(taskNumber - 1).markAsDone();
                        this.ui.showSuccessMark(taskNumber, this.storedList.get(taskNumber - 1).toString());
                        this.storage.saveTasks(this.storedList);
                    } else {
                        this.ui.showErrorInvalidTaskNumber("mark", this.storedList.size());
                    }
                }
                case UNMARK -> {
                    if (!input.matches("unmark \\d+")) {
                        this.ui.showErrorIncorrectNumberFormat("mark");
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= this.storedList.size()) {
                        this.storedList.get(taskNumber - 1).unmarkAsNotDone();
                        this.ui.showSuccessUnmark(taskNumber, this.storedList.get(taskNumber - 1).toString());
                        this.storage.saveTasks(this.storedList);
                    } else {
                        this.ui.showErrorInvalidTaskNumber("unmark", this.storedList.size());
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
                    if (taskNumber > 0 && taskNumber <= this.storedList.size()) {
                        Task removedTask = this.storedList.remove(taskNumber - 1);
                        this.ui.showSuccessDelete(taskNumber, removedTask.toString(), this.storedList.size());
                        this.storage.saveTasks(this.storedList);
                    } else {
                        this.ui.showErrorInvalidTaskNumber("delete", this.storedList.size());
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
