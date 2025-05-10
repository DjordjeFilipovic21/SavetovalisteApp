package app.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;
import java.util.Stack;

public class TopBarController {

    private static TopBarController instance;

    @FXML
    public VBox titleContainer;
    public HBox topBar;


    @FXML
    private VBox profileContainer;



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
    }

    @FXML
    private Button undoButton;


    @FXML
    private VBox contentArea;

    @FXML
    private ToggleButton themeToggleButton;

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
    private void onUndoButtonClick() {
        if (!undoStack.isEmpty()) {
            undoStack.pop();

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
            scene.getStylesheets().clear(); // Clear existing stylesheets
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(theme)).toExternalForm());
        }
    }



    public void updateTitleAndProfileVisibility(String currentPage) {
        boolean isMainPage = "/app/fxml/MainPage.fxml".equals(currentPage);
        titleContainer.setVisible(!isMainPage);
        titleContainer.setManaged(!isMainPage);
        profileContainer.setVisible(!isMainPage);
        profileContainer.setManaged(!isMainPage);
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
}