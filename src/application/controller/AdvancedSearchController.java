package application.controller;

import application.Ingredient;
import application.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 * Created by Pierre on 2015-11-03.
 */

public class AdvancedSearchController extends NavigationController implements Initializable{


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

    /**
     * Updates updateEditRecipeList and UpdateIngredients when loading vista.
     * Sets cellValueFactory with the column names for the tableview.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    updateIngredients();
        Name.setCellValueFactory(new PropertyValueFactory<Ingredient,String>("name"));
    }

    /**
     * Loads the name of all ingredients and puts them in ingredientlist.
     */
    public void updateIngredients() {
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();      //Observable arraylist for the listview

        try {
            System.out.println("- Main updateIngredientsRecipList");

            dataSet = fetchData("Ingredients", "Name");                             //Gives the arraylist the ingredientnames

            for (ArrayList<String> element : dataSet) {
                itemList.add(element.get(0));                                       //For every element in the array, get name
            }

            System.out.println("- end of Main updateIngredientsRecipList");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipeIngredients.setItems(itemList);                                       //Sets the Listview to show the obs arraylist
    }


    /**
     * ButtonMethod for add ingredients from the ingredientList into the tableview
     */
    public void addIngredientButton() {

        String selectedIngredient = recipeIngredients.getSelectionModel().getSelectedItems().toString();
        selectedIngredient = selectedIngredient.replaceAll("\\[", "").replaceAll("\\]", "");

        items.add(new Ingredient(selectedIngredient));    //Creates new

        addedIngredientTable.setItems(items);               //Object of the type Ingredient and adds it to the tableView.
        System.out.println();

    }

    /**
     * Searchfield for searching ingredients in the ingredientList
     */
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
        recipeIngredients.setItems(itemList);               //Sets the listview to show the ingredients that matches the search
    }                                                       //Criteria

    @Override
    public String getFxml() {
        return VistaNavigator.ADVSEARCH;
    }
}
