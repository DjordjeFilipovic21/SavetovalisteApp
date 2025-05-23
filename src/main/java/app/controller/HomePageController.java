package app.controller;

import app.model.Session;
import app.repository.PsychotherapeutRepository;
import app.repository.SessionRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class HomePageController {
    @FXML
    private VBox contentArea;


    @FXML
    private ListView<Session> pastSessionsListView;

    @FXML
    private ListView<Session> upcomingSessionsListView;

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);

        // Set cell factories for both ListViews
        pastSessionsListView.setCellFactory(lv -> new ListCell<>() {
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

        upcomingSessionsListView.setCellFactory(lv -> new ListCell<>() {
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

        // Add selection listener to pastSessionsListView
        pastSessionsListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        TestResultController.setCurrentSession(newSel);
                        PublishController.setSession(newSel);
                        AppRootController.getInstance().navigateToPage("/app/fxml/testResault.fxml");
                    }
                });

        loadSessions();
    }

    @FXML
    private void loadSessions() {
        PsychotherapeutRepository psychotherapeutRepo = new PsychotherapeutRepository();
        List<Integer> psyIds = psychotherapeutRepo.getAllPsychotherapistIdsBySupervisorId(TopBarController.getInstance().getUserId());
        SessionRepository sessionDAO = new SessionRepository();
        List<Session> sessions = new ArrayList<>();
        if (!psyIds.isEmpty()) {
            for (Integer psyId : psyIds) {
                sessions.addAll(sessionDAO.getAllSessions(psyId));
            }
        }
        sessions.addAll(sessionDAO.getAllSessions(TopBarController.getInstance().getUserId()));

        List<Session> pastSessions = new ArrayList<>();
        List<Session> upcomingSessions = new ArrayList<>();
        for (Session session : sessions) {
            if (session.getDate().before(java.util.Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                pastSessions.add(session);
            } else {
                upcomingSessions.add(session);
            }
        }

        if (pastSessions.isEmpty()) {
            pastSessionsListView.setPlaceholder(new javafx.scene.control.Label("No past sessions"));
        } else {
            pastSessionsListView.setPlaceholder(null);
            pastSessionsListView.getItems().setAll(pastSessions);
        }

        if (upcomingSessions.isEmpty()) {
            upcomingSessionsListView.setPlaceholder(new javafx.scene.control.Label("No upcoming sessions"));
        } else {
            upcomingSessionsListView.setPlaceholder(null);
            upcomingSessionsListView.getItems().setAll(upcomingSessions);
        }


    }

    @FXML
    private void goToPaymentOverview(){
        AppRootController.getInstance().navigateToPage("/app/fxml/paymentsOverview.fxml");
    }



}
