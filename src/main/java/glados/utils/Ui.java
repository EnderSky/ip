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
        // String lineChar = "\u2500";
        String lineChar = "-";
        for (int i = 0; i < 83; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcomeMessage() {
        System.out.println(logo);
        System.out.println("Hello, and again, welcome to the Aperture Science Computer-Aided Enrichment Center.");
        System.out.println("What can I do for you today?");
        Ui.showLine();
    }

    /**
     * Displays the exit message to the user.
     */
    public void showGoodbyeMessage() {
        this.scanner.close();
        System.out.println("Goodbye. Thank you for participating in this Aperture Science test.");
        System.out.println("Remember, the cake is a lie.");
    }

    /**
     * Prompts the user for input.
     */
    public String getUserInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    /**
     * Displays the list of tasks to the user.
     */
    public void showTasks(String tasks) {
        System.out.println("Here are the tasks in your list:");
        System.out.println(tasks);
    }

    /**
     * Displays a message when a task is added.
     *
     * @param taskString String representation of the added task.
     * @param totalTasks Total number of tasks after addition.
     */
    public void showAddTaskMessage(String taskString, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + taskString);
        if (totalTasks == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + totalTasks + " tasks in the list.");
        }
    }

    /**
     * Displays a success message when a task is marked as done.
     *
     * @param taskNumber number of the task marked as done
     * @param taskString string representation of the task marked as done
     */
    public void showSuccessMark(int taskNumber, String taskString) {
        System.out.println("Nice! I've marked task " + taskNumber + " as done:");
        System.out.println("  " + taskString);
    }

    /**
     * Displays a success message when a task is unmarked as not done.
     *
     * @param taskNumber number of the task unmarked as not done
     * @param taskString string representation of the task unmarked as not done
     */
    public void showSuccessUnmark(int taskNumber, String taskString) {
        System.out.println("OK, I've marked task " + taskNumber + " as not done yet:");
        System.out.println("  " + taskString);
    }

    /**
     * Displays a success message when a task is deleted.
     *
     * @param taskNumber number of the task deleted
     * @param taskString string representation of the task deleted
     * @param totalTasks total number of tasks after deletion
     */
    public void showSuccessDelete(int taskNumber, String taskString, int totalTasks) {
        System.out.println("Noted. I've removed task " + taskNumber + " from the list:");
        System.out.println("  " + taskString);
        if (totalTasks == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + totalTasks + " tasks in the list.");
        }
    }

    /**
     * Displays the found tasks containing the keyword.
     *
     * @param foundTasks String representation of found tasks.
     * @param count      Number of found tasks.
     * @param keyword    The keyword searched for.
     */
    public void showFindTasksMessage(String foundTasks, int count, String keyword) {
        if (count == 0) {
            System.out.println("No tasks found containing the keyword: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            System.out.println(foundTasks);
        }
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
    public static String getErrorUnknownCommand() {
        return "I'm sorry, I don't recognize that command.\n"
                + "Valid commands are: list, mark <number>, unmark <number>, delete <number>, bye, \n"
                + "                    todo <description>, \n"
                + "                    deadline <description> /by <date/time>, \n"
                + "                    event <description> /from <from> /to <to>";
    }
}
