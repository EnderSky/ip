import java.util.ArrayList;

public class GLaDOS {
    public static void intro() {
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

    public static void listen() {
        String exitCommand = "bye";
        String listCommand = "list";
        ArrayList<Task> storedList = new ArrayList<>();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            
            GLaDOS.printLine();

            // If input matches exit command, stop listening
            if (input.equals(exitCommand)) {
                break;
            }
            
            // If input matches list command, print list of stored text
            if (input.equals(listCommand)) {
                // Print stored list
                for (int i = 0; i < storedList.size(); i++) {
                    System.out.println((i + 1) + ". " + storedList.get(i));
                }
            } else {
                // Else add input to list
                Task t = new Task(input);
                storedList.add(t);
                System.out.println("added: " + input);
            }
            
            GLaDOS.printLine();
        }
        scanner.close();
    }

    public static void outro() {
        System.out.println("Goodbye. Thank you for participating in this Aperture Science test.");
        GLaDOS.printLine();
    }

    public static void printLine() {
        String lineChar = "\u2500";
        for (int i = 0; i < 83; i++) {
            System.out.print(lineChar);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GLaDOS.intro();
        GLaDOS.listen();
        GLaDOS.outro();
    }
}
