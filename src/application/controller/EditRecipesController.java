package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.deleteFrom;
import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;


/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class EditRecipesController implements Initializable{

    MainController mainController;
    private String recipeID;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = VistaNavigator.getMainController();
        updateEditRecipeList();

    }

    public void updateEditRecipeList(){
        ArrayList<ArrayList<String>> dataSet;
        String currentRecipe = mainController.recipList.getSelectionModel().getSelectedItems().toString();
        currentRecipe = currentRecipe.substring(1, currentRecipe.length() - 1);

        String condition = "Name= '" + currentRecipe + "'";



        try {
            dataSet = Query.fetchData("recipes", "*", condition);

            System.out.println(mainController.recipList.getSelectionModel().getSelectedItems().toString());
            System.out.println(dataSet.get(0).get(1));

            recipeID = dataSet.get(0).get(0);
            recipeName.setText(dataSet.get(0).get(1));
            recipeType.setText(dataSet.get(0).get(2));
            recipeCuisine.setText(dataSet.get(0).get(3));
            recipeDifficulty.setText(dataSet.get(0).get(4));
            recipeTime.setText(dataSet.get(0).get(7));
            recipeDiet.setText(dataSet.get(0).get(6));
            recipeDescription.setText(dataSet.get(0).get(9));
            mainController.updateRecipList();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addRecipe(){
        mainController.updateRecipList();
        try {
         System.out.println("THIS IS THE BLOODY NAME " + recipeName.getText());
            insertInto("Recipes", "Name", "'--New--'");


            recipeName.setText("");
            recipeType.setText("");
            recipeCuisine.setText("");
            recipeDifficulty.setText("");
            recipeTime.setText("");
            recipeDiet.setText("");
            recipeDescription.setText("");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecipe(){
        mainController.updateRecipList();
        try {
            String criteria = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
            criteria = criteria.replaceAll("\\[", "").replaceAll("\\]","");
            deleteFrom("Recipes", criteria);
            System.out.println("THIS IS YOUR BLOODY CRITERIA " + criteria);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void SubmitButtonAction() {
       String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description"};
       String[] values = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
               recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText()};
       mainController.updateRecipList();
       try {
           String condition = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
           condition = condition.replaceAll("\\[", "").replaceAll("\\]","");
           updateData("Recipes", columns, values, condition);
           System.out.println("HERE'S YOUR BLOODY CONDITION " + condition);

       } catch (SQLException e) {
           e.printStackTrace();
       }
   }



}
