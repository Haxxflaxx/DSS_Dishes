package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.fetchData;

/**
 * Created by Pierre on 2015-11-06.
 */

public class LoginController implements Initializable {

    @FXML private Label CheckMessage;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private MenuButton buttonLoggedin;
    @FXML private MenuItem buttonSignout;
    @FXML private TextField registerusernameField;
    @FXML private TextField registerPasswordField;
    @FXML private TextField confirmPasswordField;
    @FXML private TextField emailField;

     private void handleButtonAction (ActionEvent event) throws IOException {

        Parent homepage = FXMLLoader.load(getClass().getResource("Homepage.fxml")); //access the fxml
        Scene homepagescene = new Scene(homepage); //ui elements
        Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //scene visible to stage

        if (isValidCredentials()) {   //main page is loaded
            appstage.setScene(homepagescene);
            appstage.show();
        }

        else {   //username-password fields get cleared
            usernameField.clear();
            passwordField.clear();
        }
    }

    public void loginviewButtonclick () {
        VistaNavigator.loadVista(VistaNavigator.LOGINVISTA);
    }

    public void loginScreen () {

        if (isValidCredentials()) {
            VistaNavigator.loadVista(VistaNavigator.MAIN);
        }

        else {
            System.out.println("Invalid Credentials");
        }

    }

    public void registerButtonclick () {
        VistaNavigator.loadVista(VistaNavigator.REGISTER);
    }

    /**
     * Method which gives the user accessibility to his profile
     */
    private boolean isValidCredentials(){

        ArrayList<ArrayList<String>> dataSet;

        boolean log_in = false;
        String loginCondition = usernameField.getText(); //user input in the username field
        loginCondition = "Username='" + loginCondition + "'";

        try {

            String testlogin = fetchData("Users", "Username", loginCondition).toString(); //fetching data from username column in database


            dataSet = fetchData("Users", "*", loginCondition);
            String checkUsername = usernameField.getText(); //string which will allow us to test if the user input is correct
            String checkPassword = passwordField.getText(); //string which will allow us to test if the user input is correct

            if (!testlogin.equals("[]")) //if username or password isn't in the database, system prompts user to type the right ones
            {

                String username = (dataSet.get(0).get(0)); //checking the username column in table users
                String password = (dataSet.get(0).get(1)); //checking the password column in table users

                if (checkUsername.equals(username) && checkPassword.equals(password))//if credentials are correct, user logs in
                {

                    System.out.println("You are logged in!");
                    log_in = true;

                } else if (!checkUsername.equals(username) || !checkPassword.equals(password)) {
                    usernameField.clear();
                    passwordField.clear();
                    CheckMessage.setText("Invalid credentials, please try again");}
                }
                 else {
                    usernameField.clear();
                    passwordField.clear();
                    CheckMessage.setText("Invalid credentials, please try again");
                    LoginNavigator.loadLogin(
                            LoginNavigator.LOGGEDIN
                    );


                }

                return log_in;

            }
                catch(SQLException e){
                    e.printStackTrace();
                }

    return log_in;}

    public void buttonSignout(){

            System.out.println("You have been signed out");

            LoginNavigator.loadLogin(
                    LoginNavigator.LOGIN
            );
            VistaNavigator.loadVista(
                    VistaNavigator.LOGINVISTA
            );

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}