package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Pierre on 2015-11-06.
 */
public class LoginController {

    LoginController loginController;

    @FXML   private TextField usernameField;
    @FXML   private PasswordField passwordField;
    @FXML   private Button loginButton;
    @FXML   private Button registerButton;
    @FXML   private MenuButton buttonLoggedin;
    @FXML   private MenuItem buttonSettings;
    @FXML   private MenuItem buttonSignout;
    @FXML   private Button loginviewButton;

    public void registerButtonclick(){
        VistaNavigator.loadVista(
               VistaNavigator.REGISTER
        );
    }

    public void loginviewButtonclick(){
        VistaNavigator.loadVista(
                VistaNavigator.LOGINVISTA
        );
    }
}