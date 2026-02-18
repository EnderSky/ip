package glados.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Node;

/**
 * DialogBox class to represent a dialog box in the GLaDOS GUI application.
 */
public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    /**
     * Constructor for DialogBox.
     *
     * @param s The text to display in the dialog box.
     * @param i The image to display in the dialog box.
     */
    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        //Styling the dialog box
        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Factory method to create a DialogBox for user input.
     *
     * @param s The text to display in the dialog box.
     * @param i The image to display in the dialog box.
     * @return A DialogBox instance representing user input.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    /**
     * Factory method to create a DialogBox for GLaDOS response.
     *
     * @param s The text to display in the dialog box.
     * @param i The image to display in the dialog box.
     * @return A DialogBox instance representing GLaDOS response.
     */
    public static DialogBox getGladosDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}