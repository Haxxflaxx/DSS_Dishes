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

    public void activeusersList(){
        ArrayList<ArrayList<String>> userSet;
        //ArrayList<ArrayList<String>> bannedSet;
            ObservableList<String> userList = FXCollections.observableArrayList();
          //ObservableList<String> bannedList = FXCollections.observableArrayList();

        try {
            userSet = fetchData("Users", "Username", "Privilege = '1'");
           //bannedSet = fetchData("BannedUsers", "BannedUsername");
            System.out.println(userSet);
            //System.out.println(bannedSet);
            for (ArrayList<String> element : userSet) {
                userList.add(element.get(0));
                //bannedList.add(element.get(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        activeUsers.setItems(userList);
        //adminbannedUsers.setItems(bannedList);
        //System.out.println(userList);

    }

    public void testList(){
        ArrayList<ArrayList<String>> bannedSet;
        ObservableList<String> bannedList = FXCollections.observableArrayList();

        try {
            bannedSet = fetchData("BannedUsers", "Username");
            System.out.println(bannedSet);
            for (ArrayList<String> element : bannedSet) {
                bannedList.add(element.get(0));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        adminbannedUsers.setItems(bannedList);
    }

   public void bannedusersList(){

       System.out.println(activeUsers.getSelectionModel().getSelectedItem().toString());

       try {
           //System.out.println("ban test");
           //String badUser = fetchData("Users", "Username ='" + activeUsers.getSelectionModel().getSelectedItem().toString() + "'").toString();
           //badUser = badUser.replaceAll("\\[", "").replaceAll("\\]", "");

           //System.out.println(badUser);
           insertInto("BannedUsers","Username", "'" + activeUsers.getSelectionModel().getSelectedItem().toString() + " '");
           deleteFrom("Users","Username = '" + activeUsers.getSelectionModel().getSelectedItem().toString() + "'");
           //System.out.println(badUser);
       } catch (SQLException e) {
           e.printStackTrace();
       }

    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeusersList();
        //bannedusersList();
        testList();


    }
}
