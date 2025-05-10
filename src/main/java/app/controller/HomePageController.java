package app.controller;

import app.model.Session;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class HomePageController {
    @FXML
    private VBox contentArea;

    @FXML
    private ListView<String> sessionListView;


    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
        loadSessions();
    }

    private void loadSessions() {
        app.SessionRepository sessionDAO = new app.SessionRepository();
        for (Session session : sessionDAO.getAllSessions()) {
            sessionListView.getItems().add(session.getTitle() + ": " + session.getDescription());
        }
    }


}
