package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class EditRecipesController  {


    @FXML
    TextField recipeName;
    @FXML
    TextField recipeType;
    @FXML
    TextField recipeCuisine;
    @FXML
    TextField recipeDifficulty;
    @FXML
    TextField recipeDiet;
    @FXML
    TextField recipeTime;
    @FXML
    TextField recipeDescription;
    @FXML
    TextField recipeIngredients;




    private void addIngredients() {
    

    }
}
