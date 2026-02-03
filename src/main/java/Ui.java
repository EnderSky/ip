import java.util.Scanner;
import java.util.ArrayList;

/**
 * Handles user interface interactions.
 * Handles all input and output to the user.
 */
public class Ui {

    private String logo;
    private Scanner scanner;

    /**
     * Constructor for Ui class.
     * 
     * @param logo
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
        Ui.showLine();
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
    public void showTasks(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toString());
        }
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
     * Displays an error message when the task list is empty.
     */
    public void showErrorEmptyList() {
        System.out.println("Your task list is currently empty.");
        System.out.println("Add tasks using the following commands: todo, deadline, event.");
    }

    /**
     * Displays an error message for incorrect format when marking a task.
     */
    public void showErrorIncorrectNumberFormat(String taskName) {
        System.out.println("Please provide the task number to {taskName} in the format: {taskName} <number>"
                .replace("{taskName}", taskName));
        Ui.showLine();
    }

    /**
     * Displays an error message for incorrect command format.
     * 
     * @param commandName name of the command
     * @param correctFormat the correct format for the command
     */
    public void showErrorIncorrectCommandFormat(String commandName, String correctFormat) {
        System.out.println("Please provide " + commandName + " in the format: " + correctFormat);
        Ui.showLine();
    }

    /**
     * Displays an error message for invalid task number.
     * 
     * @param taskName name of the task (mark / unmark / delete)
     * @param listSize size of the current task list
     */
    public void showErrorInvalidTaskNumber(String taskName, int listSize) {
        if (listSize == 0) {
            System.out.println("There are no tasks to " + taskName + ".");
        } else if (listSize == 1) {
            System.out.println("Invalid task number. The only valid task number is 1.");
        } else {
            System.out.println("Invalid task number. Valid task numbers are from 1 to "
                    + listSize + ".");
        }
    }

    /**
     * Displays an error message for empty input.
     * 
     * @param text
     */
    public void showErrorEmpty(String text) {
        System.out.println(text + " cannot be empty.");
        Ui.showLine();
    }

    public void showErrorInvalidDateTimeFormat(String dateTimeInput) {
        System.out.println("Invalid date/time format: " + dateTimeInput);
        System.out.println("Please use one of the following formats:");
        System.out.println("- DD/MM/YYYY HH:MM AM/PM (12-hour) or HHMM (24-hour)");
        System.out.println("- DD MMM YYYY HH:MM AM/PM (12-hour) or HHMM (24-hour)");
        System.out.println("- YYYY-MM-DD HH:MM AM/PM (12-hour) or HHMM (24-hour)");
        System.out.println("- Examples: 2/1/2024 6:00 PM, 2 Dec 2024 1830, 2024-12-25 08:00 am");
        Ui.showLine();
    }

    /**
     * Displays an error message for unknown command.
     */
    public void showErrorUnknownCommand() {
        System.out.println("I'm sorry, I don't recognize that command.");
        System.out.println(
                "Valid commands are: list, mark <number>, unmark <number>, delete <number>, bye, \n" +
                        "                    todo <description>, \n" +
                        "                    deadline <description> /by <date/time>, \n" +
                        "                    event <description> /from <from> /to <to>");
    }
}
