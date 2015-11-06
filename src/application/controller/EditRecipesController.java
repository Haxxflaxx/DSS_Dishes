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

    MainController mainController;

    @FXML
    private TextField recipeName;
    @FXML
    private TextField recipeType;
    @FXML
    private TextField recipeCuisine;
    @FXML
    private TextField recipeDifficulty;
    @FXML
    private TextField recipeDiet;
    @FXML
    private TextField recipeTime;
    @FXML
    private TextArea recipeDescription;
    @FXML
    private ListView recipeIngredients;
    @FXML
    private Button recipeSubmit;




   public void handleButton(){
      String newRecipe = mainController.recipList.getSelectionModel().getSelectedItems().toString();
       String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description"};
       String[] values = {recipeName.toString(), recipeType.toString(), recipeCuisine.toString(), recipeDifficulty.toString(),
               recipeDiet.toString(), recipeTime.toString(), recipeDescription.toString()};

            try {
                String condition = "ID='"+ Query.fetchData("Recipes", "ID", "Name ='" + newRecipe + "'") +"'";

                updateData("Recipes",columns,values , condition);



            } catch (SQLException e) {
                e.printStackTrace();
            }

    }
}
