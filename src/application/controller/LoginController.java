package application.controller;

import application.dbTools.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.fetchData;
import static application.dbTools.Query.insertInto;

/**
 * Created by Pierre on 2015-11-06.
 */

public class LoginController implements Initializable {
    boolean adminrights = false;
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

    @FXML private Label WELCOMEPAGE;

    @FXML private Label showusername;

    @FXML private Button adminClick;

    public void loginviewButtonclick () {
        VistaNavigator.loadVista(VistaNavigator.LOGINVISTA);
    }

    public void adminClick(){
        VistaNavigator.loadVista(VistaNavigator.ADMINVIEW);
    }

    public void loginScreen () {

        if (isValidCredentials()) {
            VistaNavigator.loadVista(VistaNavigator.MYPAGE);
            LoginNavigator.loadLogin(LoginNavigator.LOGGEDIN);
            if (adminrights==true){
                VistaNavigator.loadVista(VistaNavigator.WELCOMEPAGE);
            }
        }

        else {
            System.out.println("Invalid Credentials");
        }

    }


    public void registerButtonclick() throws SQLException { //register part
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

        // ensure the users input is valid
    if (registerusernameField.getText().isEmpty()) {
            CheckMessage.setText("Username is invalid");

        } else {
        if (emailField.getText().isEmpty()) {
            CheckMessage.setText("E-mail is invalid");

        } else {
            if (registerPasswordField.getText().isEmpty()) {
                CheckMessage.setText("Please set a password");

            } else {
                if (usernameTaken) {
                    registerusernameField.clear();
                    CheckMessage.setText("Sorry,the Username is already been registered");
                } else {
                    if (emailTaken) {
                        emailField.clear();
                        CheckMessage.setText("Sorry,the email is already been registered");

                        //register successful
                    } else try {
                        System.out.println("- UpdateNewUser");

                        insertInto("Users", columns, values);

                        System.out.println("- End of UpdateNewUser");

                        System.out.println("You are registered,welcome");
                        VistaNavigator.loadVista(VistaNavigator.MYPAGE);

                    } catch (SQLException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
    }
                }

    public void mypagesClick(){
        VistaNavigator.loadVista(VistaNavigator.MYPAGE);
    }
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

                    if (checkUsername.equals("admin")){
                        System.out.println("AdminTools");
                        adminrights = true;
                    }

                } else if (!checkUsername.equals(username) || !checkPassword.equals(password)) {
                    usernameField.clear();
                    passwordField.clear();
                    CheckMessage.setText("Invalid credentials, please try again");}
            }
            else {
                usernameField.clear();
                passwordField.clear();
                CheckMessage.setText("Invalid credentials, please try again");



            }

            return log_in;

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return log_in;}

    public void buttonSignout(){

        if (isValidCredentials()) {


            System.out.println("You have been signed out");

            LoginNavigator.loadLogin(
                    LoginNavigator.LOGIN
            );
            VistaNavigator.loadVista(
                    VistaNavigator.LOGINVISTA
            );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}