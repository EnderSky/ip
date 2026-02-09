package glados.parser;

import java.time.LocalDateTime;

import glados.command.Command;
import glados.command.CommandAddTask;
import glados.command.CommandBye;
import glados.command.CommandList;
import glados.command.CommandMark;
import glados.command.CommandDelete;
import glados.command.CommandFind;
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
                dateTime = DateTimeParser.parseToLocalDateTime(by);
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

        if (input.startsWith("find")) {
            if (!input.matches("find .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("find", "find <keyword>"));
            }
            String keyword = input.substring(4).trim();
            if (keyword.isEmpty()) {
                throw new GladosException(Ui.getErrorEmpty("Keyword for find command"));
            }
            return new CommandFind(keyword);
        }

        // Unknown command
        throw new GladosException(Ui.getErrorUnknownCommand());
    }
}
