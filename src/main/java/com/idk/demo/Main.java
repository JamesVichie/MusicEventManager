package com.idk.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try{
            stage.setTitle("Music Event");
//            stage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("Views/LoginView.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            e.printStackTrace();
        }



    }
}
