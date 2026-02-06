/**
 * Main application class for GLaDOS task manager.
 */
public class Glados {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;

    /**
     * Runs the main application loop.
     */
    public void run() {
        this.ui.showWelcomeMessage();

        String input;
        Boolean isExit = false;

        while (!isExit) {
            input = this.ui.getUserInput();
            Ui.showLine();
            
            try {
                Command cmd = InputParser.parseInput(input);
                cmd.execute(this.taskList, this.ui);
                isExit = cmd.isExit();
            } catch (GladosException e) {
                this.ui.showError(e.getMessage());
            } finally {
                Ui.showLine();
            }
        }
    }

    /**
     * Constructor for GLaDOS application.
     * Initializes UI, Storage, and TaskList components.
     * 
     * @param filePath File path for storing tasks.
     * @param logo Logo string to display on startup.
     */
    public Glados(String filePath, String logo) {
        this.ui = new Ui(logo);
        this.storage = new Storage(filePath);
        this.taskList = new TaskList(this.storage);
    }

    /**
     * Main method to start the GLaDOS application.
     * 
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        final String filePath = "../../../data/tasks.txt";
        final String LOGO = "  ____ _          ____   ___  ____  \r\n" + //
                " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n" + //
                "| |  _| |   / _` | | | | | | \\___ \\ \r\n" + //
                "| |_| | |__| (_| | |_| | |_| |___) |\r\n" + //
                " \\____|_____\\__,_|____/ \\___/|____/ \r\n" + //
                "                                       ";

        Glados app = new Glados(filePath, LOGO);
        app.run();
    }
}
