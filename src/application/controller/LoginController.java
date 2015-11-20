package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
     * @return
     */
    private boolean isValidCredentials(){

        ArrayList<ArrayList<String>> dataSet;


        boolean log_in = false;
        String loginCondition = usernameField.getText(); //user input in the username field
        loginCondition = "Username='" + loginCondition + "'";

        try {

            String testlogin = fetchData("Users", "Username", loginCondition).toString();//fetching data from column users in database


            dataSet = fetchData("Users", "*", loginCondition);
            String checkUsername = usernameField.getText();
            String checkPassword = passwordField.getText();

            if (!testlogin.equals("[]")) {

                String username = (dataSet.get(0).get(0));
                String password = (dataSet.get(0).get(1));


                System.out.println("testerror " + username);


                if (checkUsername.equals(username) && checkPassword.equals(password)) {

                    System.out.println("You are logged in!");
                    log_in = true;

                } else if (!checkUsername.equals(username) || !checkPassword.equals(password)) {
                    System.out.println("Please enter username again");
                    usernameField.clear();
                    passwordField.clear();
                    CheckMessage.setText("Invalid Credentials, please try again bitch");}
                }
                 else {
                    //clear fields if user input isn't correct
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}