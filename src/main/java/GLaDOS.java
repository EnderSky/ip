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
                    System.out.println("Invalid task number.");
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
                    System.out.println("Invalid task number.");
                }
            }
            // input matches "todo <description>" command
            else if (input.matches("todo .*")) {
                String description = input.substring(4).trim();
                Task t = new Todo(description);
                GLaDOS.addTask(t);
            }
            //input matches "deadline <description> /by <deadline>" command
            else if (input.matches("deadline .* /by .*")) {
                String[] parts = input.substring(8).split(" /by ", 2);
                String description = parts[0].trim();
                String by = parts[1].trim();
                Deadline t = new Deadline(description, by);
                GLaDOS.addTask(t);
            }
            //input matches "event <description> /from <from> /to <to>" command
            else if (input.matches("event .* /from .* /to .*")) {
                String[] parts = input.substring(6).split(" /from | /to ", 3);
                String description = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                Event t = new Event(description, from, to);
                GLaDOS.addTask(t);
            }
            // else add input to list
            else {
                Task t = new Task(input);
                GLaDOS.addTask(t);
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
