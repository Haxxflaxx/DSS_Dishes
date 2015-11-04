package application.controller;

import application.dbTools.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController {

    MainController mainController;
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
