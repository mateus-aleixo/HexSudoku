module com.example.trabalhocriativo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trabalhocriativo to javafx.fxml;
    exports com.example.trabalhocriativo;
}