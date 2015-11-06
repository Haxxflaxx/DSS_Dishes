package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ListView;

import application.dbTools.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.insertInto;

/**
 * Main controller class for the layout.
 */
public class MainController implements Initializable {

    @FXML    private Button recipeEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateRecipList();
        recipList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            VistaNavigator.loadVista(
                    VistaNavigator.RECIPE
            );
        });
    }

    /** Holder of a switchable vista. */
    @FXML private StackPane vistaHolder;

    /** List holding the recipe names from the DB. */
    @FXML protected ListView recipList;

    /** Field for entering search criteria. */
    @FXML private TextField recipSearch;

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }


    /**
     * Loads the name of all recipes and puts them into recipList.
     */
    public void updateRecipList(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchData("recipes", "Name");

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipList.setItems(itemList);
    }

    /**
     * Loads the name of all recipes and puts them into recipList.
     *
     * @param search input for selection based on name with wildcard at the end.
     */
    public void updateRecipList(String search){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();
        String condition = "Name LIKE '" +"%" + search + "%'";
        System.out.println(condition);

        try {
            dataSet = Query.fetchData("recipes", "Name", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        recipList.setItems(itemList);
    }

    /**
     * Run search recipe based on name.
     */
    public void search(){
        System.out.println(recipSearch.getText());
        updateRecipList(recipSearch.getText());
    }

    public void addNewRecipeButton(){
        VistaNavigator.loadVista(
                VistaNavigator.EDITRECIPES);
    }


}

