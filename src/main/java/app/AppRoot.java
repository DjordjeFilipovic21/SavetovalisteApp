package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppRoot {
    private static AppRoot instance;
    private final Stage stage;

    private AppRoot(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    public static AppRoot getInstance(Stage stage) {
        if (instance == null) {
            instance = new AppRoot(stage);
        }
        return instance;
    }

    private void initializeUI() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/AppRoot.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1280, 720);
            stage.setTitle("Main Page");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        stage.show();
    }
}