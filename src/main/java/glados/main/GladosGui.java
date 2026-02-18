package glados.main;

import glados.gui.DialogBox;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class to launch the GLaDOS GUI application.
 */
public class GladosGui extends Application {

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userImage;
    private Image gladosImage;
    private GladosCli glados;

    /**
     * Constructor for GladosGui.
     * Initializes the GLaDOS CLI application and loads images for the GUI.
     */
    public GladosGui() {
        String filepath = "../../../data/tasks.txt";
        String logo = "  ____ _          ____   ___  ____  \r\n"
                + " / ___| |    __ _|  _ \\ / _ \\/ ___| \r\n"
                + "| |  _| |   / _` | | | | | | \\___ \\ \r\n"
                + "| |_| | |__| (_| | |_| | |_| |___) |\r\n"
                + " \\____|_____\\__,_|____/ \\___/|____/ \r\n"
                + "                                       ";

        this.userImage = new Image(this.getClass().getResourceAsStream("/images/chell.png"));
        this.gladosImage = new Image(this.getClass().getResourceAsStream("/images/glados.jpg"));
        this.glados = new GladosCli(filepath, logo);
    }

    /**
     * Start the JavaFX application.
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage stage) {

        // Setting up required components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        DialogBox dialogBox = new DialogBox("Hello!", userImage);
        dialogContainer.getChildren().addAll(dialogBox);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        // Formatting the window to look as expected
        stage.setTitle("GLaDOS");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Showing the stage
        stage.setScene(scene);
        stage.show();

        // Handling user input
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));
    }

    /**
     * Creates a dialog box containing user input, and appends it to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        String userText = userInput.getText();
        String gladosText = this.glados.getResponse(userInput.getText());
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getGladosDialog(gladosText, gladosImage)
        );
        userInput.clear();
    }

    

    public static void main(String[] args) {
        launch(args);
    }

}
