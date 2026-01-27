import java.util.ArrayList;

public class GLaDOS {

    private static ArrayList<Task> storedList;

    private static void intro() {
        String logo =   "  ____ _          ____   ___  ____  \r\n" + //
                        " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n" + //
                        "| |  _| |   / _` | | | | | | \\___ \\ \r\n" + //
                        "| |_| | |__| (_| | |_| | |_| |___) |\r\n" + //
                        " \\____|_____\\__,_|____/ \\___/|____/ \r\n" + //
                        "                                       ";
        System.out.println(logo);
        System.out.println("Hello, and again, welcome to the Aperture Science Computer-Aided Enrichment Center.");
        System.out.println("What can I do for you today?");
        GLaDOS.printLine();
    }

    private static void listen() {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;

        loop:
        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            
            GLaDOS.printLine();

            Command cmd = Command.fromInput(input);

            switch (cmd) {
                case BYE -> {
                    break loop;
                }
                case LIST -> {
                    if (storedList.size() == 0) {
                        System.out.println("Your task list is currently empty.");
                        System.out.println("Add tasks using the following commands: todo, deadline, event.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < storedList.size(); i++) {
                            System.out.println((i + 1) + ". " + storedList.get(i));
                        }
                    }
                }
                case MARK -> {
                    if (!input.matches("mark \\d+")) {
                        System.out.println("Please provide the task number to mark in the format: mark <number>");
                        GLaDOS.printLine();
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= storedList.size()) {
                        storedList.get(taskNumber - 1).markAsDone();
                        System.out.println("Nice! I've marked task " + taskNumber + " as done:");
                        System.out.println("  " + storedList.get(taskNumber - 1));
                    } else {
                        if (storedList.size() == 0) {
                            System.out.println("There are no tasks to unmark.");
                        } else if (storedList.size() == 1) {
                            System.out.println("Invalid task number. The only valid task number is 1.");
                        } else {
                            System.out.println("Invalid task number. Valid task numbers are from 1 to " + storedList.size() + ".");
                        }
                    }
                }
                case UNMARK -> {
                    if (!input.matches("unmark \\d+")) {
                        System.out.println("Please provide the task number to unmark in the format: unmark <number>");
                        GLaDOS.printLine();
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= storedList.size()) {
                        storedList.get(taskNumber - 1).unmarkAsNotDone();
                        System.out.println("OK, I've marked task " + taskNumber + " as not done yet:");
                        System.out.println("  " + storedList.get(taskNumber - 1));
                    } else {
                        if (storedList.size() == 0) {
                            System.out.println("There are no tasks to unmark.");
                        } else if (storedList.size() == 1) {
                            System.out.println("Invalid task number. The only valid task number is 1.");
                        } else {
                            System.out.println("Invalid task number. Valid task numbers are from 1 to " + storedList.size() + ".");
                        }
                    }
                }
                case TODO -> {
                    if (!input.matches("todo .*")) {
                        System.out.println("Please provide a description for the todo task in the format: todo <description>");
                        GLaDOS.printLine();
                        continue;
                    }
                    String description = input.substring(4).trim();
                    if (description.isEmpty()) {
                        System.out.println("Description of a todo cannot be empty.");
                        GLaDOS.printLine();
                        continue;
                    }
                    Task t = new Todo(description);
                    GLaDOS.addTask(t);
                }
                case DEADLINE -> {
                    if (!input.matches("deadline .* /by .*")) {
                        System.out.println("Please provide deadline in the format: deadline <description> /by <deadline>");
                        GLaDOS.printLine();
                        continue;
                    }
                    String[] parts = input.substring(8).split(" /by ", 2);
                    String description = parts[0].trim();
                    String by = parts[1].trim();
                    if (description.isEmpty()) {
                        System.out.println("Description of a deadline cannot be empty.");
                        GLaDOS.printLine();
                        continue;
                    }
                    if (by.isEmpty()) {
                        System.out.println("Deadline time cannot be empty.");
                        GLaDOS.printLine();
                        continue;
                    }
                    Deadline t = new Deadline(description, by);
                    GLaDOS.addTask(t);
                }
                case EVENT -> {
                    if (!input.matches("event .* /from .* /to .*")) {
                        System.out.println("Please provide event in the format: event <description> /from <from> /to <to>");
                        GLaDOS.printLine();
                        continue;
                    }
                    String[] parts = input.substring(6).split(" /from | /to ", 3);
                    String description = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    if (description.isEmpty()) {
                        System.out.println("Description of an event cannot be empty.");
                        GLaDOS.printLine();
                        continue;
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        System.out.println("From and to times of an event cannot be empty.");
                        GLaDOS.printLine();
                        continue;
                    }
                    Event t = new Event(description, from, to);
                    GLaDOS.addTask(t);
                }
                case DELETE -> {
                    if (!input.matches("delete \\d+")) {
                        System.out.println("Please provide the task number to delete in the format: delete <number>");
                        GLaDOS.printLine();
                        continue;
                    }
                    int taskNumber = Integer.parseInt(input.split(" ")[1]);
                    if (taskNumber > 0 && taskNumber <= storedList.size()) {
                        Task removedTask = storedList.remove(taskNumber - 1);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        if (storedList.size() == 1) {
                            System.out.println("Now you have 1 task in the list.");
                        } else {
                            System.out.println("Now you have " + storedList.size() + " tasks in the list.");
                        }
                    } else {
                        if (storedList.size() == 0) {
                            System.out.println("There are no tasks to delete.");
                        } else if (storedList.size() == 1) {
                            System.out.println("Invalid task number. The only valid task number is 1.");
                        } else {
                            System.out.println("Invalid task number. Valid task numbers are from 1 to " + storedList.size() + ".");
                        }
                    }
                }
                case UNKNOWN -> {
                    System.out.println("I'm sorry, I don't recognize that command.");
                    System.out.println("Valid commands are: list, mark <number>, unmark <number>, delete <number>, bye, \n" + 
                    "                    todo <description>, \n" + 
                    "                    deadline <description> /by <deadline>, \n" +
                    "                    event <description> /from <from> /to <to>");
                }
            }
            
            GLaDOS.printLine();
        }
        scanner.close();
    }

    private static void addTask(Task t) {
        GLaDOS.storedList.add(t);
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + t);
        if (storedList.size() == 1) {
            System.out.println("Now you have 1 task in the list.");
        } else {
            System.out.println("Now you have " + storedList.size() + " tasks in the list.");
        }
    } 

    private static void outro() {
        System.out.println("Goodbye. Thank you for participating in this Aperture Science test.");
        GLaDOS.printLine();
    }

    private static void printLine() {
        //String lineChar = "\u2500"; 
        String lineChar = "-";
        for (int i = 0; i < 83; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GLaDOS.storedList = new ArrayList<>();
        GLaDOS.intro();
        GLaDOS.listen();
        GLaDOS.outro();
    }
}
