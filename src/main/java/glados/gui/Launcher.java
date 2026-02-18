package glados.gui;

import glados.main.GladosGui;
import javafx.application.Application;

/**
 * Launcher class for the GLaDOS GUI application
 * to workaround classpath issues
 */
public class Launcher {

    /**
     * Main method to launch the application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(GladosGui.class, args);
    }
}
