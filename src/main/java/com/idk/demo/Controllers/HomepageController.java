package com.idk.demo.Controllers;

import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;


public class HomepageController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button signout;
    @FXML
    private Button managerOptions;

//    public void displayHomepage(ActionEvent event) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Views/HomepageView.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }

    public void onSignout(ActionEvent event) throws Exception {
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayManagerMenu(ActionEvent event) throws Exception {
        if (!Objects.requireNonNull(SessionManager.loadSessionPosition()).equalsIgnoreCase("Manager")){
            //displaying warning, eat input
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You do not have required permissions");
            alert.setContentText("Managers only!");
            alert.showAndWait();


        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/ManagerHomepageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
