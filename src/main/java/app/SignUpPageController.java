package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SignUpPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private BorderPane root;

    @FXML
    public VBox contentArea;
    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }



    @FXML
    private void onSignUp() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        // Add logic to handle sign-up (e.g., save to database, validate input, etc.)
        System.out.println("Sign-up successful for user: " + username);
    }
}