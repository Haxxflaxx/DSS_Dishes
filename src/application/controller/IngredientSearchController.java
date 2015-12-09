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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 * Created by Pierre on 2015-11-03.
 */

public class IngredientSearchController extends NavigationController implements Initializable{


    MainController mainController;
    private String recipeID;
    private ObservableList<Ingredient> items = FXCollections.observableArrayList();


    @FXML
    private ListView recipeIngredients;
    @FXML
    private Button recipeSubmit;
    @FXML
    private TextField ingredientSearch;
    @FXML
    private Button addIngredients;
    @FXML
    private TableView addedIngredientTable;
    @FXML

    private TableView searchResult;
    @FXML TableColumn id;
    @FXML TableColumn name;
    @FXML TableColumn type;
    @FXML TableColumn diet;
    @FXML TableColumn cuisine;
    @FXML TableColumn difficulty;
    @FXML TableColumn prepTime;

    @FXML
    private TableColumn Name;
    @FXML
    private Button searchIngredientbutton;


    /**
     * Updates updateEditRecipeList and UpdateIngredients when loading vista.
     * Sets cellValueFactory with the column names for the tableview.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    updateIngredients();
        Name.setCellValueFactory(new PropertyValueFactory<Ingredient,String>("name"));

        searchResult.setRowFactory(tv -> {
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Recipe.setSelected(row.getItem());
                    VistaNavigator.loadVista(
                            VistaNavigator.RECIPE
                    );
                }
            });
            return row;
        });
        id.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("id")
        );
        name.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("name")
        );
        type.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("type")
        );
        diet.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("diet")
        );
        cuisine.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("cuisine")
        );
        difficulty.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("difficulty")
        );
        prepTime.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("time")
        );

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

    public void searchIngredient() {
        String filterCondition = "";
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        if (addedIngredientTable.getItems() != null)
           // filterCondition += " AND Name='" + addedIngredientTable.getSelectionModel().getSelectedItem().toString() + "'";


        try {
            dataSet = Query.fetchData("recipes", "*");
            System.out.println("Select condition");
            System.out.println(filterCondition + ";");

            for (ArrayList<String> element : dataSet){

                items.add(new Recipe(
                                element.get(0),
                                element.get(1),
                                element.get(2),
                                element.get(3),
                                element.get(4),
                                element.get(5),
                                element.get(6),
                                element.get(7),
                                element.get(8),
                                element.get(9),
                                element.get(10)
                        )
                );
            }

            searchResult.setItems(items);
            searchResult.refresh();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFxml() {
        return VistaNavigator.ADVSEARCH;
    }
}
