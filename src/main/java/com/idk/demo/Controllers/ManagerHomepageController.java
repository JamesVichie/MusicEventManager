package com.idk.demo.Controllers;

import com.idk.demo.Models.Users;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static com.idk.demo.Models.DatabasesInteract.DeleteRow.deleteUserRow;
import static com.idk.demo.Models.DatabasesInteract.GetTables.getUsers;
import static com.idk.demo.Models.DatabasesInteract.InsertRow.insertUserRow;
import static com.idk.demo.Models.DatabasesInteract.EditTables.promoteUser;


public class ManagerHomepageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button onSignout;
    @FXML
    private Button displayHomepage;
    @FXML
    private Button deleteButton;
    @FXML
    private Button promoteButton;
    @FXML
    private Button addAccountButton;
    @FXML
    private TextField promoteUsernameTF;
    @FXML
    private TextField deleteUsernameTF;
    @FXML
    private TextField addUsernameTF;
    @FXML
    private TextField addPasswordTF;
    @FXML
    private RadioButton som;
    @FXML
    private RadioButton mos;




    public void displayManagerHomepage(ActionEvent event) throws Exception {

    }

    public void OnAddAccount(ActionEvent event) {
        if ((addUsernameTF.getText().isEmpty()) || (addPasswordTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username or Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Check if a radio button is selected
        if (!som.isSelected() && !mos.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Role not selected.");
            alert.setContentText("Please select either 'Manager' or 'Staff' for the position.");
            alert.showAndWait();
            return;
        }
        String role = "";


        if (som.isSelected()){
            role = "Staff";
        }
        else if (mos.isSelected()){
            role = "Manager";
        }

        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(addUsernameTF.getText().trim()) && p.getPassword().equals(addPasswordTF.getText()));
        if (exists) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username already exists.");
            alert.setContentText("Please choose a different username.");
            alert.showAndWait();
            return;
        }
        else{
            insertUserRow(addUsernameTF.getText(), addPasswordTF.getText(), role);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success! Below user has been added");
            alert.setContentText("Username: " + addUsernameTF.getText() + "\n Password: " + addPasswordTF.getText() + "\n Role: " + role);
            addUsernameTF.clear();
            addPasswordTF.clear();
            som.setSelected(false);
            mos.setSelected(false);
            alert.showAndWait();
        }
    }

    public void OnDeleteUsername(ActionEvent event){
        if ((deleteUsernameTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username or Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(deleteUsernameTF.getText().trim()));

        if (exists) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WAIT");
            alert.setContentText("Are you sure you want to delete account: \n Username: " + deleteUsernameTF.getText());
            try {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    deleteUsernameTF.clear();
                    deleteUserRow(deleteUsernameTF.getText());
                }
            //i dont undertsand why the .get() needs the isPresent() or how to add it so added an exception which shouldnt ever trigger
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
            alert.showAndWait();
        }
    }

    public void OnPromoteUsername(ActionEvent event){
        if ((promoteUsernameTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }
        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(promoteUsernameTF.getText().trim()));

        if (exists) {
            //check they are staff
            String position = "";
            for (Users user : users) {
                if (user.getUsername().equalsIgnoreCase(promoteUsernameTF.getText())){
                    position = user.getPosition();
                    System.out.println(position);
                }
            }
            if (position.equalsIgnoreCase("Staff")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Promotion Confirmation");
                alert.setHeaderText("User: \"" + promoteUsernameTF.getText() + "\" to be made a Manager?");
                alert.setContentText("Please confirm.");
               try {
                   if (alert.showAndWait().get() == ButtonType.OK) {
                       promoteUser(promoteUsernameTF.getText());
                       promoteUsernameTF.clear();
                   }
               } catch (NoSuchElementException e) {
                   e.printStackTrace();
               }
            }
            else if (position.equalsIgnoreCase("Manager")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText("User is already a Manager.");
                alert.setContentText("Please choose a different username.");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
        }

    }

    public void displayHomepage(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        Stage stage = (Stage) displayHomepage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onSignout(ActionEvent event) throws Exception {
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


