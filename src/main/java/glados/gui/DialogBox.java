package glados.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * DialogBox class to represent a dialog box in the GLaDOS GUI application.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructor for DialogBox.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed in the dialog box.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Factory method to create a DialogBox for GLaDOS's response. Flips the dialog
     * box so that the image is on the left and text on the right.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed in the dialog box.
     * @return A DialogBox instance with the specified text and image, flipped.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Changes the style of the dialog box based on the type of command executed.
     *
     * @param commandType The type of command executed, used to determine dialog
     *                    style.
     */
    private void changeDialogStyle(String commandType) {
        switch (commandType) {
            case "CommandAddTask" -> dialog.getStyleClass().add("add-label");
            case "CommandMark" -> dialog.getStyleClass().add("marked-label");
            case "CommandDelete" -> dialog.getStyleClass().add("delete-label");
            case "Error" -> dialog.getStyleClass().add("error-label");
            default -> {
            }
        }
    }

    /**
     * Factory method to create a DialogBox for GLaDOS's response. Flips the dialog
     * box so that the image is on the left and text on the right.
     *
     * @param text        The text to be displayed in the dialog box.
     * @param img         The image to be displayed in the dialog box.
     * @param commandType The type of command executed, used to determine dialog
     *                    style.
     * @return A DialogBox instance with the specified text and image, flipped and
     *         styled.
     */
    public static DialogBox getGladosDialog(String text, Image img, String commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }
}
