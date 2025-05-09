package app;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.io.IOException;

public class AppRootController {

    private static AppRootController instance;

    @FXML
    private BorderPane mainContent;

    public AppRootController() {
        if (instance != null) {
            throw new IllegalStateException("AppRootController is already initialized!");
        }
        instance = this;
    }

    public static AppRootController getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AppRootController has not been initialized yet!");
        }
        return instance;
    }

    @FXML
    private void initialize() {
        navigateToPage("/app/MainPage.fxml");
    }

    public void navigateToPage(String fxmlPath) {
        try {
            if (mainContent.getCenter() != null) {
                FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainContent.getCenter());
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event -> {
                    // Load the new page after fade-out
                    loadNewPage(fxmlPath);
                });
                fadeOut.play();
            } else {
                loadNewPage(fxmlPath);
            }
        } catch (Exception e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    private void loadNewPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent page = loader.load();
            mainContent.setCenter(page);

            // Update the TopBar
            TopBarController.getInstance().pushOntoStack(fxmlPath);
            TopBarController.getInstance().updateButtonStates();
            TopBarController.getInstance().updateTitleAndProfileVisibility(fxmlPath);



        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}