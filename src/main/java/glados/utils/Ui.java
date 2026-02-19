package glados.utils;

import java.util.Scanner;

/**
 * Handles user interface interactions.
 * Handles all input and output to the user.
 */
public class Ui {

    private String logo;
    private Scanner scanner;

    /**
     * Constructor for Ui class.
     * Initializes the logo and scanner.
     *
     * @param logo The logo string to display.
     */
    public Ui(String logo) {
        this.logo = logo;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints a horizontal line to the console for formatting.
     */
    public static void showLine() {
        String lineChar = "-"; // alternative if Unicode is supported: "\u2500"
        for (int i = 0; i < 83; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }

    /**
     * Prompts the user for input.
     */
    public String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays the welcome message to the user.
     */
    public String getWelcomeMessage() {
        return logo + "\n" + getWelcomeMessageNoLogo();
    }

    /**
     * Displays the welcome message without the logo to the user.
     */
    public String getWelcomeMessageNoLogo() {
        return "Hello, and again, welcome to the Aperture Science Computer-Aided Enrichment Center.\n"
                + "What can I do for you today?";
    }

    /**
     * Displays the exit message to the user.
     */
    public String getGoodbyeMessage() {
        this.scanner.close();
        return "Goodbye. Thank you for participating in this Aperture Science test.\nRemember, the cake is a lie.";
    }

    /**
     * Displays the list of tasks to the user.
     */
    public String getShowTasksMessage(String tasks) {
        return "Here are the tasks in your list:\n" + tasks;
    }

    /**
     * Displays a message when a task is added.
     *
     * @param taskString String representation of the added task.
     * @param totalTasks Total number of tasks after addition.
     */
    public String getAddTaskMessage(String taskString, int totalTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Got it. I've added this task:\n");
        sb.append("  " + taskString + "\n");
        if (totalTasks == 1) {
            sb.append("Now you have 1 task in the list.\n");
        } else {
            sb.append("Now you have " + totalTasks + " tasks in the list.\n");
        }
        return sb.toString();
    }

    /**
     * Displays a success message when a task is marked as done.
     *
     * @param taskNumber number of the task marked as done
     * @param taskString string representation of the task marked as done
     */
    public String getSuccessMark(int taskNumber, String taskString) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nice! I've marked task " + taskNumber + " as done:\n");
        sb.append("  " + taskString + "\n");
        return sb.toString();
    }

    /**
     * Displays a success message when a task is unmarked as not done.
     *
     * @param taskNumber number of the task unmarked as not done
     * @param taskString string representation of the task unmarked as not done
     */
    public String getSuccessUnmark(int taskNumber, String taskString) {
        StringBuilder sb = new StringBuilder();
        sb.append("OK, I've marked task " + taskNumber + " as not done yet:\n");
        sb.append("  " + taskString + "\n");
        return sb.toString();
    }

    /**
     * Displays a success message when a task is deleted.
     *
     * @param taskNumber number of the task deleted
     * @param taskString string representation of the task deleted
     * @param totalTasks total number of tasks after deletion
     */
    public String getSuccessDelete(int taskNumber, String taskString, int totalTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Noted. I've removed task " + taskNumber + " from the list:\n");
        sb.append("  " + taskString + "\n");
        if (totalTasks == 1) {
            sb.append("Now you have 1 task in the list.\n");
        } else {
            sb.append("Now you have " + totalTasks + " tasks in the list.\n");
        }
        return sb.toString();
    }

    /**
     * Displays the found tasks containing the keyword.
     *
     * @param foundTasks String representation of found tasks.
     * @param count      Number of found tasks.
     * @param keyword    The keyword searched for.
     */
    public String getFindTasksMessage(String foundTasks, int count, String keyword) {
        StringBuilder sb = new StringBuilder();
        if (count == 0) {
            sb.append("No tasks found containing the keyword: " + keyword + "\n");
        } else {
            sb.append("Here are the matching tasks in your list:\n");
            sb.append(foundTasks + "\n");
        }
        return sb.toString();
    }

    /**
     * Displays a custom error message.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(message);
    }

    /**
     * Returns an error message for empty task list.
     */
    public static String getErrorEmptyList() {
        return "Your task list is currently empty.\n"
                + "Add tasks using the following commands: todo, deadline, event.";
    }

    /**
     * Returns an error message for incorrect format when marking a task.
     *
     * @param taskName name of the task (mark / unmark)
     */
    public static String getErrorIncorrectNumberFormat(String taskName) {
        return "Please provide the task number to {taskName} in the format: {taskName} <number>"
                .replace("{taskName}", taskName);
    }

    public static String getErrorTaskAlreadyMarked() {
        return "This task is already marked as done.";
    }

    public static String getErrorTaskAlreadyUnmarked() {
        return "This task is already marked as not done.";
    }

    /**
     * Returns an error message for incorrect command format.
     *
     * @param commandName   name of the command
     * @param correctFormat the correct format for the command
     */
    public static String getErrorIncorrectCommandFormat(String commandName, String correctFormat) {
        return "Please provide " + commandName + " in the format: " + correctFormat;
    }

    /**
     * Returns an error message for invalid task number.
     *
     * @param taskName name of the task (mark / unmark / delete)
     * @param listSize size of the current task list
     */
    public static String getErrorInvalidTaskNumber(String taskName, int listSize) {
        if (listSize == 0) {
            return "There are no tasks to " + taskName + ".";
        } else if (listSize == 1) {
            return "Invalid task number. The only valid task number is 1.";
        } else {
            return "Invalid task number. Valid task numbers are from 1 to "
                    + listSize + ".";
        }
    }

    /**
     * Returns an error message for empty input.
     *
     * @param text The text that cannot be empty.
     */
    public static String getErrorEmpty(String text) {
        return text + " cannot be empty.";
    }

    /**
     * Returns an error message for invalid date/time format.
     *
     * @param dateTimeInput The invalid date/time input string.
     */
    public static String getErrorInvalidDateTimeFormat(String dateTimeInput) {
        return "Invalid date/time format: " + dateTimeInput + "\n"
                + "Please use one of the following formats:\n"
                + "- DD/MM/YYYY HH:MM AM/PM (12-hour) or HHMM (24-hour)\n"
                + "- DD MMM YYYY HH:MM AM/PM (12-hour) or HHMM (24-hour)\n"
                + "- YYYY-MM-DD HH:MM AM/PM (12-hour) or HHMM (24-hour)\n"
                + "- Examples: 2/1/2024 6:00 PM, 2 Dec 2024 1830, 2024-12-25 08:00 am";
    }

    /**
     * Returns an error message for unknown command.
     */
    public static String getErrorUnknownCommand(String input) {
        return "I'm sorry, I don't recognize the command: " + input + ".\n"
                + "Type 'help' to see the list of available commands.";
    }

    public static String getHelpString() {
        return "Here are the available commands:\n"
                + "- help: Show this help message.\n"
                + "- list: Show all tasks in the list.\n"
                + "- mark <number>: Mark a task as done by its number in the list.\n"
                + "- unmark <number>: Mark a task as not done by its number in the list.\n"
                + "- delete <number>: Delete a task by its number in the list.\n"
                + "- find <keyword>: Find tasks containing the keyword.\n"
                + "- remindme: Sort and show deadlines.\n"
                + "- todo <description>: Add a todo task with the given description.\n"
                + "- deadline <description> /by <date/time>: Add a deadline task with the given description and due date/time.\n"
                + "- event <description> /from <from> /to <to>: Add an event task with the given description, start date/time, and end date/time.\n"
                + "- bye: Exit the application.\n";
    }
}
