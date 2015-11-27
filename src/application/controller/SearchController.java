package application.controller;

import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;

/**
 * Created by Haxxflaxx on 2015-11-06.
 */
public class SearchController extends NavigationController implements Initializable {



    @FXML TableView searchResult;
    @FXML TableColumn id;
    @FXML TableColumn name;
    @FXML TableColumn type;
    @FXML TableColumn diet;
    @FXML TableColumn cuisine;
    @FXML TableColumn difficulty;
    @FXML TableColumn prepTime;

    //Choicebox
    @FXML private ChoiceBox typeChoicebox;
    @FXML private ChoiceBox cuisineChoicebox;
    @FXML private ChoiceBox difficultyChoicebox;
    @FXML private ChoiceBox timeChoicebox;
    @FXML private ChoiceBox dietChoicebox;

    //Search conditions
    String search;
    String nameCondition;
    String typeCondition;
    String dietCondition;
    String cuisineCondition;
    String difficultyCondition;
    String condition;

    /**
     * Get MainController instance and generate a selection condition for query.
     */
    public SearchController() {
        super();

        search = VistaNavigator.getMainController().getSearch();

        String nameCondition = "Name LIKE '%" + search + "%' ";
        String typeCondition = "Type LIKE '%" + search + "%' ";
        String dietCondition = "Diet LIKE '%" + search + "%' ";
        String cuisineCondition = "Cuisine LIKE '%" + search + "%' ";
        String difficultyCondition = "Difficulty LIKE '%" + search + "%'";

        condition = nameCondition;
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
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

        updateResultTable();
        updateChoicebox();


    }

    /**
     * Get data from recipes based on search query.
     * Filter selection based on selected filters.
     * Put data into TableView.
     */
    public void updateResultTable() {
        String filterCondition = "";
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        if (typeChoicebox.getSelectionModel().getSelectedItem() != null)
            filterCondition += " AND Type='" + typeChoicebox.getSelectionModel().getSelectedItem().toString() + "'";

        if (cuisineChoicebox.getSelectionModel().getSelectedItem() != null) {
            filterCondition += " AND Cuisine='" + cuisineChoicebox.getSelectionModel().getSelectedItem().toString() + "'";
        }
        if (difficultyChoicebox.getSelectionModel().getSelectedItem() != null) {
            filterCondition += " AND Difficulty='" + difficultyChoicebox.getSelectionModel().getSelectedItem().toString() + "'";
        }

        if (timeChoicebox.getSelectionModel().getSelectedItem() != null) {
            filterCondition += " AND Time='" + timeChoicebox.getSelectionModel().getSelectedItem().toString() + "'";
        }

        if (dietChoicebox.getSelectionModel().getSelectedItem() != null){
            filterCondition += " AND Diet='" + dietChoicebox.getSelectionModel().getSelectedItem().toString() + "'";
        }

        try {
            dataSet = Query.fetchData("recipes", "*", condition + filterCondition);
            System.out.println("Select condition");
            System.out.println(condition + filterCondition + ";");

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
                        element.get(9)
                    )
                );
            }

            searchResult.setItems(items);
            searchResult.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateChoicebox(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> typeChoicelist = FXCollections.observableArrayList();
        ObservableList<String> cuisineChoicelist = FXCollections.observableArrayList();
        ObservableList<String> difficultyChoicelist = FXCollections.observableArrayList();
        ObservableList<String> timeChoicelist = FXCollections.observableArrayList();
        ObservableList<String> dietChoicelist = FXCollections.observableArrayList();

        try {
            dataSet = fetchDistinct("Recipes", "Type");
            for (ArrayList<String> element : dataSet) typeChoicelist.add(element.get(0));

            dataSet = fetchDistinct("Recipes", "Cuisine");
            for (ArrayList<String> element : dataSet) cuisineChoicelist.add(element.get(0));

            dataSet = fetchDistinct("Recipes", "Difficulty");
            for (ArrayList<String> element : dataSet) difficultyChoicelist.add(element.get(0));

            dataSet = fetchDistinct("Recipes", "Time");
            for (ArrayList<String> element : dataSet) timeChoicelist.add(element.get(0));

            dataSet = fetchDistinct("Recipes", "Diet");
            for (ArrayList<String> element : dataSet) dietChoicelist.add(element.get(0));
        }

        catch (SQLException e){
            e.printStackTrace();
        }

        typeChoicebox.setItems(typeChoicelist);
        cuisineChoicebox.setItems(cuisineChoicelist);
        difficultyChoicebox.setItems(difficultyChoicelist);
        timeChoicebox.setItems(timeChoicelist);
        dietChoicebox.setItems(dietChoicelist);

        }


    @Override
    public String getFxml() {
        return VistaNavigator.SEARCH;
    }
}