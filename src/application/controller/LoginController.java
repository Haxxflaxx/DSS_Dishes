package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;


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