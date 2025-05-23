package app.controller;

import app.model.Psychotherapist;
import app.repository.ImageRepository;
import app.repository.PsychotherapeutRepository;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    public Button removePicture;

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
                removePicture.setDisable(true);
            }
            else{
                profileImage.setImage(new Image(new ByteArrayInputStream(imageRepository.getImageDataById(imgId))));
                removePicture.setDisable(false);
            }

            usernameField.setText(psychotherapist.getUsername());
            fullNameField.setText(psychotherapist.getFullName());
            passwordField.setText(psychotherapist.getPassword());
        }
    }

    @FXML
    private void onSave() {
        String username = usernameField.getText();
        String fullName = fullNameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            TopBarController.getInstance().showErrorAlert("Empty error","Sva polja moraju biti popunjena.");
            return;
        }

        Integer userId = TopBarController.getInstance().getUserId();
        if (userId != null) {
            Psychotherapist psychotherapist = new Psychotherapist();
            psychotherapist.setPsyId(userId);
            psychotherapist.setUsername(username);
            psychotherapist.setFullName(fullName);
            psychotherapist.setPassword(password);
            psychotherapeutRepository.updatePsychotherapist(psychotherapist);
            TopBarController.getInstance().onUndoButtonClick();
        } else {
            TopBarController.getInstance().showErrorAlert("Save error","Greska pri cuvanju.");
        }
    }

    @FXML void onRemovePicture() {
        TopBarController.getInstance().onRemoveImage();
        loadProfileData(TopBarController.getInstance().getUserId());
    }

    @FXML
    public void onProfileImageClick() {
        TopBarController.getInstance().onUploadImage();
        loadProfileData(TopBarController.getInstance().getUserId());
    }
}