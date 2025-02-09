package com.idk.demo.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static com.idk.demo.Models.DatabasesInteract.EditTables.updatePassword;
import static com.idk.demo.Models.DatabasesInteract.EditTables.updateUsername;

public class UserProfileController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button displayHomepage;
    @FXML
    private Button onSignout;
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private Button updateUsername;
    @FXML
    private Button updatePassword;

    public void onSignout(ActionEvent event) throws Exception {
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayHomepage(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        Stage stage = (Stage) displayHomepage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void onUpdateUsername(){
        if ((newUsername.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }
        String currentUsername = SessionManager.loadSession();
        System.out.println(currentUsername);
        String updatedUsername = newUsername.getText();
        System.out.println(updatedUsername);


        updateUsername(currentUsername, newUsername.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success! Below user has been updated");
        alert.setContentText("Username: " + updatedUsername);
        newUsername.clear();
        alert.showAndWait();

    }

    public void onUpdatePassword(){
        if ((newPassword.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }
        String currentUsername = SessionManager.loadSession();
        String updatedPassword = newPassword.getText();

        updatePassword(currentUsername, newPassword.getText());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success! Below user has been updated");
        alert.setContentText("Password: " + updatedPassword);
        newPassword.clear();
        alert.showAndWait();

    }
}

