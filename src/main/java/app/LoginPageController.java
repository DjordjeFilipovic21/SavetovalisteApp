package app;

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

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void onSubmit() {
        AppRootController.getInstance().navigateToPage("/app/HomePage.fxml");
    }
}