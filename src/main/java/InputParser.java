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

    public static InputParser parseInput(String input) throws GladosException {
        if (input.equals("bye")) {
            return BYE;
        }
        
        if (input.equals("list")) {
            return LIST;
        }
        
        if (input.startsWith("mark")) {
            if (!input.matches("mark \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("mark"));
            }
            return MARK;
        }
        
        if (input.startsWith("unmark")) {
            if (!input.matches("unmark \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("mark"));
            }
            return UNMARK;
        }
        
        if (input.startsWith("todo")) {
            if (!input.matches("todo .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("todo", "todo <description>"));
            }
            return TODO;
        }
        
        if (input.startsWith("deadline")) {
            if (!input.matches("deadline .* /by .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("deadline",
                        "deadline <description> /by <date/time>"));
            }
            return DEADLINE;
        }
        
        if (input.startsWith("event")) {
            if (!input.matches("event .* /from .* /to .*")) {
                throw new GladosException(Ui.getErrorIncorrectCommandFormat("event",
                        "event <description> /from <from> /to <to>"));
            }
            return EVENT;
        }

        if (input.startsWith("delete")) {
            if (!input.matches("delete \\d+")) {
                throw new GladosException(Ui.getErrorIncorrectNumberFormat("delete"));
            }
            return DELETE;
        }
        
        // Unknown command
        throw new GladosException(Ui.getErrorUnknownCommand());
    }
}