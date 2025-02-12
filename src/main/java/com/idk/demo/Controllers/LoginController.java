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

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
        ArrayList<Users> users = getUsers();
        String position = "";

        //GET A MANAGER NOT A PASSWPRD
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(usernameField.getText().trim()) && p.getPassword().equals(passwordField.getText()));

        for (Users user : users) {
            if (user.getUsername().equalsIgnoreCase(usernameField.getText())){
                position = user.getPosition();
                System.out.println(position);
            }
        }
        System.out.println(position);

        System.out.println("User found: " + exists);
        if (exists) {
            try {
                SessionManager.saveSession(usernameField.getText(), position);
                Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setWidth(1930);  // Set the window width
                stage.setHeight(1080);  // Set the window height
                stage.setResizable(false);  // Optional: makes the window non-resizable
                stage.centerOnScreen();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Incorrect Login Details");
        }
    }

}
