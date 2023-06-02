package com.example.trabalhocriativo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DifficultyController {

    @FXML Label usernameLbl;
    @FXML Button easyBtn;
    @FXML Button mediumBtn;
    @FXML Button hardBtn;
    @FXML Button exitBtn;
    //@FXML Button recordBtn;

    private String username;
    private int difficulty;

    private Stage stage;
    private Scene scene;

    public void setUsername(String username) {
        this.username = username;
    }

    public void displayUsername(String username) {
        setUsername(username);
        this.usernameLbl.setText("Bem-vindo " + username);
    }

    private void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @FXML
    public void start(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));

        GameController gameController = new GameController(this.username, this.difficulty);
        loader.setController(gameController);

        Parent root = loader.load();

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(root);
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        String difficulty = switch (this.difficulty) {
            case 1 -> "Fácil";
            case 2 -> "Médio";
            case 3 -> "Difícil";
            default -> "Erro";
        };

        this.stage.setTitle("HexSudoku - " + difficulty);
        this.stage.setScene(scene);
        this.stage.show();

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

    public void easy(ActionEvent event) throws IOException {
        setDifficulty(1);
        start(event);
    }

    public void medium(ActionEvent event) throws IOException {
        setDifficulty(2);
        start(event);
    }

    public void hard(ActionEvent event) throws IOException {
        setDifficulty(3);
        start(event);
    }

    @FXML
    public void exit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));

        Parent root = loader.load();

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        this.scene = new Scene(root);

        this.stage.setTitle("HexSudoku - Login");
        this.stage.setScene(this.scene);
        this.stage.show();
    }
}
