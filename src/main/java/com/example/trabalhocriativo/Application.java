package com.example.trabalhocriativo;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Carrega o FXML
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - stage.getHeight()) / 2;

        stage.setX(x);
        stage.setY(y);
    }

    public static void main(String[] args) {
        launch();
    }
}