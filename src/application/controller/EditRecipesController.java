package application.controller;

import application.dbTools.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.deleteFrom;
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
        System.out.println("Initialize EditRecipesController");
        mainController = VistaNavigator.getMainController();
        updateEditRecipeList();
        System.out.println("- End of Initialize EditRecipesController");
    }

    /**
     * Updates the fields in the editing of recipes to the selected item.
     */
    public void updateEditRecipeList(){
        ArrayList<ArrayList<String>> dataSet;
        String currentRecipe = mainController.recipList.getSelectionModel().getSelectedItems().toString();
        currentRecipe = currentRecipe.substring(1, currentRecipe.length() - 1);

        String condition = "Name= '" + currentRecipe + "'";

        try {
            System.out.println("- UpdateEditRecipeList");
            dataSet = Query.fetchData("recipes", "*", condition);       //Method for fetching the recipes where
            recipeID = dataSet.get(0).get(0);                           //name == selected recipe
            recipeName.setText(dataSet.get(0).get(1));
            recipeType.setText(dataSet.get(0).get(2));
            recipeCuisine.setText(dataSet.get(0).get(3));
            recipeDifficulty.setText(dataSet.get(0).get(4));
            recipeTime.setText(dataSet.get(0).get(7));
            recipeDiet.setText(dataSet.get(0).get(6));
            recipeDescription.setText(dataSet.get(0).get(9));
            System.out.println("- End of UpdateEditRecipeList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * ButtonMethod for deleting the selected recipe
     */
    public void deleteRecipe(){

        try {
            System.out.println("- DeleteRecipe");
            String criteria = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
            criteria = criteria.replaceAll("\\[", "").replaceAll("\\]","");
            deleteFrom("Recipes", criteria);            //Delete recipes where name == selected recipe

            mainController.recipList.getSelectionModel().select(1);
            System.out.println("- End of DeleteRecipe");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  ButtonMethod for updating Recipes
     */
   public void SubmitButtonAction() {
       String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description"};     //Array with columns
       String[] values = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
               recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText()};                //Array with Values

       try {
           System.out.println("- SubmitButtonAction");
           String condition = "ID='" + Query.fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
           condition = condition.replaceAll("\\[", "").replaceAll("\\]","");
           updateData("Recipes", columns, values, condition);                   //Update data in columns with values
           VistaNavigator.loadVista(
                   VistaNavigator.WELCOME);                                     //Load wellcome vista
           System.out.println("- End of SubmitButtonAction");
       }

       catch (SQLException e) {
           e.printStackTrace();
       }
   }

}
