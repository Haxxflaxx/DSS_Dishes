package application.controller;

import application.dbTools.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController implements Initializable{

    MainController mainController;


@FXML    public Label recipeID;
@FXML    public Label recipeName;
@FXML    public Label recipeType;
@FXML    public Label recipeCuisine;
@FXML    public Label recipeDifficulty;
@FXML    public Label recipeTIme;
@FXML    public Label recipeDiet;



    //TODO: Add control id for items in view.

    /**
     * On view creation:
     * Get controller for main view.
     * Run update.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
