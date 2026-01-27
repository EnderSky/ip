// Keep all possible command types in one place using enums
// Prevents mistyping command strings
public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    TODO,
    DEADLINE,
    EVENT,
    DELETE,
    UNKNOWN;

    public static Command fromInput(String input) {
        if (input.equals("bye")) return BYE;
        if (input.equals("list")) return LIST;
        if (input.startsWith("mark")) return MARK;
        if (input.startsWith("unmark")) return UNMARK;
        if (input.startsWith("todo")) return TODO;
        if (input.startsWith("deadline")) return DEADLINE;
        if (input.startsWith("event")) return EVENT;
        if (input.startsWith("delete")) return DELETE;
        return UNKNOWN;
    }
}
