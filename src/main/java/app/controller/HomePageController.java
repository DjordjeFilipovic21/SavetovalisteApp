package app.controller;

import app.model.Session;
import app.model.TestResult;
import app.repository.SessionRepository;
import app.repository.TestResultRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HomePageController {
    @FXML
    private VBox contentArea;

    @FXML
    private ListView<Session> sessionListView;

    @FXML
    private void showSessionDetails(int sessionId) {
        TestResultRepository repo = new TestResultRepository();
        List<TestResult> results = repo.getResultsForSession(sessionId);

        VBox detailsBox = new VBox(10);
        detailsBox.setPadding(new Insets(10,10,10,10));

        // Naslov
        Label header = new Label("Session details: " + sessionId);
        header.getStyleClass().add("header-label");
        detailsBox.getChildren().add(header);

        // Tabela ili lista rezultata
        for (TestResult r : results) {
            Label row = new Label(
                    "Test: " + r.getTest().getName()
                            + "   | Resault: " + r.getResultValue()
            );
            detailsBox.getChildren().add(row);
        }

        // Dugme za objavljivanje
        Button publishButton = new Button("Publish session");
        publishButton.getStyleClass().add("publish-button");

        // Preuzmi sesiju iz bilo kog rezultata (svi imaju istu sesiju)
        Session session = results.isEmpty() ? null : results.get(0).getSession();

        if (session != null) {
            publishButton.setOnAction(e -> openPublishForm(session));
            detailsBox.getChildren().add(publishButton);
        }

        contentArea.getChildren().setAll(detailsBox);

        contentArea.getChildren().setAll(detailsBox);
    }

    @FXML
    private void openPublishForm(Session session) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/fxml/publish_form.fxml"));
            VBox form = loader.load();

            PublishController controller = loader.getController();
            controller.setSession(session);

            Stage stage = new Stage();
            stage.setTitle("Publish session");
            stage.setScene(new Scene(form));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
        sessionListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Session item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null
                        : item.getTitle() + ": " + item.getDescription());
            }
        });

        loadSessions();

        sessionListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        showSessionDetails(newSel.getId());
                    }
                });
    }

    private void loadSessions() {
        SessionRepository sessionDAO = new SessionRepository();
        List<Session> sessions = sessionDAO.getAllSessions();
        sessionListView.getItems().setAll(sessions);
    }


}
