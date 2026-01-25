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

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            
            GLaDOS.printLine();

            // If input matches exit command, stop listening
            if (input.equals("bye")) {
                break;
            }
            
            // If input matches list command, print list of stored text
            if (input.equals("list")) {
                // Print stored list
                for (int i = 0; i < storedList.size(); i++) {
                    System.out.println((i + 1) + ". " + storedList.get(i));
                }
            } 
            // input matches "mark <number>" command
            else if (input.matches("mark \\d+")) { 
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber > 0 && taskNumber <= storedList.size()) { // input sanitization
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
            // input matches "unmark <number>" command
            else if (input.matches("unmark \\d+")) { 
                int taskNumber = Integer.parseInt(input.split(" ")[1]);
                if (taskNumber > 0 && taskNumber <= storedList.size()) { // input sanitization
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
            // input matches "todo <description>" command
            else if (input.startsWith("todo")) {
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
            //input matches "deadline <description> /by <deadline>" command
            else if (input.startsWith("deadline")) {
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
            //input matches "event <description> /from <from> /to <to>" command
            else if (input.startsWith("event")) {
                if (!input.matches("event .* /from .* /to .*")) {
                    System.out.println("Please provide event in the format: event <description> /from <from> /to <to>");
                    GLaDOS.printLine();
                    continue;
                }
                String[] parts = input.substring(6).split(" /from | /to ", 3);
                String description = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                // error handling
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
            // else add input to list
            else {
                // handle errors for unrecognized commands
                System.out.println("I'm sorry, I don't recognize that command.");
                System.out.println("Valid commands are: list, mark <number>, unmark <number>, bye, todo <description>, \n" + 
                "                    deadline <description> /by <deadline>, \n" +
                "                    event <description> /from <from> /to <to>");
                // funny error handling responses
                // String[] responses = {
                //     "That doesn't seem to be a valid command. Intelligence is always running after you, but you’re faster.",
                //     "I'm sorry, I don't understand that. I was going to challenge you to a battle of wits but I see you are unarmed.",
                //     "That command is not recognized. If I agreed with you, we'd both be wrong."
                // };
                // int randIndex = (int) (Math.random() * responses.length);
                // System.out.println(responses[randIndex]);
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
