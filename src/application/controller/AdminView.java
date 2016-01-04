package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static application.dbTools.Query.*;


/**
 * Created by Nikos on 07/12/2015.
 */
public class AdminView implements Initializable{

    @FXML private ListView activeUsers;
    @FXML private ListView adminbannedUsers;
    @FXML private Button banButton;

    //List of active usernames
    public void activeusersList(){
        ArrayList<ArrayList<String>> userSet;
            ObservableList<String> userList = FXCollections.observableArrayList();


        try {
            userSet = fetchData("Users", "Username", "Privilege != 9");
            System.out.println(userSet);
            for (ArrayList<String> element : userSet) {
                userList.add(element.get(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        activeUsers.setItems(userList);

    }
    //List of banned usernames
    public void testList(){
        ArrayList<ArrayList<String>> bannedSet;
        ObservableList<String> bannedList = FXCollections.observableArrayList();

        try {
            bannedSet = fetchData("Users", "Username", "Privilege = 9");
            System.out.println(bannedSet);
            for (ArrayList<String> element : bannedSet) {
                bannedList.add(element.get(0));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        adminbannedUsers.setItems(bannedList);
    }

    //Button for banning users
   public void bannedusersList(){
       System.out.println(activeUsers.getSelectionModel().getSelectedItem().toString());

       try {
           String condition = "Username = '" + activeUsers.getSelectionModel().getSelectedItem().toString() + "'";
           updateData("Users","Privilege","9", condition);
           //insertInto("BannedUsers","Username", "'" + activeUsers.getSelectionModel().getSelectedItem().toString() + "'");
           //deleteFrom("Users","Username = '" + activeUsers.getSelectionModel().getSelectedItem().toString() + "'");

           testList();
           activeusersList();
       } catch (SQLException e) {
           e.printStackTrace();
       }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeusersList();
        testList();
    }
}
