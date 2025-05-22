package app.controller;

import app.repository.ImageRepository;
import app.repository.PsychotherapeutRepository;
import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Stack;

public class TopBarController {

    private static TopBarController instance;

    @FXML
    private final BooleanProperty imgVisible = new SimpleBooleanProperty(false);




    @FXML
    public VBox titleContainer;
    public HBox topBar;
    public ImageView profileImage;


    @FXML
    private VBox profileContainer;

    private Integer userId = null;


    public static TopBarController getInstance() {
        if (instance == null) {
            instance = new TopBarController();
        }
        return instance;
    }


    @FXML
    private void initialize() {
        instance = this;
        System.out.println("TopBarController initialized");
        profileContainer.visibleProperty().bind(imgVisible);
        profileContainer.managedProperty().bind(imgVisible);
    }

    @FXML
    private Button undoButton;


    @FXML
    private VBox contentArea;

    @FXML
    private ToggleButton themeToggleButton;

    final private app.repository.ImageRepository imageRepository = new app.repository.ImageRepository();

    final private app.repository.PsychotherapeutRepository psychotherapeutRepository = new app.repository.PsychotherapeutRepository();

    private final Stack<String> undoStack = new Stack<>();




    public void setContentArea(VBox contentArea) {
        this.contentArea = contentArea;
    }

    public void pushOntoStack(String page) {
        if (!undoStack.isEmpty() && !undoStack.peek().equals(page)) {
            undoStack.push(page);
        } else if (undoStack.isEmpty()) {
            undoStack.push(page);
        }
    }

    @FXML
    public void onUndoButtonClick() {
        if (!undoStack.isEmpty()) {
            String page = undoStack.pop();
            if(page.equalsIgnoreCase("/app/fxml/HomePage.fxml")) {
                onLogout();
            }
            else if (page.equalsIgnoreCase("/app/fxml/ProfilePage.fxml")) {
                this.setImgVisible(true);
            }
            if (!undoStack.isEmpty()) {
                String previousPage = undoStack.peek();
                AppRootController.getInstance().navigateToPage(previousPage);
            }
        }

        updateButtonStates();
    }





    @FXML
    private void onThemeToggle() {
        boolean isDarkMode = themeToggleButton.isSelected();
        String theme = isDarkMode ? "/app/css/dark-theme.css" : "/app/css/light-theme.css";
        themeToggleButton.setText(isDarkMode ? "🌙" : "☀");
        applyTheme(theme);
    }

    private void applyTheme(String theme) {
        Scene scene = contentArea.getScene();
        if (scene != null) {
            Node root = scene.getRoot();
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), root);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                scene.getStylesheets().clear();
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(theme)).toExternalForm());

                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
                fadeIn.setFromValue(0.0);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        }
    }



    public void updateTitleAndProfileVisibility(String currentPage) {
        boolean isMainPage = "/app/fxml/MainPage.fxml".equals(currentPage);
        titleContainer.setVisible(!isMainPage);
        titleContainer.setManaged(!isMainPage);
    }

    void updateButtonStates() {
        undoButton.setDisable(undoStack.size() <= 1);
    }



    public Stack<String> getUndoStack() {
        return undoStack;
    }

    public ToggleButton getThemeToggleButton() {
        return themeToggleButton;
    }

    public void setThemeToggleButton(ToggleButton themeToggleButton) {
        this.themeToggleButton = themeToggleButton;
    }

    public VBox getContentArea() {
        return contentArea;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public void setUndoButton(Button undoButton) {
        this.undoButton = undoButton;
    }

    public HBox getTopBar() {
        return topBar;
    }

    public void setTopBar(HBox topBar) {
        this.topBar = topBar;
    }

    public void onLogin() {
        this.setImgVisible(true);
        updateProfileImage(userId);
        profileContainer.getParent().layout();
    }

    private void updateProfileImage(int userId) {
        PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
        delay.setOnFinished(event -> {
            int imgId = psychotherapeutRepository.getPsychotherapistImageById(userId);
            if(imgId == 0) {
                Image plusImage = new Image(Objects.requireNonNull(getClass().getResource("/app/images/profile.png")).toExternalForm());
                profileImage.setImage(plusImage);
            }
            else
                profileImage.setImage(new Image(new ByteArrayInputStream(imageRepository.getImageDataById(imgId))));
        });
        delay.play();
    }

    public void onLogout() {
        this.setImgVisible(false);
        profileImage.setImage(null);
    }

    @FXML
    public void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile)) {
                int newImgId = imageRepository.uploadImageToDatabase(selectedFile.getName(), fis);

                if (newImgId > 0 && userId != null) {
                    psychotherapeutRepository.updateImgId(userId, newImgId);
                    updateProfileImage(userId);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToDatabase(String fileName, FileInputStream imageStream) {
        imageRepository.uploadImageToDatabase(fileName, imageStream);
    }



    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onProfileImageClick() {
        this.setImgVisible(false);
        AppRootController.getInstance().navigateToPage("/app/fxml/ProfilePage.fxml");
    }

    public boolean isImgVisible() {
        return imgVisible.get();
    }

    public BooleanProperty imgVisibleProperty() {
        return imgVisible;
    }
    public void setImgVisible(boolean imgVisible) {
        this.imgVisible.set(imgVisible);
    }

    public void onRemoveImage() {
        psychotherapeutRepository.updateImgId(userId, null);
        Image plusImage = new Image(Objects.requireNonNull(getClass().getResource("/app/images/profile.png")).toExternalForm());
        profileImage.setImage(plusImage);
    }
}