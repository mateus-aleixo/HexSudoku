package com.example.trabalhocriativo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class LoginController {

    @FXML AnchorPane sceneAP;
    @FXML Label errorLbl;
    @FXML TextField usernameTxtField;
    @FXML PasswordField passwordPwField;
    @FXML Button registerBtn;
    @FXML Button loginBtn;
    @FXML Button exitBtn;
    private Stage stage;

    private FXMLLoader loadFXML(ActionEvent event,String fxmlFile, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        this.stage.setScene(scene);
        this.stage.show();

        this.stage.setTitle("HexSudoku - " + title);

        return loader;
    }

    public boolean safeLogin(String username, String password) throws FileNotFoundException {
        boolean valid = false;

        if (!(username.isEmpty() || password.isEmpty())) { // Verifica se o username e a pw não estão vazios
            ArrayList<String> usernameArr = new ArrayList<>();
            ArrayList<String> passwordArr = new ArrayList<>();

            File file = new File("src\\main\\resources\\com\\example\\trabalhocriativo\\loginData.txt"); // PATH para os dados
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {
                String currentLine = scan.nextLine();

                String[] data = currentLine.split(" ");

                if (!currentLine.equals("")) {
                    usernameArr.add(data[0]); // Armazena o username
                    passwordArr.add(data[1]); // Armazena a pw
                }
            }
            for (int i = 0; i < usernameArr.size(); i++) {
                if (usernameArr.get(i).equals(username) && passwordArr.get(i).equals(password)) {
                    valid = true; // Se o username e a pw forem corretos define como válido
                    break;
                }
            }
        }
        else {
            this.errorLbl.setText("Insira os seus dados"); // Mensagem de erro
        }

        if (!valid && !username.isEmpty() && !password.isEmpty()) {
            this.errorLbl.setText("Nome de utilizador ou palavra-passe errada"); // Mensagem de erro
        }

        return valid;
    }

    public void login(ActionEvent event) throws IOException {
        String username = this.usernameTxtField.getText();
        String password = this.passwordPwField.getText();
        boolean safe = safeLogin(username, password);

        if (safe) {
            FXMLLoader loader = loadFXML(event, "difficulty.fxml", "Dificuldade"); // Carrega o FXML

            // Centraliza a janela
            Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
            double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
            double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

            this.stage.setX(x);
            this.stage.setY(y);

            DifficultyController difficultyController = loader.getController();

            difficultyController.displayUsername(username); // Passa o username para o DifficultyController
        }
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        loadFXML(event, "register.fxml", "Registar"); // Carrega o FXML

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

    @FXML
    public void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Sair");
        alert.setHeaderText("Está prestes a sair da aplicação!");
        alert.setContentText("Tem a certeza que deseja sair?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.stage = (Stage) this.sceneAP.getScene().getWindow();
            this.stage.close();
            System.exit(0);
        }
    }

}