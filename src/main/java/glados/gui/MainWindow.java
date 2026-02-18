package glados.gui;

import glados.main.Glados;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Glados glados;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/chell.png"));
    private Image gladosImage = new Image(this.getClass().getResourceAsStream("/images/glados.jpg"));

    /**
     * Initializes the main window. Binds the scroll pane to the height of the
     * dialog container.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

    }

    /** Injects the Glados instance */
    public void setGlados(Glados g) {
        glados = g;
        DialogBox welcomeDialog = DialogBox.getGladosDialog(glados.getWelcomeMessage(), gladosImage,
                glados.getCommandType());
        dialogContainer.getChildren().add(welcomeDialog);
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Glados's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = glados.getResponse(input);
        String commandType = glados.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getGladosDialog(response, gladosImage, commandType));
        userInput.clear();

        if (commandType.equals("CommandBye")) {
            // Disable input after exit command
            userInput.setDisable(true);
            sendButton.setDisable(true);

            // Close the application after a short delay
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Wait for 2 seconds before closing
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }).start();
        }
    }
}
