package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


public class MainPageController {


    @FXML
    private VBox contentArea;


    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }


    @FXML
    private void onLoginButtonClick() {
        AppRootController.getInstance().navigateToPage("/app/fxml/LoginPage.fxml");
    }

    @FXML
    private void onSignInButtonClick() {
        AppRootController.getInstance().navigateToPage("/app/fxml/SignUpPage.fxml");
    }


    public void onPsychoTherapeutButtonClick(ActionEvent actionEvent) {
        AppRootController.getInstance().navigateToPage("/app/fxml/PsychotherapeutForm.fxml");
    }
}