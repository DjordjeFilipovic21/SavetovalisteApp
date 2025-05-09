package app;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class HomePageController {
    @FXML
    private VBox contentArea;

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void onLogoutButtonClick() {
        AppRootController.getInstance().navigateToPage("/app/LoginPage.fxml");
    }

    @FXML
    private void onSettingsButtonClick() {
        AppRootController.getInstance().navigateToPage("/app/SettingsPage.fxml");
    }

    @FXML
    private void onProfileButtonClick() {
        AppRootController.getInstance().navigateToPage("/app/ProfilePage.fxml");
    }

}
