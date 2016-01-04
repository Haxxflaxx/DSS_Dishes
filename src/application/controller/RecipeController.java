package application.controller;

import application.Recipe;
import application.User;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Daniel on 2015-11-03.
 * This class is the controller for the recipeDetailsVista.
 * Responsible programmer Fredrik Rissanen
 */
public class RecipeController extends NavigationController implements Initializable {

    private Recipe recipe;

    @FXML    private Label recipeName;
    @FXML    private Label recipeType;
    @FXML    private Label recipeCuisine;
    @FXML    private Label recipeDifficulty;
    @FXML    private Label recipeTime;
    @FXML    private Label recipeDiet;
    @FXML    private TextArea recipeDescription;
    @FXML    private ListView recipeIngredients;
    @FXML    private Button editRecipe;
    @FXML    private Label recipeCreator;
    @FXML    private Button r1,r2,r3,r4,r5;
    @FXML    private Label ratings,totalRatings;
    private  String  scoreSum;
    private int button;

    /**
     * On view creation:
     * Get controller for main view.
     * Run update.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("- Initialize RecipeController");
        recipe = Recipe.getSelected();
        updateContent();
        updateIngredientList();
        recipeDescription.setEditable(false);
        privilegeCheck();
        initializeRating();

        System.out.println("- End of Initialize RecipeController");


    }


    /**
     * Fetches data from the database based on selected item in
     * MainController.recipList and puts the data into the fields for the view.
     *
     * Created by Daniel.
     */
    private void updateContent(){

        System.out.println("- Recipe updateContent");

        recipeName.setText(recipe.getName());
        recipeType.setText(recipe.getType());
        recipeCuisine.setText(recipe.getCuisine());
        recipeDifficulty.setText(recipe.getDifficulty());
        recipeTime.setText(recipe.getTime());
        recipeDiet.setText(recipe.getDiet());
        recipeDescription.setText(recipe.getDescription());
        recipeCreator.setText("Created By: " + recipe.getCreator());
        ratings.setText(recipe.getRatings());
        totalRatings.setText(recipe.getTotalRatings());
        scoreSum = recipe.getScoreSum();

        System.out.println("- End of Recipe updateContent");

    }
    /**
     * Method for checking user privilege and applying according settings
     */
    private void privilegeCheck() {
        if (User.getPrivilege() == 5){
            editRecipe.setVisible(true);
        }

        else if (User.getPrivilege() == 0 || User.getPrivilege() == 1 || !User.getName().equals(recipe.getCreator())){
            editRecipe.setVisible(false);
        }
        if (User.getPrivilege() == 0 || hasRated()) { //disable buttons
            disableRating();
        }
    }
    /**
     *  Method for updating the Ingredients in the RecipeView
     */
    private void updateIngredientList() {
        ArrayList<ArrayList<String>> dataSet;                     //Arraylist for storing the ingredients
        String condition = "RUI.IID = ingredients.ID AND RID = '" + recipe.getId() + "'";
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            System.out.println("- UpdateIngredientList");
            dataSet = Query.fetchData("RUI, ingredients", "Name, Quantity, Unit", condition);

            for (ArrayList<String> element : dataSet){            //Adds an element for every item in the dataset
                itemList.add(element.get(0)+ "  " + element.get(1)+ "  " + element.get(2));
                }
            recipeIngredients.setItems(itemList);                 //Sets the listview as the observable arraylist
            System.out.println("- End of UpdateIngredientList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  ButtonMethod for loading the editRecipes.
     */
    public void editRecipeView(){
        System.out.println("- Load editRecipeview");
        VistaNavigator.loadVista(
                VistaNavigator.EDITRECIPES);                       //Load editRecipesView
        System.out.println("- end of Load editRecipeview");
    }
    /**
     *
     * Method for updating rating values
     * created by Ioannis Gkikas
     */
    public void rate(int button) {
        //update the database (user with this ID rated recipe with this ID)
        //refresh values on score and total labels
        DecimalFormat df = new DecimalFormat("0.0");
        String condition = "RID = '" + recipe.getId() + "' AND UID = '" + User.getId() + "'";

        try {
            Query.insertInto("RaUU", "RID, UID","" + recipe.getId() + ", " + User.getId() + "");
            Query.updateData("Recipes", "TotalRatings", String.valueOf(Integer.parseInt(recipe.getTotalRatings())+1), "ID = " + recipe.getId());
            totalRatings.setText(String.valueOf(Integer.parseInt(recipe.getTotalRatings())+1));
            Query.updateData("Recipes", "SumRatings", String.valueOf(Integer.parseInt(recipe.getScoreSum())+button), "ID = " + recipe.getId());
            double SS = (double)(Integer.parseInt(recipe.getScoreSum()) * Integer.parseInt(recipe.getTotalRatings()) + button);
            double TT = (double)(Integer.parseInt(recipe.getTotalRatings())+1);
            Query.updateData("Recipes", "Ratings", Double.toString(SS/TT), "ID = " + recipe.getId());
            ratings.setText(Double.toString(SS/TT));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        disableRating();
    }

    /**
     * Method for checking if user has rated
     * created by Ioannis Gkikas
     */
    public boolean hasRated() {
        ArrayList<ArrayList<String>> dataSet;
        boolean hasRated = false;
        String condition = "RID = '" + recipe.getId() + "' AND UID = '" + User.getId() + "'";
        try {
            dataSet = Query.fetchData("RaUU", "RID", condition);
            if (dataSet.size() == 0) {
                hasRated = false;
            }
            else {
                hasRated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasRated;
    }

    /**
     * Method for disabling buttons
     * Created by Ioannis Gkikas
     */
    public void disableRating() {
        r1.setDisable(true);
        r2.setDisable(true);
        r3.setDisable(true);
        r4.setDisable(true);
        r5.setDisable(true);
    }

    /**
     * Method for starting up rating buttons
     * Created by Ioannis Gkikas
     */
    public void initializeRating() {
        r1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rate(1);
            }
        });
        r2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rate(2);
            }
        });
        r3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rate(3);
            }
        });
        r4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rate(4);
            }
        });
        r5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                rate(5);
            }
        });
    }

    @Override
    public String getFxml() {
        return VistaNavigator.RECIPE;
    }
}
