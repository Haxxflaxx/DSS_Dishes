package application.controller;

import application.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import java.io.IOException;

/**
 * Created by Haxxflaxx on 2015-11-02.
 */


/**
 * Utility class for controlling navigation between vistas.
 *
 * All methods on the navigator are static to facilitate
 * simple access from anywhere in the application.
 */
public class LoginNavigator {
    @FXML
    private Button loginviewButton;
    @FXML
    private Button yesSignout;
    @FXML
    private Button cancelSignout;
    @FXML
    private MenuItem signoutView;


    /**
     * Constants holding path and name for views.
     */
    public static final String LOGIN = "/application/view/loginView.fxml";
    public static final String LOGGEDIN = "/application/view/loggedinView.fxml";

    /** The main application layout controller. */
    private static MainController mainController;

    /**
     * Stores the main controller for later use in navigation tasks.
     *
     * @param loginController the main application layout controller.
     */
    public static void setLoginController(MainController loginController){
        LoginNavigator.mainController = loginController;
    }

    /**
     * Returns the main controller.
     *
     * @return MainController the main application layout controller.
     */
    public static MainController getLoginController(){
        return mainController;
    }

    /**
     * Loads the vista specified by the fxml file into the
     * vistaHolder pane of the main application layout.
     *
     * Previously loaded vista for the same fxml file are not cached.
     * The fxml is loaded anew and a new vista node hierarchy generated
     * every time this method is invoked.
     *
     * A more sophisticated load function could potentially add some
     * enhancements or optimizations, for example:
     *   cache FXMLLoaders
     *   cache loaded vista nodes, so they can be recalled or reused
     *   allow a user to specify vista node reuse or new creation
     *   allow back and forward history like a browser
     *
     * @param fxml the fxml file to be loaded.
     */
    public static void loadLogin(String fxml) {
        try {
            mainController.setLogin(

                    FXMLLoader.load(
                            LoginNavigator.class.getResource(
                                    fxml
                            )
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that loads the loginVista
     * By Fredrik Rissanen
     */
    public void loginviewButtonclick () {
        VistaNavigator.loadVista(VistaNavigator.LOGINVISTA);
    }

    /**
     * Method that loads the signoutView
     * By Fredrik Rissanen
     */
    public void signoutView(){
        VistaNavigator.loadVista(VistaNavigator.LOGOUT);
    }

    /**
     * Method for signing out
     * By Fredrik Rissanen
     */
    public void signoutButton(){
        mainController = VistaNavigator.getMainController();
        User.setName("");
        User.setId("");
        User.setPrivilege("0");
        mainController.loginStatus();
        VistaNavigator.loadVista(VistaNavigator.SEARCH);
        LoginNavigator.loadLogin(LoginNavigator.LOGIN);
    }

    /**
     * Method for canceling sign out
     * By Fredrik Rissanen
     */
    public void cancelSignout(){
        VistaNavigator.loadVista(VistaNavigator.SEARCH);
    }

}