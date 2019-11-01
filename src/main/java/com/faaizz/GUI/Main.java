package com.faaizz.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try{

            Parent root= FXMLLoader.load(getClass().getResource("main.fxml"));

            primaryStage.setTitle("Contacts");

            //GET SCREEN SIZE
            Rectangle2D screenSize= Screen.getPrimary().getBounds();

            //SET SCENE AND MAKE ITS SIZE FULLSCREEN
            primaryStage.setScene(new Scene(root, ( screenSize.getWidth() - 75), ( screenSize.getHeight() - 50 )));

            //SHOW STAGE
            primaryStage.show();

        } catch(Exception e){
            //CATCH BLOCK TO HANDLE EXCEPTIONS THROWN FROM ANY PART OF THE APPLICATION
            Alert exceptionAlert= new Alert(Alert.AlertType.ERROR);

            exceptionAlert.setResizable(true);

            exceptionAlert.setHeaderText("An Exception Ocurred");
            exceptionAlert.setContentText(e.getClass().getName() + "\n" + e.getMessage());

            exceptionAlert.show();

            e.printStackTrace();
        }

    }
}
