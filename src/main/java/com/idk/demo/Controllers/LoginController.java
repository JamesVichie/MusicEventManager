package com.idk.demo.Controllers;

import com.idk.demo.Models.Users;
import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.idk.demo.Models.DatabasesInteract.GetTables.getUsers;

public class LoginController {

    // FXML injected fields for the UI components
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    // Method called when the login button is clicked
    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        // Print the username and password to the console for debugging purposes
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());

        // Fetch the list of users from the database
        ArrayList<Users> users = getUsers();
        String position = "";

        // Check if the entered username and password match any user in the database
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(usernameField.getText().trim()) && p.getPassword().equals(passwordField.getText()));

        // Loop through the users to fetch the position (role) of the matched username
        for (Users user : users) {
            if (user.getUsername().equalsIgnoreCase(usernameField.getText())){
                position = user.getPosition();
                System.out.println(position);
            }
        }

        // Debugging output to confirm position and if the user was found
        System.out.println(position);
        System.out.println("User found: " + exists);

        // If the login details are correct, proceed to the homepage
        if (exists) {
            try {
                // Save the session (username and role) for future use
                SessionManager.saveSession(usernameField.getText(), position);

                // Load the homepage view
                Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Set the window properties (size and non-resizable)
                stage.setWidth(1930);  // Set the window width
                stage.setHeight(1080);  // Set the window height
                stage.setResizable(false);  // Make the window non-resizable
                stage.centerOnScreen();  // Center the window on the screen

                // Set the scene and show the window
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                // Print any exceptions to the console for debugging
                e.printStackTrace();
            }
        } else {
            // If login details are incorrect, display an error message
            errorLabel.setText("Incorrect Login Details");
        }
    }
}
