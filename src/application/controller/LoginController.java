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

    private boolean isValidCredentials(){

        boolean log_in = false;
        String LoginUser = usernameField.getText().toString(); //LoginUser is "username"+userinput
        String LoginPass= passwordField.getText().toString();//LoginPass is "password"+userinput

        LoginUser = "Username='" + LoginUser + "'";
        LoginPass = "Password='" + LoginPass + "'";

        try {
            String Username = fetchData("Users", "Username", LoginUser).toString();
            String Password = fetchData("Users", "Password", LoginPass).toString();


            Username = Username.replaceAll("\\[", "").replaceAll("\\]", ""); //replacing sql chars
            Password = Password.replaceAll("\\[", "").replaceAll("\\]", ""); //replacing sql chars


            String CheckUsername = usernameField.getText().toString();
            String CheckPassword = passwordField.getText().toString();

            System.out.println("CheckUsername :" + CheckUsername); //checking username through intellij console
            System.out.println("CheckPassword :" + CheckPassword); //checking password through intellij console


                if (CheckUsername.equals(Username) && CheckPassword.equals(Password)) {

                    System.out.println("You are logged in!");
                    log_in = true;

                    LoginNavigator.loadLogin(
                            LoginNavigator.LOGGEDIN
                    );


                } else {
                    usernameField.clear();
                    passwordField.clear();
                    CheckMessage.setText("Wrong Username or Password");
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