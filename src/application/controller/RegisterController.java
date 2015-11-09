package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.*;


import java.sql.SQLException;

import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;

/**
 * Created by Pierre on 2015-11-09.
 */
public class RegisterController {

    MainController mainController;

    @FXML   private TextField registerName;
    @FXML   private PasswordField registerPassword;
    @FXML   private PasswordField confirmPassword;
    @FXML   private TextField registerEmail;



}
