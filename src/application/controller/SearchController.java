package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Haxxflaxx on 2015-11-06.
 */
public class SearchController implements Initializable {

    @FXML TableView searchResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String search = VistaNavigator.getMainController().getSearch();


    }
}