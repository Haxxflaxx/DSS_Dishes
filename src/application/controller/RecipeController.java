package application.controller;

import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class RecipeController implements Initializable {

    MainController mainController;
    private String recipeID;


    @FXML    private Label recipeName;
    @FXML    private Label recipeType;
    @FXML    private Label recipeCuisine;
    @FXML    private Label recipeDifficulty;
    @FXML    private Label recipeTime;
    @FXML    private Label recipeDiet;
    @FXML    private TextArea recipeDescription;
    @FXML    private ListView recipeIngredients;
    @FXML    private Button editRecipe;



    /**
     * On view creation:
     * Get controller for main view.
     * Run update.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = VistaNavigator.getMainController();
        updateContent();
        updateIngredientList();
    }


    /**
     * Fetches data from the database based on selected item in
     * MainController.recipList and puts the data into the fields for the view.
     */
    private void updateContent(){
        ArrayList<ArrayList<String>> dataSet;
        String recipeChoice = mainController.recipList.getSelectionModel().getSelectedItems().toString();


        recipeChoice = recipeChoice.substring(1, recipeChoice.length() - 1);

        String condition = "Name= '" + recipeChoice + "'";
        System.out.println(condition);


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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateIngredientList() {
        ArrayList<ArrayList<String>> dataSet;
        String condition = "RUI.IID = ingredients.ID AND RID = '" + recipeID + "'";
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchData("RUI, ingredients", "Name, Quantity, Unit", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0)+ "  " + element.get(1)+ "  " + element.get(2));
                }
            recipeIngredients.setItems(itemList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editRecipeView(){
        VistaNavigator.loadVista(
                VistaNavigator.EDITRECIPES);
    }

}
