package application.controller;


import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Pierre on 2015-11-18.
 */
public class BrowseController extends NavigationController implements Initializable {

    @FXML private TableView recipeTable;
    @FXML TableColumn id;
    @FXML TableColumn rating;
    @FXML TableColumn name;
    @FXML TableColumn type;
    @FXML TableColumn diet;
    @FXML TableColumn cuisine;
    @FXML TableColumn difficulty;
    @FXML TableColumn prepTime;

    private final int selectNr = 20;


    @Override

    public void initialize(URL location, ResourceBundle resources){
        id.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("id")
        );
        rating.setCellValueFactory(
                new PropertyValueFactory<Recipe, String>("ratings")
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
    }

    private void updateResultTable(){
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchSorted("recipes", "*", VistaNavigator.getMainController().selectionSort);

            for (int i = 0; i < selectNr && i < dataSet.size(); i++){
                items.add(new Recipe(
                                dataSet.get(i).get(0),
                                dataSet.get(i).get(1),
                                dataSet.get(i).get(2),
                                dataSet.get(i).get(3),
                                dataSet.get(i).get(4),
                                dataSet.get(i).get(5),
                                dataSet.get(i).get(6),
                                dataSet.get(i).get(7),
                                dataSet.get(i).get(8),
                                dataSet.get(i).get(9)
                        )
                );
            }

            recipeTable.setItems(items);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getFxml() {
        return VistaNavigator.BROWSE;
    }
}
