package app.controller;

import app.repository.PsychotherapeutRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class SignUpPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;



    @FXML
    private BorderPane root;

    @FXML
    public VBox contentArea;

    private final PsychotherapeutRepository psychotherapeutRepository = new PsychotherapeutRepository();

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void onSignUp() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String fullname = fullnameField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            TopBarController.getInstance().showErrorAlert("Validation Error", "All fields are required.");
            return;
        }

        try {
            int id = psychotherapeutRepository.insertPsychotherapeut(username, email, password, fullname);
            if (id!=-1) {
                AppRootController.getInstance().navigateToPage("/app/fxml/HomePage.fxml");
                TopBarController.getInstance().setUserId(id);
                TopBarController.getInstance().onLogin();
            } else {
                TopBarController.getInstance().showErrorAlert("Sign-Up Failed", "Could not create account. Please try again.");
            }
        } catch (Exception e) {
            TopBarController.getInstance().showErrorAlert("Error", "An error occurred: " + e.getMessage());
        }
    }
}