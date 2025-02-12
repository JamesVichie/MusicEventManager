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

    // FXML injected fields for the UI components
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

    // Method to handle user sign out
    public void onSignout(ActionEvent event) throws Exception {
        // Clear session data on sign out
        SessionManager.clearSession();

        // Load the login screen after sign-out
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to display the homepage after successful sign-in
    public void displayHomepage(ActionEvent event) throws Exception {
        // Load the homepage view
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        Stage stage = (Stage) displayHomepage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to handle username update
    public void onUpdateUsername(ActionEvent event) throws Exception {
        // Check if the new username is empty
        if (newUsername.getText().isEmpty()) {
            // Show a warning alert if the username field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Fetch the current username from the session
        String currentUsername = SessionManager.loadSession();
        System.out.println(currentUsername);
        String updatedUsername = newUsername.getText();
        System.out.println(updatedUsername);

        // Update the username in the database
        updateUsername(currentUsername, updatedUsername);

        // Show a success alert after updating the username
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success! Below user has been updated");
        alert.setContentText("Username: " + updatedUsername);
        newUsername.clear(); // Clear the text field after update
        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Method to handle password update
    public void onUpdatePassword(ActionEvent event) throws Exception {
        // Check if the new password is empty
        if (newPassword.getText().isEmpty()) {
            // Show a warning alert if the password field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Fetch the current username from the session
        String currentUsername = SessionManager.loadSession();
        String updatedPassword = newPassword.getText();

        // Update the password in the database
        updatePassword(currentUsername, updatedPassword);

        // Show a success alert after updating the password
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success! Below user has been updated");
        alert.setContentText("Password: " + updatedPassword);
        newPassword.clear(); // Clear the text field after update
        alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
