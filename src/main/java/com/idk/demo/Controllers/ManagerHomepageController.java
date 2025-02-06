package com.idk.demo.Controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class ManagerHomepageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

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


    public void displayManagerHomepage(ActionEvent event) throws Exception {

    }

    public void OnAddAccount(ActionEvent event) {

    }

    public void OnDeleteUsername(ActionEvent event){

    }

    public void OnPromoteUsername(ActionEvent event){

    }
}


