package application.controller;

import application.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Frank on 07/12/15.
 */
public class MyRecipeController implements Initializable {
    @FXML
    private Label myUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myUsername.setText(User.getName() + " recipes");
    }
}
