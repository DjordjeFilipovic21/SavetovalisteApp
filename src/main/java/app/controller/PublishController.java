package app.controller;

import app.model.Publication;
import app.model.Session;
import app.repository.PublicationRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class PublishController {
    @FXML
    private VBox contentArea;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField publishedToField;

    @FXML
    private TextArea noteField;

    @FXML
    private static Session currentSession;

    @FXML
    public static void setSession(Session session) {
        currentSession = session;
    }

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    private void handlePublish() {
        Publication pub = new Publication();
        pub.setSession(currentSession);
        pub.setPublishedTo(publishedToField.getText());
        pub.setPublicationDate(datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now());
        pub.setNote(noteField.getText());

        PublicationRepository repo = new PublicationRepository();
        repo.savePublication(pub);

        System.out.println("Session published.");
        AppRootController.getInstance().navigateToPage("/app/fxml/testResault.fxml");
    }
}
