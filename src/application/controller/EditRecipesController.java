package application.controller;

import application.Ingredient;
import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 * Created by haxxflaxx on 2015-11-03.
 */
public class EditRecipesController implements Initializable {

    MainController mainController;
    private String recipeID;
    private ObservableList<Ingredient> items = FXCollections.observableArrayList();


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
    @FXML
    private TextField ingredientAmount;
    @FXML
    private TextField ingredientUnit;
    @FXML
    private TextField ingredientSearch;
    @FXML
    private Button addIngredients;
    @FXML
    private TableView addedIngredientTable;
    @FXML
    private TableColumn Name;
    @FXML
    private TableColumn Amount;
    @FXML
    private TableColumn Unit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize EditRecipesController");
        mainController = VistaNavigator.getMainController();
        updateEditRecipeList();
        updateIngredients();
        Name.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("name")
        );
        Amount.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("amount")
        );
        Unit.setCellValueFactory(
                new PropertyValueFactory<Ingredient, String>("unit")
        );
        System.out.println("- End of Initialize EditRecipesController");
    }

    /**
     * Updates the fields in the editing of recipes to the selected item.
     */
    public void updateEditRecipeList() {
        ArrayList<ArrayList<String>> dataSet;

        String currentRecipe = mainController.recipList.getSelectionModel().getSelectedItems().toString();
        currentRecipe = currentRecipe.substring(1, currentRecipe.length() - 1);

        String condition = "Name= '" + currentRecipe + "'";


        try {
            System.out.println("- UpdateEditRecipeList");
            dataSet = fetchData("recipes", "*", condition);       //Method for fetching the recipes where
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
    public void deleteRecipe() {

        try {
            System.out.println("- DeleteRecipe");
            String criteria = "ID='" + fetchData("Recipes", "ID", "Name ='" + recipeName.getText() + "'") + "'";
            criteria = criteria.replaceAll("\\[", "").replaceAll("\\]", "");
            deleteFrom("Recipes", criteria);            //Delete recipes where name == selected recipe

            mainController.recipList.getSelectionModel().select(1);
            System.out.println("- End of DeleteRecipe");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ButtonMethod for updating Recipes
     */
    public void SubmitButtonAction() {
        String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description"};     //Array with columns
        String[] values = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
                recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText()};                //Array with Values

        String currentRecipe = mainController.recipList.getSelectionModel().getSelectedItems().toString();
        currentRecipe = "NAME='" + currentRecipe + "'";
        currentRecipe = currentRecipe.replaceAll("\\[", "").replaceAll("\\]", "");



        try {
            System.out.println("- SubmitButtonAction");
            updateData("Recipes", columns, values, currentRecipe);                   //Update data in columns with values
            System.out.println("- End of SubmitButtonAction");

            for (Object o : addedIngredientTable.getItems()) {
                String iName = Name.getCellData(o).toString();                      //iName = ingredientName
                String iAmount = Amount.getCellData(o).toString();                  //iAmount = ingredientAmount
                String iUnit = Unit.getCellData(o).toString();                      //iUnit = ingredientUnit

                System.out.println("TESTING" + iName);

                String currentId = fetchData("Ingredients", "ID", "Name='" + iName + "'").toString();   //Fetches id where name
                currentId = currentId.replaceAll("\\[", "").replaceAll("\\]", "");                      //is column-name

                System.out.println("RECIPE ID"+ recipeID);
                System.out.println("Current ID"+ currentId);

                insertInto("RUI", "RID, IID, Quantity, Unit", "'" + recipeID + "','" + currentId + "','" + iAmount + "','" + iUnit + "'");
                                                                                    //Inserts recipe id, current id,
            }                                                                       //amount, and unit
            mainController.updateRecipList();
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the name of all ingredients and puts them into ingredientlist.
     */
    public void updateIngredients() {
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            System.out.println("- Main updateIngredientsRecipList");

            dataSet = fetchData("Ingredients", "Name");

            for (ArrayList<String> element : dataSet) {
                itemList.add(element.get(0));
            }

            System.out.println("- end of Main updateIngredientsRecipList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipeIngredients.setItems(itemList);
    }

    public void addIngredientButton() {

        String selectedIngredient = recipeIngredients.getSelectionModel().getSelectedItems().toString();
        selectedIngredient = selectedIngredient.replaceAll("\\[", "").replaceAll("\\]", "");

        items.add(new Ingredient(selectedIngredient, ingredientAmount.getText(), ingredientUnit.getText()));
        addedIngredientTable.setItems(items);

    }

    public void updateIngredientSearch(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String condition = "Name LIKE '" +"%" + ingredientSearch.getText() + "%'";
        System.out.println("Condition " + condition);

        try {

            dataSet = fetchData("Ingredients", "Name", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipeIngredients.setItems(itemList);
    }



}
