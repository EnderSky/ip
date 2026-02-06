import java.time.LocalDateTime;

/**
 * InputParser class to handle user input parsing.
 */
public enum InputParser {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    UNKNOWN;

    public static Command parseInput(String input) throws GladosException {
        if (input.equals("bye")) {
            return new CommandBye();
        }
        
        if (input.equals("list")) {
            return new CommandList();
        }
        
        if (input.startsWith("mark")) {
            if (!input.matches("mark \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("mark"));
            }
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            return new CommandMark(taskNumber, true);
        }
        
        if (input.startsWith("unmark")) {
            if (!input.matches("unmark \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("mark"));
            }
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            return new CommandMark(taskNumber, false);
        }
        
        if (input.startsWith("todo")) {
            if (!input.matches("todo .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("todo", "todo <description>"));
            }

            String description = input.substring(4).trim();
            if (description.isEmpty()) {
                throw new GladosException(Ui.getErrorEmpty("Description of a todo"));
            }

            return new CommandAddTask(TaskType.TODO, description);
        }
        
        if (input.startsWith("deadline")) {
            if (!input.matches("deadline .* /by .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("deadline",
                        "deadline <description> /by <date/time>"));
            }

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

            return new CommandAddTask(TaskType.DEADLINE, description, dateTime);
        }
        
        if (input.startsWith("event")) {
            if (!input.matches("event .* /from .* /to .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("event",
                        "event <description> /from <from> /to <to>"));
            }

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

            return new CommandAddTask(TaskType.EVENT, description, from, to);
        }

        if (input.startsWith("delete")) {
            if (!input.matches("delete \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("delete"));
            }
            int taskNumber = Integer.parseInt(input.split(" ")[1]);
            return new CommandDelete(taskNumber);
        }
        
        // Unknown command
        throw new GladosException(Ui.getErrorUnknownCommand());
    }
}