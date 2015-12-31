package application.controller;

import application.Ingredient;
import application.Recipe;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static application.dbTools.Query.fetchData;
import static application.dbTools.Query.insertInto;
import static application.dbTools.Query.updateData;

/**
 * Created by Frank on 17/12/15.
 */
public class NewRecipeController extends EditRecipesController implements Initializable {

    private ObservableList<Ingredient> items = FXCollections.observableArrayList();
    Recipe recipe;
    String recipeID;
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
    }

    public void SubmitButtonAction() {
        String[] columns = {"Name", "Type", "Cuisine", "Difficulty", "Diet", "Time", "Description","Creator"};     //Array with columns
        String[] values = {recipeName.getText(), recipeType.getText(), recipeCuisine.getText(), recipeDifficulty.getText(),
                recipeDiet.getText(), recipeTime.getText(), recipeDescription.getText(), User.getName()};                //Array with Values

        try {
            insertInto("Recipes", "Name", "'" + recipeName.getText() +"'");
            System.out.println("- SubmitButtonAction");
            updateData("Recipes", columns, values, "Name='" + recipeName.getText() + "'");                   //Update data in columns with values
            System.out.println("- End of SubmitButtonAction");

            for (Object o : addedIngredientTable.getItems()) {                      //Foreach object in the list, getItems
                String iName = Name.getCellData(o).toString();                      //iName = ingredientName
                String iAmount = Amount.getCellData(o).toString();                  //iAmount = ingredientAmount
                String iUnit = Unit.getCellData(o).toString();                      //iUnit = ingredientUnit

                String currentId = fetchData("Ingredients", "ID", "Name='" + iName + "'").toString();   //Fetches id where name
                currentId = currentId.replaceAll("\\[", "").replaceAll("\\]", "");                      //is column-name

                insertInto("RUI", "RID, IID, Quantity, Unit", "'" + recipeID + "','" + currentId + "','" + iAmount + "','" + iUnit + "'");
                //Inserts recipe id, current id,
            }                                                                       //amount, and unit
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Recipe.setSelectedByName(recipeName.getText());
        VistaNavigator.loadVista(VistaNavigator.RECIPE);
    }

    /**
     * ButtonMethod for add ingredients from the ingredientList into the tableview
     */
    public void addIngredientButton() {

        String selectedIngredient = recipeIngredients.getSelectionModel().getSelectedItems().toString();
        selectedIngredient = selectedIngredient.replaceAll("\\[", "").replaceAll("\\]", "");

        items.add(new Ingredient(selectedIngredient, ingredientAmount.getText(), ingredientUnit.getText()));    //Creates new
        addedIngredientTable.setItems(items);               //Object of the type Ingredient and adds it to the tableView.

    }
}
