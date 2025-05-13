package app.controller;

import app.model.Psychotherapist;
import app.repository.ImageRepository;
import app.repository.PsychotherapeutRepository;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class ProfilePageController {

    @FXML
    public ImageView profileImage;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public VBox contentArea;


    private final PsychotherapeutRepository psychotherapeutRepository = new PsychotherapeutRepository();

    private final ImageRepository imageRepository = new ImageRepository();

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
        Integer userId = TopBarController.getInstance().getUserId();
        if (userId != null) {
            loadProfileData(userId);
        }
    }

    private void loadProfileData(int userId) {
        Psychotherapist psychotherapist = psychotherapeutRepository.getPsychotherapistById(userId);
        if (psychotherapist != null) {
            int imgId = psychotherapeutRepository.getPsychotherapistImageById(userId);
            if(imgId == 0) {
                Image plusImage = new Image(Objects.requireNonNull(getClass().getResource("/app/images/profile.png")).toExternalForm());
                profileImage.setImage(plusImage);
            }
            else
                profileImage.setImage(new Image(new ByteArrayInputStream(imageRepository.getImageDataById(imgId))));

            usernameField.setText(psychotherapist.getUsername());
            fullNameField.setText(psychotherapist.getFullName());
            passwordField.setText(psychotherapist.getPassword());
        }
    }

    @FXML
    private void onBackButtonClick() {

    }

    @FXML void onChangePictureClick() {
        System.out.println("Change picture clicked");
    }
}