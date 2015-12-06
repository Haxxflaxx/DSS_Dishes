package application.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * Created by Nikos on 27/11/2015.
 */
public class LogoutController{

    @FXML
    private MenuButton buttonLoggedin;

    @FXML
    private MenuItem buttonSignout;

    @FXML
    private Label WELCOMEPAGE;

    @FXML
    private Label showusername;

    @FXML
    private Button yesSignout;

    @FXML
    private Button cancelSignout;


    public boolean buttonSignout() {

        System.out.println("Are you sure?");

       /* if (yesSignout) {

            LoginController2.loadLogin(
                    LoginController2.LOGIN
            );
            VistaNavigator.loadVista(
                    VistaNavigator.LOGINVISTA
            );

        }*/
        return true;
    }

}

