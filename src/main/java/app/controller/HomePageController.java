package app.controller;

import app.model.Session;
import app.model.Test;
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
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
        sessionListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Session item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getTitle() + ": " + item.getDescription());
                }
            }
        });

        loadSessions();

        sessionListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        TestResultController.setCurrentSession(newSel);
                        PublishController.setSession(newSel);
                        AppRootController.getInstance().navigateToPage("/app/fxml/testResault.fxml");
                    }
                });
    }

    @FXML
    private void loadSessions() {
        SessionRepository sessionDAO = new SessionRepository();
        List<Session> sessions = sessionDAO.getAllSessions();
        sessionListView.getItems().setAll(sessions);
    }


}
