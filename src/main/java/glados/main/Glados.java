package glados.main;

import glados.command.Command;
import glados.parser.InputParser;
import glados.task.TaskList;
import glados.utils.GladosException;
import glados.utils.Storage;
import glados.utils.Ui;

/**
 * Main application class for GLaDOS task manager.
 */
public class Glados {

    private Ui ui;
    private Storage storage;
    private TaskList taskList;
    private String commandType;

    /**
     * Constructor for GLaDOS application.
     * Initializes UI, Storage, and TaskList components.
     *
     * @param filePath File path for storing tasks.
     * @param logo     Logo string to display on startup.
     */
    public Glados(String filePath, String logo) {
        this.storage = new Storage(filePath);
        this.ui = new Ui(logo);
        this.taskList = new TaskList(this.storage);
        this.commandType = "";
    }

    /**
     * Runs the main application loop.
     */
    public void run() {
        this.ui.showMessage(this.ui.getWelcomeMessage());
        Ui.showLine();

        String input;
        Boolean isExit = false;

        while (!isExit) {
            input = this.ui.getUserInput();
            Ui.showLine();

            try {
                Command cmd = InputParser.parseInput(input);
                String response = cmd.execute(this.taskList, this.ui);
                this.ui.showMessage(response);
                isExit = cmd.isExit();
            } catch (GladosException e) {
                this.ui.showError(e.getMessage());
            } finally {
                Ui.showLine();
            }
        }
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user's input message.
     * @return The response message from GLaDOS.
     */
    public String getResponse(String input) {
        try {
            Command cmd = InputParser.parseInput(input);
            this.commandType = cmd.getClass().getSimpleName();
            return cmd.execute(this.taskList, this.ui);
        } catch (GladosException e) {
            this.commandType = "Error";
            return e.getMessage();
        }
    }

    public String getCommandType() {
        return this.commandType;
    }

    /**
     * Gets the welcome message.
     *
     * @return The welcome message to be displayed on startup.
     */
    public String getWelcomeMessageNoLogo() {
        return this.ui.getWelcomeMessageNoLogo();
    }

    public static void main(String[] args) {
        String filepath = "./data/tasks.txt";
        String logo = "  ____ _          ____   ___  ____  \r\n"
                + " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n"
                + "| |  _| |   / _` | | | | | | \\___ \\ \r\n"
                + "| |_| | |__| (_| | |_| | |_| |___) |\r\n"
                + " \\____|_____\\__,_|____/ \\___/|____/ \r\n"
                + "                                       ";

        Glados app = new Glados(filepath, logo);
        app.run();
    }
}
