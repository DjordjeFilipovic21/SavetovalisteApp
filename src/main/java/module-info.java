module com.example.bazedjordjefilipovicnikolajovic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens app to javafx.fxml;
    exports app;
    exports app.repository;
    opens app.repository to javafx.fxml;
    exports app.controller;
    opens app.controller to javafx.fxml;
}