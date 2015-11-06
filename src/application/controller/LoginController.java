package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ListView;

import application.dbTools.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Pierre on 2015-11-06.
 */
public class LoginController {

    @FXML   private TextField usernameField;
    @FXML   private PasswordField passwordField;
    @FXML   private Button loginButton;
    @FXML   private Button registerButton;
}
