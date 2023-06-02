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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RegisterController {

    @FXML Label errorLbl;

    @FXML TextField usernameTxtField;

    @FXML PasswordField passwordPwField;

    @FXML Button registerBtn;
    @FXML Button goBackBtn;

    @FXML
    private void register() throws IOException {
        ArrayList<String> usernameArr = new ArrayList<>();
        ArrayList<String> passwordArr = new ArrayList<>();
        String data = this.usernameTxtField.getText() + this.passwordPwField.getText();
        String[] dataArr;
        int repeatedIndex = -1, spaces = 0;

        File file = new File("src\\main\\resources\\com\\example\\trabalhocriativo\\loginData.txt");
        String fileCore;

        Scanner scanner = new Scanner(file);

        // Leitura dos dados
        while (scanner.hasNextLine()) {
            fileCore = scanner.nextLine();
            dataArr = fileCore.split(" ");

            if (!fileCore.equals("")) {
                usernameArr.add(dataArr[0]);
                passwordArr.add(dataArr[1]);
            }
        }

        for (int i = 0; i < usernameArr.size(); i++)
            if (usernameArr.get(i).equals(usernameTxtField.getText()))
                repeatedIndex = i;

        for (int i = 0; i < data.length(); i++)
            if(data.charAt(i) == ' ')
                spaces++;

        // Verifica se há usuários repetidos ou espaços no username
        if (repeatedIndex == -1 && spaces == 0) {
            FileWriter fileWriter = new FileWriter(file, true);

            // Escreve os dados
            fileWriter.write(usernameTxtField.getText() + " " + passwordPwField.getText() + "\n");
            fileWriter.close();

            usernameTxtField.setText("");
            passwordPwField.setText("");
            errorLbl.setText("Registo bem sucedido");

            System.out.println("Registo com sucesso");
        }
        else {
            // Exibe uma mensagem de erro
            errorLbl.setText("Credênciais inválidas");
        }
        
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        // Carrega o FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("HexSudoku - Login");

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - stage.getHeight()) / 2;

        stage.setX(x);
        stage.setY(y);
    }

}
