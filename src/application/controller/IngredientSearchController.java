package application.controller;

import application.Ingredient;
import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 *
 * Controller class for ingredient search.
 * Allows the user to search for for recipes based on ingredients.
 *
 * Created by Pierre on 2015-11-03.
 */

public class IngredientSearchController extends NavigationController implements Initializable{


    MainController mainController;
    private String recipeID;
    private ObservableList<Ingredient> ingredientItems = FXCollections.observableArrayList();


    @FXML
    private ListView recipeIngredients;
    @FXML
    private Button removeIngredients;
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
    private TableColumn ingredientName;
    @FXML
    private Button searchIngredientbutton;


    /**
     * Updates searchIngredient and updateIngredients when loading vista.
     * Sets cellValueFactory with the column names for the tableview.
     * Added mouse event to add and remove recipes with double click.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    updateIngredients();
        ingredientName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
        addedIngredientTable.setItems(ingredientItems);
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

        addedIngredientTable.setRowFactory(tv -> {
            TableRow<Recipe> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    removeIngredientButton();
                }
            });
            return row;
        });

        recipeIngredients.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {

                if (click.getClickCount() == 2){
                    addIngredientButton();
                }
            }
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

        searchIngredient();
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
     * ButtonMethod for adding ingredients from the ingredientList into the tableview
     */

    public void addIngredientButton() {
        ArrayList<ArrayList<String>> dataSet;
        String selectedIngredient = recipeIngredients.getSelectionModel().getSelectedItems().toString();
        selectedIngredient = selectedIngredient.replaceAll("\\[", "").replaceAll("\\]", "");

        String condition = "Name='"+selectedIngredient+"'";


        try {
            dataSet = Query.fetchData("ingredients", "*", condition);

            ingredientItems.add(new Ingredient(
                    dataSet.get(0).get(1),
                    dataSet.get(0).get(0)

            ));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        addedIngredientTable.setItems(ingredientItems);

    }

    /**
     * Removes ingredients from tableview.
     */

    public void removeIngredientButton() {

        final int selected = addedIngredientTable.getSelectionModel().getSelectedIndex();
        if (selected != -1) {
            Object itemToRemove = addedIngredientTable.getSelectionModel().getSelectedItem();

            final int newSelectedIdx =
                    (selected == addedIngredientTable.getItems().size() - 1)
                            ? selected - 1
                            : selected;

            addedIngredientTable.getItems().remove(selected);
            addedIngredientTable.getSelectionModel().select(newSelectedIdx);


        }
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

    /**
     * Builds a search condition based on selected ingredients and
     * fetch the data from the DB.
     *
     * Created by Daniel.
     */
    public void searchIngredient() {
        boolean first = true;
        String condition = "";
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        try {
            if (ingredientItems.size() == 0) dataSet = Query.fetchData("recipes", "*");

            else {
                for (Ingredient ingredient : ingredientItems) {
                    if (!first) condition += " and ";
                    condition += "id IN (SELECT rid FROM rui WHERE iid = '" + ingredient.getId() + "')";
                    first = false;
                }
                dataSet = Query.fetchDistinct("recipes", "*", condition);
            }

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
                                element.get(10),
                                element.get(11),
                                element.get(12),
                                element.get(13)
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

    /**
     * Clear all the ingredients from the table view.
     */

    public void clearIngredient(){
        ingredientItems.clear();
        addedIngredientTable.setItems(ingredientItems);
        searchIngredient();
    }

    @Override
    public String getFxml() {
        return VistaNavigator.ADVSEARCH;
    }
}
