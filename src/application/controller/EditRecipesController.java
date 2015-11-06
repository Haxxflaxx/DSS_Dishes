package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.*;


import java.sql.SQLException;

import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;


/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class EditRecipesController  {


    @FXML
    private TextField recipeName;
    @FXML
    private TextField recipeType;
    @FXML
    private   TextField recipeCuisine;
    @FXML
    private  TextField recipeDifficulty;
    @FXML
    private TextField recipeDiet;
    @FXML
    private TextField recipeTime;
    @FXML
    private TextField recipeDescription;
    @FXML
    private TextField recipeIngredients;
    @FXML
    private Button recipeSubmit;




   private void handleButton(){

            try {

                insertInto("Recipes", "Name", recipeName.toString());
                insertInto("Recipes", "Type", recipeType.toString());
                insertInto("Recipes", "Cuisine", recipeCuisine.toString());
                insertInto("Recipes", "Difficulty", recipeDifficulty.toString());
                insertInto("Recipes", "Diet", recipeDiet.toString());
                insertInto("Recipes", "Time", recipeTime.toString());
                insertInto("Recipes", "Description", recipeDescription.toString());

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}
