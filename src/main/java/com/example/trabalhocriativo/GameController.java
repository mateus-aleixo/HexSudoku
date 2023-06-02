package com.example.trabalhocriativo;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private Label usernameLbl;
    @FXML private Label timeLbl;

    @FXML private Button replay;
    @FXML private Button exit;
    @FXML private  Button pause;
    @FXML private Button submit;
    @FXML private Button records;
    @FXML private Button delete;
    @FXML private Button available;
    @FXML private Button btn0;
    @FXML private Button btn1;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Button btn5;
    @FXML private Button btn6;
    @FXML private Button btn7;
    @FXML private Button btn8;
    @FXML private Button btn9;
    @FXML private Button btn10;
    @FXML private Button btn11;
    @FXML private Button btn12;
    @FXML private Button btn13;
    @FXML private Button btn14;
    @FXML private Button btn15;

    @FXML private GridPane gridPane;
    private GridPane[] subGrids;

    private final String username;
    private final int difficulty;
    private LocalTime time = LocalTime.parse("00:00:00");
    private Timeline timeline;
    private Button[] btnArr;
    private Parent root;
    private Stage stage;
    private Scene scene;

    public GameController(String username, int difficulty) {
        this.username = username;
        this.difficulty = difficulty;
    }

    private void createBoard(GridPane mainGrid, int difficulty) {
        int size = 16;
        int area = size * size;
        double leads = switch (difficulty) {
            case 1 -> area * 0.5;
            case 2 -> area * 0.375;
            case 3 -> area * 0.25;
            default -> area;
        };

        int blanks = (int) (area - leads);

        Board board = new Board(size, blanks);
        char[][] solution = board.fillValues();

        System.out.println("Dificuldade: " + difficulty + " Pistas: " + (int) leads);
        System.out.println("\nTabuleiro:");
        board.printBoard();

        System.out.println("\nSolução:");
        board.displayMatrix(solution);

        char[][] matrix = board.getMat();

        // board de exemplo para mostrar na apresentação
        /*
        char[][] matrix = {
                {'1', '6', '0', 'A', '8', 'D', '9', '2', '5', 'E', '3', '7', 'C', 'B', '4', 'F'},
                {'9', 'E', 'D', '8', 'B', '3', '0', '4', 'F', 'C', 'A', '2', '5', '7', '6', '1'},
                {'7', '4', 'C', '2', 'F', '5', '6', '1', '8', 'D', 'B', '0', 'E', '3', '9', 'A'},
                {'5', 'F', '3', 'B', '7', 'A', 'E', 'C', '4', '1', '9', '6', '0', '8', 'D', '2'},
                {'E', 'C', '7', 'F', '9', '2', 'D', 'A', 'B', '6', '4', '8', '1', '5', '3', '0'},
                {'6', '9', '5', '0', 'E', 'B', '4', '8', '3', '2', 'F', '1', '7', 'A', 'C', 'D'},
                {'D', '3', '2', '4', 'C', '7', '1', '6', 'E', '5', '0', 'A', '9', 'F', 'B', '8'},
                {'8', 'B', 'A', '1', '5', '0', 'F', '3', 'C', '9', '7', 'D', '6', '4', '2', 'E'},
                {'2', '1', '8', 'C', '4', 'E', '5', 'B', '0', '3', 'D', '9', 'A', '6', 'F', '7'},
                {'B', '0', 'F', 'E', '1', '6', '7', '9', 'A', '4', '2', '5', 'D', 'C', '8', '3'},
                {'A', '7', '4', '6', '3', 'F', '8', 'D', '1', 'B', 'E', 'C', '2', '9', '0', '5'},
                {'3', '5', '9', 'D', '2', 'C', 'A', '0', '6', '7', '8', 'F', 'B', '1', 'E', '4'},
                {'4', 'D', 'B', '9', '0', '1', 'C', '7', '2', '8', '5', '3', 'F', 'E', 'A', '6'},
                {'F', 'A', 'E', '3', '6', '9', '2', '5', '7', '0', 'C', '4', '8', 'D', '1', 'B'},
                {'0', '8', '1', '5', 'D', '4', 'B', 'F', '9', 'A', '6', 'E', '3', '2', '7', 'C'},
                {'C', '2', '6', '7', 'A', '8', '3', 'E', 'D', 'F', '1', 'B', '4', '0', '5', '9'}
        };
        */

        GridPane[] subGrids = createSubGrids(matrix, size);
        mainGrid.setAlignment(Pos.CENTER);

        for (int i = 0; i < size; i++) {
            GridPane subGrid = subGrids[i];

            subGrid.setPadding(new Insets(10));
            mainGrid.add(subGrid, i % 4, i / 4);
        }
    }

    private GridPane[] createSubGrids(char[][] matrix, int size) {
        GridPane[] subGrids = new GridPane[size];
        char[][][] subMatrices = new char[16][4][4];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int subMatrixIndex = (i / 4) * 4 + (j / 4);
                int subMatrixRow = i % 4;
                int subMatrixCol = j % 4;

                subMatrices[subMatrixIndex][subMatrixRow][subMatrixCol] = matrix[i][j];
            }
        }

        for (int i = 0; i < size; i++)
            subGrids[i] = createSubGrid(subMatrices[i], size);

        this.subGrids = subGrids;

        return subGrids;
    }

    private GridPane createSubGrid(char[][] subMatrix, int size) {
        GridPane subGrid = new GridPane();
        subGrid.setAlignment(Pos.CENTER);

        Font leadFont = Font.font("Arial", FontWeight.BOLD, 14);
        Font editableFont = Font.font("Arial", 14);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                TextField textField = new TextField();

                textField.getStyleClass().add("text-field");

                if (subMatrix[i][j] != '-') {
                    textField.setText(String.valueOf(subMatrix[i][j]).toUpperCase());
                    textField.setFont(leadFont);
                    textField.setEditable(false);
                } else {
                    textField.setText("");
                    textField.setFont(editableFont);
                    textField.setEditable(true);
                }

                for (int k = 0; k < size; k++) {
                    Button button = btnArr[k];

                    button.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                                if (textField.isEditable())
                                    textField.focusedProperty().addListener(
                                            (observableValue12, aBoolean12, t112) -> textField.setText(button.getText())
                                    );
                    });
                }

                this.delete.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                        if (textField.isEditable())
                            textField.focusedProperty().addListener(
                                    (observableValue1, aBoolean1, t11) -> textField.setText("")
                            );
                });

                subGrid.add(textField, j, i);
            }
        }

        return subGrid;
    }

    public static boolean isValid(int[][] board) {
        for (int i = 0; i < 16; i++) {
            if (!isValidRow(board, i) || !isValidColumn(board, i) || !isValidRegion(board, (i / 4) * 4, (i % 4) * 4)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidRow(int[][] board, int row) {
        boolean[] seen = new boolean[17];
        for (int col = 0; col < 16; col++) {
            int digit = board[row][col];
            if (digit != 0 && seen[digit]) {
                return false;
            }
            seen[digit] = true;
        }
        return true;
    }

    private static boolean isValidColumn(int[][] board, int col) {
        boolean[] seen = new boolean[17];
        for (int row = 0; row < 16; row++) {
            int digit = board[row][col];
            if (digit != 0 && seen[digit]) {
                return false;
            }
            seen[digit] = true;
        }
        return true;
    }

    private static boolean isValidRegion(int[][] board, int startRow, int startCol) {
        boolean[] seen = new boolean[17];
        for (int row = startRow; row < startRow + 4; row++) {
            for (int col = startCol; col < startCol + 4; col++) {
                int digit = board[row][col];
                if (digit != 0 && seen[digit]) {
                    return false;
                }
                seen[digit] = true;
            }
        }
        return true;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.btnArr = new Button[16];

        this.btnArr[0] = btn0;
        this.btnArr[1] = btn1;
        this.btnArr[2] = btn2;
        this.btnArr[3] = btn3;
        this.btnArr[4] = btn4;
        this.btnArr[5] = btn5;
        this.btnArr[6] = btn6;
        this.btnArr[7] = btn7;
        this.btnArr[8] = btn8;
        this.btnArr[9] = btn9;
        this.btnArr[10] = btn10;
        this.btnArr[11] = btn11;
        this.btnArr[12] = btn12;
        this.btnArr[13] = btn13;
        this.btnArr[14] = btn14;
        this.btnArr[15] = btn15;

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.time = this.time.plusSeconds(1);
            this.timeLbl.setText(this.time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }));
        this.timeline.setCycleCount(Animation.INDEFINITE);

        this.usernameLbl.setText(this.username);

        createBoard(this.gridPane, this.difficulty);

        this.timeline.play();
    }

    @FXML
    private void replay(ActionEvent event) throws IOException {
        // Carrega o FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("difficulty.fxml"));

        this.root = loader.load();

        DifficultyController difficultyController = loader.getController();

        difficultyController.displayUsername(this.username);

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);

        this.stage.setScene(this.scene);
        this.stage.setTitle("HexSudoku - Dificuldade");
        this.stage.show();

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

    @FXML
    private void leave(ActionEvent event) throws IOException {
        // Carrega o FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));

        this.root = loader.load();

        this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        this.scene = new Scene(this.root);

        this.stage.setScene(this.scene);
        this.stage.setTitle("HexSudoku - Login");
        this.stage.show();

        // Centraliza a janela
        Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
        double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
        double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

        this.stage.setX(x);
        this.stage.setY(y);
    }

    @FXML
    private void pause() {
        if (this.timeline.getStatus().equals(Animation.Status.PAUSED)) {
            this.timeline.play();
            this.gridPane.setVisible(true);
            this.pause.setText("Pausar");
        } else if (this.timeline.getStatus().equals(Animation.Status.RUNNING)) {
            this.timeline.pause();
            this.gridPane.setVisible(false);
            this.pause.setText("Continuar");
        }
    }

    private boolean isComplete(int[][] matrix) {
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                if (matrix[i][j] == -1)
                    return false;

        return true;
    }

    @FXML
    private void submit(ActionEvent event) throws IOException {
        int[][] matrix = new int[16][16];

        int rowOffset, colOffset;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rowOffset = i * 4;
                colOffset = j * 4;

                GridPane subGrid = this.subGrids[i * 4 + j];
                ObservableList<Node> nodes = subGrid.getChildren();

                for (Node node : nodes) {
                    if (node instanceof TextField textField) {
                        String text = textField.getText().trim();

                        int row = GridPane.getRowIndex(textField);
                        int col = GridPane.getColumnIndex(textField);

                        if (text.isEmpty()) {
                            matrix[row + rowOffset][col + colOffset] = -1;
                        } else {
                            try {
                                int value = Integer.parseInt(text, 16);
                                matrix[row + rowOffset][col + colOffset] = value;
                            } catch (NumberFormatException e) {
                                matrix[row + rowOffset][col + colOffset] = -1;
                            }
                        }
                    }
                }
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (isComplete(matrix)) {
            if (isValid(matrix)) {
                System.out.println("Resposta correta\n");

                // Carrega o FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("accepted.fxml"));

                AcceptedController acceptedController = new AcceptedController(this.username, this.difficulty, this.timeLbl.getText());
                loader.setController(acceptedController);

                this.root = loader.load();
                this.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                this.scene = new Scene(this.root);
                this.stage.setScene(this.scene);
                this.stage.setTitle("HexSudoku - Sucesso");
                this.stage.show();

                // Centraliza a janela
                Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
                double x = rectangle2D.getMinX() + (rectangle2D.getWidth() - this.stage.getWidth()) / 2;
                double y = rectangle2D.getMinY() + (rectangle2D.getHeight() - this.stage.getHeight()) / 2;

                this.stage.setX(x);
                this.stage.setY(y);
            }
            else {
                System.out.println("Resposta errada\n");
                alert.setHeaderText("Tem a sua resposta errada");
                alert.setTitle("Resposta errada");
                alert.showAndWait();
            }
        }
        else {
            System.out.println("Tabuleiro por completar\n");
            alert.setHeaderText("Precisa de terminar para submeter");
            alert.setTitle("Tabuleiro incompleto");
            alert.showAndWait();
        }
    }

    @FXML
    private void markFirstEmptySquare() {
        ObservableList<Node> nodes = gridPane.getChildren();

        for (Node node : nodes)
            if (node instanceof GridPane subGrid) {
                ObservableList<Node> subNodes = subGrid.getChildren();

                for (Node subNode : subNodes)
                    if (subNode instanceof TextField textField)
                        if (textField.getText().isEmpty()) {
                            String previousStyle = textField.getStyle();

                            textField.getStyleClass().add("empty");

                            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

                            pauseTransition.setOnFinished(event -> {
                                textField.getStyleClass().remove("empty");
                                textField.getStyleClass().add(previousStyle);
                            });
                            pauseTransition.play();

                            return;
                        }
            }
    }
}
