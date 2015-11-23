package application.controller;

import application.dbTools.Query;
import com.sun.org.apache.bcel.internal.generic.Select;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;

/**
 * Created by Pierre on 2015-11-06.
 */

public class LoginController implements Initializable {

    @FXML
    private Label CheckMessage;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private MenuButton buttonLoggedin;
    @FXML
    private MenuItem buttonSignout;
    @FXML
    private TextField registerusernameField;
    @FXML
    private TextField registerPasswordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField emailField;

    public void handleButtonAction(ActionEvent event) throws IOException {

        Parent homepage = FXMLLoader.load(getClass().getResource("Homepage.fxml")); //access the fxml
        Scene homepagescene = new Scene(homepage); //ui elements
        Stage appstage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //scene visible to stage

        if (!isValidCredentials()) {   //username-password fields get cleared
            usernameField.clear();
            passwordField.clear();
        } else {   //main page is loaded
            appstage.setScene(homepagescene);
            appstage.show();
        }
    }

    public void loginviewButtonclick() {
        VistaNavigator.loadVista(VistaNavigator.LOGINVISTA);
    }

    public void loginScreen() {

        if (isValidCredentials()) {
            VistaNavigator.loadVista(VistaNavigator.WELCOME);
        } else {
            System.out.println("Invalid Credentials");
        }

    }

    //user register
    public void registerButtonclick() throws SQLException {

        //if users input invalid
        CheckMessage.setText("");

        String columns = "Username,Password,Email";     //Array with columns
        String values = "'" + registerusernameField.getText() + "','" + registerPasswordField.getText() + "','" + emailField.getText() + "'";
        ArrayList<ArrayList<String>> dataSet;

        // Check whether username already exists
        dataSet = Query.fetchData("Users", "Username", "Username = '" + registerusernameField.getText() + "'");
        boolean usernameTaken = dataSet.size() > 0;

        // Check whether email already exists
        dataSet = Query.fetchData("Users", "Email", "Email = '" + emailField.getText() + "'");
        boolean emailTaken = dataSet.size() > 0;

        // ensure the users
    if (registerusernameField.getText().isEmpty()) {
            CheckMessage.setText("Username is invalid");

        } else if (emailField.getText().isEmpty()) {
        CheckMessage.setText("E-mail is invalid");

        } else if(registerPasswordField.getText().isEmpty()){
        CheckMessage.setText("Please set a password");

        } else {
        if (usernameTaken) {
            registerusernameField.clear();
            CheckMessage.setText("Sorry,the Username is already been registered");
        } else {
            if (emailTaken) {
                emailField.clear();
                CheckMessage.setText("Sorry,the email is already been registered");

            } else {
                try {
                    System.out.println("- UpdateNewUser");

                    insertInto("Users", columns, values);

                    System.out.println("- End of UpdateNewUser");

                    System.out.println("You are registered,welcome");

                    VistaNavigator.loadVista(VistaNavigator.WELCOME);
                } catch (SQLException e) {
                    e.printStackTrace();

                }
                }
                }
                }
                }
                   private boolean isValidCredentials () {

                       boolean log_in = false;
                       String LoginUser = usernameField.getText(); //LoginUser is "username"+userinput
                       String LoginPass = passwordField.getText();//LoginPass is "password"+userinput

                       LoginUser = "Username='" + LoginUser + "'";
                       LoginPass = "Password='" + LoginPass + "'";

                       try {
                           String Username = fetchData("Users", "Username", LoginUser).toString();
                           ArrayList<ArrayList<String>> PasswordList = fetchData("Users", "Password", "Username = " + LoginUser);
                           String Password = PasswordList.get(0).get(0);
                           Username = Username.replaceAll("\\[", "").replaceAll("\\]", ""); //replacing sql chars
                           Password = Password.replaceAll("\\[", "").replaceAll("\\]", ""); //replacing sql chars


                           String CheckUsername = usernameField.getText().toString();
                           String CheckPassword = passwordField.getText().toString();

                           System.out.println("CheckUsername :" + CheckUsername); //checking username through intellij console
                           System.out.println("CheckPassword :" + CheckPassword); //checking password through intellij console


                           if (CheckPassword.equals(Password)) {

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
                       } catch (SQLException e) {
                           e.printStackTrace();
                       }

                       return log_in;
                   }

               public void buttonSignout () {




                   System.out.println("You have been signed out");

                   LoginNavigator.loadLogin(
                           LoginNavigator.LOGIN
                   );
                   VistaNavigator.loadVista(
                           VistaNavigator.LOGINVISTA
                   );
               }

               @Override
               public void initialize (URL url, ResourceBundle rb){

               }

           }