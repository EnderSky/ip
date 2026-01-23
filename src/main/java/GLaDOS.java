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
        System.out.println("___________________________________________________________________________________\n"); 
    }

    public static void listen() {
        String exitCommand = "bye";

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine();
            
            // If input matches exit command, stop listening
            if (input.equals(exitCommand)) {
                break;
            }
            
            // Else echo the input
            System.out.println("___________________________________________________________________________________");
            System.out.println(input);
            System.out.println("___________________________________________________________________________________\n");
        }
        scanner.close();
    }

    public static void outro() {
        System.out.println("___________________________________________________________________________________");
        System.out.println("Goodbye. Thank you for participating in this Aperture Science test.");
        System.out.println("___________________________________________________________________________________");
    }

    public static void main(String[] args) {
        GLaDOS.intro();
        GLaDOS.listen();
        GLaDOS.outro();
    }
}
