package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Shiwei on 2015-11-30.
 */
public class MypageconnectionController implements Initializable {

    @FXML private Label myvistaconnection;

    @FXML private Label welcomepage;

    @FXML private Label showusername;

    @FXML private Button mypageconnection;


public void MypageconnectionButtonclick(){

    VistaNavigator.loadVista(VistaNavigator.MYPAGE);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
