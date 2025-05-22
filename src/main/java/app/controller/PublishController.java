package app.controller;

import app.model.Publication;
import app.model.Session;
import app.repository.PublicationRepository;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class PublishController {
    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField publishedToField;

    @FXML
    private TextArea noteField;

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @FXML
    private void handlePublish() {
        Publication pub = new Publication();
        pub.setSession(session);
        pub.setPublishedTo(publishedToField.getText());
        pub.setPublicationDate(datePicker.getValue() != null ? datePicker.getValue() : LocalDate.now());
        pub.setNote(noteField.getText());

        PublicationRepository repo = new PublicationRepository();
        repo.savePublication(pub);

        System.out.println("Session published.");
    }
}
