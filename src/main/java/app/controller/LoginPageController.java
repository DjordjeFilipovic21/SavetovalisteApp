package app.controller;

import app.repository.PsychotherapeutRepository;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginPageController {

    @FXML
    public VBox contentArea;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final PsychotherapeutRepository psychotherapeutRepository = new PsychotherapeutRepository();

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void onSubmit() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        int id = psychotherapeutRepository.validateCredentials(username, password);
        if (id != -1) {
            AppRootController.getInstance().navigateToPage("/app/fxml/HomePage.fxml");
            TopBarController.getInstance().setUserId(id);
            TopBarController.getInstance().onLogin();
        } else {
            TopBarController.getInstance().showErrorAlert("Invalid Credentials", "Wrong username or password.");
        }
    }
}