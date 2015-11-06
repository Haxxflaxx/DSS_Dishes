package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.scene.control.*;


import java.sql.SQLException;

import static application.dbTools.Query.deleteFrom;
import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;


/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class EditRecipesController{

    MainController mainController;

    @FXML
    public TextField recipeName;
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




    public void addRecipe(){
        try {
         System.out.println("THIS IS THE BLOODY NAME " + recipeName.getText());
            insertInto("Recipes", "Name", "'" + recipeName.getText() + "'");

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(){


        try {

            String criteria = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
            System.out.println("criteria" + criteria);
            deleteFrom("Recipes", criteria);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


   public void SubmitButtonAction() {
       String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description"};
       String[] values = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
               recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText()};

       try {
           String condition = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
           updateData("Recipes", columns, values, condition);
           System.out.println("HERE'S YOUR BLOODY CONDITION " + condition);


       } catch (SQLException e) {
           e.printStackTrace();
       }
   }


}
