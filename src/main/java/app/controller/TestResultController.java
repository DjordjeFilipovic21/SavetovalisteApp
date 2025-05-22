package app.controller;

import app.model.Publication;
import app.model.Session;
import app.model.TestResult;
import app.repository.PublicationRepository;
import app.repository.TestResultRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;
import java.util.Optional;

public class TestResultController {
    @FXML
    private ListView<TestResult> resultsContainer;

    @FXML
    private Button publishButton;

    @FXML
    private VBox contentArea;

    @FXML
    private Label headerLabel;

    @FXML
    private TextField publishedToField;

    @FXML
    private TextField noteField;

    @FXML
    private TextField dateField;

    @FXML
    private TestResultRepository repo = new TestResultRepository();

    @FXML
    private PublicationRepository pubRepo = new PublicationRepository();

    @FXML
    private static Session currentSession;

    @FXML
    private void showSessionDetails() {
        if (currentSession == null) {
            headerLabel.setText("No session selected");
            publishButton.setVisible(false);
            return;
        }

        headerLabel.setText("Session details: " + currentSession.getId());
        List<TestResult> results = repo.getResultsForSession(currentSession.getId());
        resultsContainer.getItems().addAll(results);

        resultsContainer.setCellFactory(lv -> new ListCell<TestResult>() {
            @Override
            protected void updateItem(TestResult testResult, boolean empty) {
                super.updateItem(testResult, empty);
                setText(empty || testResult == null
                        ? null
                        : "Test: " + testResult.getTest().getName() +
                        "   |                    Result: " + testResult.getResultValue());
            }
        });

        Optional<Publication> pubOpt = pubRepo.getPublicationForSession(currentSession.getId());

        if (pubOpt.isPresent()) {
            Publication pub = pubOpt.get();
            publishedToField.setText(pub.getPublishedTo());
            noteField.setText(pub.getNote());
            dateField.setText(pub.getPublicationDate().toString());
            publishButton.setVisible(false);
        } else {
            publishedToField.clear();
            noteField.clear();
            dateField.clear();
            publishButton.setVisible(true);
        }
    }

    @FXML
    private void initialize() {
        showSessionDetails();
        TopBarController.getInstance().setContentArea(contentArea);
    }

    @FXML
    public void goToPublishForm(ActionEvent actionEvent) {
        AppRootController.getInstance().navigateToPage("/app/fxml/publish_form.fxml");
    }

    public static void setCurrentSession(Session session) {
        currentSession = session;
    }
}
