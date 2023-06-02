package com.example.trabalhocriativo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AcceptedController implements Initializable {

    @FXML private Label timeLbl;
    @FXML private Label record;

    @FXML private Button replay;
    @FXML private Button records;
    @FXML private Button quit;

    private final String username;
    private final int difficulty;
    private final String time;
    private final ArrayList<Time> bestTimes = new ArrayList<>();
    private Parent root;
    private Stage stage;
    private Scene scene;

    public AcceptedController(String username, int difficulty, String time) throws IOException {
        this.username = username;
        this.difficulty = difficulty;
        this.time = time;
        StringBuilder stringBuilder = new StringBuilder();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\trabalhocriativo\\recordsData.txt"));

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        bufferedReader.close();

        String core = stringBuilder.toString();
        core = core + this.username + " " + this.difficulty + " " + this.time;

        String[] lines = core.split("\n");
        Arrays.sort(lines);

        core = String.join("\n", lines);

        FileWriter fileWriter = new FileWriter("src\\main\\resources\\com\\example\\trabalhocriativo\\recordsData.txt");

        fileWriter.write(core);
        fileWriter.close();

        bestTimes.clear();

        String[] lineCore;

        bufferedReader = new BufferedReader(new FileReader("src\\main\\resources\\com\\example\\trabalhocriativo\\recordsData.txt"));

        while (((line = bufferedReader.readLine()) != null)) {
            lineCore = line.split(" ");
            Time timeObject = new Time(lineCore[0], lineCore[1], lineCore[2]);
            bestTimes.add(timeObject);
        }

        bufferedReader.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.timeLbl.setText(this.time);

        LocalTime localTime = LocalTime.parse(this.time, DateTimeFormatter.ofPattern("HH:mm:ss"));

        String bestTime = "indefinido";

        for (Time currentTime : this.bestTimes) {
            if (currentTime.username.equals(this.username) && currentTime.difficulty.equals(String.valueOf(this.difficulty))) {
                bestTime = currentTime.time;
                break;
            }
        }

        System.out.println("Melhor tempo: " + bestTime + "\n");

        if (localTime.equals(LocalTime.parse(bestTime, DateTimeFormatter.ofPattern("HH:mm:ss")))) {
            System.out.println("Novo recorde: " + this.time + "\n");
            this.record.setVisible(true);
        }
        else {
            this.record.setVisible(false);
        }

    }

    @FXML
    private void replay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("difficulty.fxml"));

        this.root = loader.load();

        DifficultyController difficultyController = loader.getController();

        difficultyController.displayUsername(this.username);

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.setTitle("HexSudoku - Dificuldade");
        this.stage.show();

        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

    @FXML
    private void records() {
        StringBuilder record = new StringBuilder("Fácil\n");
        int easyCounter = 0, mediumCounter = 0, hardCounter = 0;

        for (Time bestTime : bestTimes) {
            if (bestTime.username.equals(this.username) && bestTime.difficulty.equals("1")) {
                easyCounter++;
                record.append(easyCounter).append(": ").append(bestTime.time).append("\n");
            }
        }

        if (easyCounter == 0)
            record.append("Sem fáceis resolvidos\n");

        record.append("\nMédio\n");

        for (Time bestTime : bestTimes) {
            if (bestTime.username.equals(this.username) && bestTime.difficulty.equals("2")) {
                mediumCounter++;
                record.append(mediumCounter).append(": ").append(bestTime.time).append("\n");
            }
        }

        if (mediumCounter == 0)
            record.append("Sem médios resolvidos\n");

        record.append("\nDifícil\n");

        for (Time bestTime : bestTimes) {
            if (bestTime.username.equals(this.username) && bestTime.difficulty.equals("3")) {
                hardCounter++;
                record.append(hardCounter).append(": ").append(bestTime.time).append("\n");
            }
        }

        if (hardCounter == 0)
            record.append("Sem difíceis resolvidos\n");

        System.out.println("\nTempos:\n" + record);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        int counter = easyCounter + mediumCounter + hardCounter;

        if (counter != 0) {
            alert.setTitle("Recordes");
            alert.setHeaderText(record.toString());
        }
        else {
            alert.setTitle("Sem recordes");
            alert.setHeaderText("Sem recordes para apresentar");
        }

        alert.showAndWait();
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));

        this.root = loader.load();

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.setTitle("HexSudoku - Login");
        this.stage.show();

        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

}
