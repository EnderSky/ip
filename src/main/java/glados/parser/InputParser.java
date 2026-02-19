package glados.parser;

import java.time.LocalDateTime;

import glados.command.Command;
import glados.command.CommandAddTask;
import glados.command.CommandBye;
import glados.command.CommandDelete;
import glados.command.CommandFind;
import glados.command.CommandHelp;
import glados.command.CommandList;
import glados.command.CommandMark;
import glados.command.CommandRemindMe;
import glados.task.TaskType;
import glados.utils.GladosException;
import glados.utils.Ui;

/**
 * InputParser class to handle user input parsing.
 */
public class InputParser {

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param input The user input string.
     * @return The Command object corresponding to the input.
     * @throws GladosException If the input is invalid or cannot be parsed.
     */
    public static Command parseInput(String input) throws GladosException {
        input = input.trim();
        if (input.equals("bye")) {
            return new CommandBye();
        } else if (input.equals("help")) {
            return new CommandHelp();
        } else if (input.equals("list")) {
            return new CommandList();
        } else if (input.equals("remindme")) {
            return new CommandRemindMe();
        } else if (input.startsWith("mark")) {
            return parseMarkCommand(input, true);
        } else if (input.startsWith("unmark")) {
            return parseMarkCommand(input, false);
        } else if (input.startsWith("todo")) {
            return parseTodoCommand(input);
        } else if (input.startsWith("deadline")) {
            return parseDeadlineCommand(input);
        } else if (input.startsWith("event")) {
            return parseEventCommand(input);
        } else if (input.startsWith("delete")) {
            return parseDeleteCommand(input);
        } else if (input.startsWith("find")) {
            return parseFindCommand(input);
        }

        // Unknown command
        throw new GladosException(Ui.getErrorUnknownCommand(input));
    }

    private static Command parseMarkCommand(String input, boolean isMark) throws GladosException {
        // Trim internal spaces
        input = input.replaceAll("\\s+", " ");
        if (!input.matches("(mark|unmark) \\d+")) {
            throw new GladosException(Ui.getErrorIncorrectNumberFormat(isMark ? "mark" : "unmark"));
        }
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        return new CommandMark(taskNumber, isMark);
    }

    private static Command parseTodoCommand(String input) throws GladosException {
        if (!input.matches("todo .*")) {
            throw new GladosException(Ui.getErrorIncorrectCommandFormat("todo", "todo <description>"));
        }
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new GladosException(Ui.getErrorEmpty("Description of a todo"));
        }

        return new CommandAddTask(TaskType.TODO, description);
    }

    private static Command parseDeadlineCommand(String input) throws GladosException {
        if (!input.matches("deadline .* /by .*")) {
            throw new GladosException(
                    Ui.getErrorIncorrectCommandFormat("deadline", "deadline <description> /by <date/time>"));
        }
        String[] parts = input.substring(8).split(" /by ", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        if (description.isEmpty()) {
            throw new GladosException(Ui.getErrorEmpty("Description of a deadline"));
        } else if (by.isEmpty()) {
            throw new GladosException(Ui.getErrorEmpty("Deadline time"));
        }

        // If by is in datetime format, store in a java.time.LocalDateTime object
        // Accepts formats: DD/MM/YYYY HH:mm am/pm, DD MMM YYYY HH:mm am/pm, YYYY-MM-DD
        // HH:mm am/pm
        LocalDateTime dateTime;
        try {
            dateTime = DateTimeParser.parseToLocalDateTime(by);
        } catch (IllegalArgumentException e) {
            throw new GladosException(Ui.getErrorInvalidDateTimeFormat(by));
        }

        return new CommandAddTask(TaskType.DEADLINE, description, dateTime);
    }

    private static Command parseEventCommand(String input) throws GladosException {
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
        } else if (from.isEmpty() || to.isEmpty()) {
            throw new GladosException(Ui.getErrorEmpty("From and to times of an event"));
        }

        return new CommandAddTask(TaskType.EVENT, description, from, to);
    }

    private static Command parseDeleteCommand(String input) throws GladosException {
        if (!input.matches("delete \\d+")) {
            throw new GladosException(Ui.getErrorIncorrectNumberFormat("delete"));
        }
        int taskNumber = Integer.parseInt(input.split(" ")[1]);
        return new CommandDelete(taskNumber);
    }

    private static Command parseFindCommand(String input) throws GladosException {
        if (!input.matches("find .*")) {
            throw new GladosException(Ui.getErrorIncorrectCommandFormat("find", "find <keyword>"));
        }
        String keyword = input.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new GladosException(Ui.getErrorEmpty("Keyword for find command"));
        }
        return new CommandFind(keyword);
    }

}
