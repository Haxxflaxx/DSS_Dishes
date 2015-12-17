package application.controller;

import application.User;
import application.dbTools.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.stage.*;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.fetchData;
import static application.dbTools.Query.insertInto;

/**
 * Created by Daniel.
 *
 * Main controller class for the layout.
 */
public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("- Intialize mainController");
        loginStatus();
        updateRecipList();
        recipList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            VistaNavigator.loadVista(
                    VistaNavigator.RECIPE
            );
            System.out.println("- End of Initialize mainController");
        });
    }

    /** Holder of a switchable vista. */
    @FXML private StackPane vistaHolder;

    /** List holding the recipe names from the DB. */
    @FXML protected ListView recipList;

    /** Field for entering search criteria. */
    @FXML private TextField recipSearch;

    @FXML public  Label addRecipe;

    @FXML public  Label myRecipes;

    @FXML public Button loginButton;

    @FXML public MenuButton buttonLoggedin;

    /** Ioannis Gkikas extraction of main stage
     *
     */
    @FXML private Stage mainStage;
    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    public String selectionSort = "";

    /**
     * Replaces the vista displayed in the vista holder with a new vista.
     *
     * @param node the vista node to be swapped in.
     */
    public void setVista(Node node) {
        vistaHolder.getChildren().setAll(node);
    }

    public String getSearch(){
        return recipSearch.getText();
    }


    /**
     * Loads the name of all recipes and puts them into recipList.
     */
    public void updateRecipList(){
        ArrayList<ArrayList<String>> dataSet;
        ObservableList<String> itemList = FXCollections.observableArrayList();

        try {
            System.out.println("- Main updateRecipList");

            dataSet = Query.fetchData("recipes", "Name");

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
            }

            System.out.println("- end of Main updateRecipList");
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
            System.out.println("- updateRecipList");
            dataSet = Query.fetchData("recipes", "Name", condition);

            for (ArrayList<String> element : dataSet){
                itemList.add(element.get(0));
                System.out.println("- End of updateRecipList");
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
        VistaNavigator.loadVista(
                VistaNavigator.SEARCH
        );
    }

    /**
     *
     * ButtonMethod for adding new Recipes.
     * By Fredrik Rissanen
     */
    public void addNewRecipeClick(){

        try {
            System.out.println("- addNewRecipeButton");
            String checkRecipe = fetchData("Recipes", "Name", "NAME='--New--'").toString();
            checkRecipe = checkRecipe.replaceAll("\\[", "").replaceAll("\\]", "");

            if (!checkRecipe.equals("--New--")) {
                insertInto("Recipes", "Name, Creator", "'--New--','" + User.getName() + "'");
                System.out.println(User.getName());
                VistaNavigator.loadVista(VistaNavigator.RECIPE);
                VistaNavigator.loadVista(VistaNavigator.EDITRECIPES);
            }

            System.out.println("- End of addNewRecipeButton");
    }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigation method for going back to a previous view
     */
    public void back(){
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.moveBack();
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.reloadVista();
    }

    /**
     * Navigation method for going forward to a previous view
     */
    public void forward(){
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.moveForward();
        System.out.println(VistaNavigator.getActiveController());
        VistaNavigator.reloadVista();
    }

    /**
     *  The browser menu
     */
    public void allRecipesclick(){
        VistaNavigator.loadVista(
                VistaNavigator.SEARCH
        );

        System.out.println("Load All Recipes");

    }

    public void topRatedclick(){
        System.out.println("Load Top Rated Recipes");
        selectionSort = "Ratings";
        VistaNavigator.loadVista(VistaNavigator.BROWSE);
    }

    public void topDateClick(){
        System.out.println("Load New Recipes");
        selectionSort = "Date";
        VistaNavigator.loadVista(VistaNavigator.BROWSE);
    }

    public void myRecipesclick(){
        VistaNavigator.loadVista(VistaNavigator.MYPAGE);
        System.out.println("Load My Recipes");
    }

    /** custom  minimize,resize and close buttons,manipulating the main stage */

    public void minimize(){
     mainStage.setIconified(true);
    }

    public void resize() {
       if(mainStage.isFullScreen()) {
           mainStage.setFullScreen(false);
       } else {
           mainStage.setFullScreen(true);
       }
    }

    public void loginStatus() {
        if (User.getPrivilege() == 1) {
            addRecipe.setVisible(false);
            myRecipes.setVisible(true);
        } else if (User.getPrivilege() == 2) {
            addRecipe.setVisible(true);
            myRecipes.setVisible(true);
        } else if (User.getPrivilege() == 5) {
            addRecipe.setVisible(true);
            myRecipes.setVisible(true);
        }
        else{
            addRecipe.setVisible(false);
            myRecipes.setVisible(false);
        }

        if (User.getPrivilege() > 0) {
            loginButton.setVisible(false);
            buttonLoggedin.setVisible(true);
            buttonLoggedin.setText(User.getName());
        }
        else {
            loginButton.setVisible(true);
            buttonLoggedin.setVisible(false);
        }
    }

    public void exit(){
        System.exit(0);
    }

    /**
     * Method that loads the loginVista
     * By Fredrik Rissanen
     */
    public void loginButtonclick () {
        VistaNavigator.loadVista(VistaNavigator.LOGINVISTA);
    }

    /**
     * Method for signing out
     * By Fredrik Rissanen
     */
    public void signoutButton(){
        User.setName("");
        User.setId("");
        User.setPrivilege("0");
        loginStatus();
        VistaNavigator.loadVista(VistaNavigator.SEARCH);
    }

    /**
     * Method for canceling sign out
     * By Fredrik Rissanen
     */
    public void cancelSignout(){
        VistaNavigator.loadVista(VistaNavigator.SEARCH);
    }
}

