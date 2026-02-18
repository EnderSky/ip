package glados.main;

import java.io.IOException;

import glados.gui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class to launch the GLaDOS GUI application.
 */
public class GladosGui extends Application {

    private String filepath = "./data/tasks.txt";
    private String logo = "  ____ _          ____   ___  ____  \r\n"
            + " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n"
            + "| |  _| |   / _` | | | | | | \\___ \\ \r\n"
            + "| |_| | |__| (_| | |_| | |_| |___) |\r\n"
            + " \\____|_____\\__,_|____/ \\___/|____/ \r\n"
            + "                                       ";
    private Glados glados = new Glados(this.filepath, this.logo);

    /**
     * Starts the GLaDOS GUI application by setting up the main window, layout, and
     * event handlers.
     *
     * @param stage The primary stage for this application, onto which the
     *              application scene can be set.
     */
    @Override
    public void start(Stage stage) {
        try {
            Image icon = new Image(this.getClass().getResourceAsStream("/images/aperture-science-logo.png"));
            FXMLLoader fxmlLoader = new FXMLLoader(GladosGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("GLaDOS");
            stage.getIcons().add(icon);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setGlados(this.glados); // inject the GLaDOS instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
