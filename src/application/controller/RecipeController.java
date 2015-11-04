package application.controller;

import application.dbTools.Query;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController {

    MainController mainController;


    @FXML    private Label recipeID;
    @FXML    private Label recipeName;
    @FXML    private Label recipeType;
    @FXML    private Label recipeCuisine;
    @FXML    private Label recipeDifficulty;
    @FXML    private Label recipeTIme;
    @FXML    private Label recipeDiet;
    @FXML    private TextArea recipeDescription;
    @FXML    private ListView recipeIngredients;


    //TODO: Add control id for items in view.

    /**
     * On view creation:
     * Get controller for main view.
     * Run update.
     */
    public RecipeController(){
        mainController = VistaNavigator.getMainController();
        updateContent();
    }

    /**
     * Fetches data from the database based on selected item in
     * MainController.recipList and puts the data into the fields for the view.
     */
    private void updateContent(){
        String condition = "'Name=" + mainController.recipList.getSelectionModel().getSelectedItems() + "'";
        try {
            ArrayList<ArrayList<String>> dataSet = Query.fetchData("recipes", "*", condition);

            System.out.println(mainController.recipList.getSelectionModel().getSelectedItems().toString());
            //TODO: Move data from dataSet into view fields
            recipeID.setText(dataSet.get(0).get(0));
            recipeName.setText(dataSet.get(0).get(1));
            recipeType.setText(dataSet.get(0).get(2));
            recipeCuisine.setText(dataSet.get(0).get(3));
            recipeDifficulty.setText(dataSet.get(0).get(4));
            recipeTIme.setText(dataSet.get(0).get(7));
            recipeDiet.setText(dataSet.get(0).get(6));
            recipeDescription.setText(dataSet.get(0).get(9));
            //recipeIngredients.setAccessibleText(dataSet.get(0).get(0));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
