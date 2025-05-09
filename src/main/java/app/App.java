package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        AppRoot mainFrame = AppRoot.getInstance(stage);
        mainFrame.show();
    }

    public static void main(String[] args) {
        launch();
    }
}