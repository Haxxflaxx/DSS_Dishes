package application.controller;

import application.Recipe;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Haxxflaxx on 2015-11-06.
 */
public class SearchController implements Initializable {


    @FXML TableView searchResult;

    //Search conditions
    String search;
    String nameCondition;
    String typeCondition;
    String dietCondition;
    String cuisineCondition;
    String difficultyCondition;
    String condition;

    public SearchController() {
        search = VistaNavigator.getMainController().getSearch();

        String nameCondition = "Name = '%" + search + "%' ";
        String typeCondition = "Type = '%" + search + "%' ";
        String dietCondition = "Diet = '%" + search + "%' ";
        String cuisineCondition = "Cuisine = '%" + search + "%' ";
        String difficultyCondition = "Difficulty = '%" + search + "%' ";

        condition = nameCondition + " OR " +
                typeCondition + " OR " +
                dietCondition + " OR " +
                cuisineCondition + " OR " +
                difficultyCondition;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateResultTable();
    }

    private void updateResultTable(){
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();

        try {
            dataSet = Query.fetchData("recipes", "*", condition);

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}