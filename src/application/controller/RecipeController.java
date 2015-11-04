package application.controller;

import application.dbTools.Query;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController {

    MainController mainController;


@FXML    private  Label recipeID;
@FXML    private Label recipeName;
@FXML    private Label recipeType;
@FXML    private Label recipeCuisine;
@FXML    private Label recipeDifficulty;
@FXML    private Label recipeTIme;
@FXML    private Label recipeDiet;



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
            recipeName.setText(dataSet.get(1).get(1));
            recipeType.setText(dataSet.get(2).get(2));
            recipeCuisine.setText(dataSet.get(3).get(3));
            recipeDifficulty.setText(dataSet.get(4).get(4));
            recipeTIme.setText(dataSet.get(7).get(7));
            recipeDiet.setText(dataSet.get(6).get(6));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
