package application.controller;

import application.Recipe;
import application.User;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Frank on 07/12/15.
 */
public class MyRecipeController extends SearchController implements Initializable {
    @FXML
    private Label myUsername;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myUsername.setText(User.getName() + " recipes");

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


    }

    public void updateResultTable() {
        ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
        ObservableList<Recipe> items = FXCollections.observableArrayList();
        String userRecipe = "Creator='" + User.getName() + "'";

        try {
            dataSet = Query.fetchData("recipes", "*", userRecipe);
            System.out.println("testing " + dataSet);

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
                                element.get(11)
                        )
                );
            }

            searchResult.setItems(items);
            searchResult.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
