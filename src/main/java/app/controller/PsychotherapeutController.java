package app.controller;

import app.model.Psychotherapist;
import app.model.Session;
import app.repository.ImageRepository;
import app.repository.PsychotherapeutRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;

public class PsychotherapeutController {
    @FXML
    public ListView<Psychotherapist> psychotherapistListView;

    private final PsychotherapeutRepository psychotherapeutRepository = new PsychotherapeutRepository();

    private ImageRepository imageRepository = new ImageRepository();

    @FXML
    private VBox contentArea;

    @FXML
    private void initialize() {
        TopBarController.getInstance().setContentArea(contentArea);
        loadPsychos();

        psychotherapistListView.setCellFactory(listView -> new ListCell<>() {
            private final HBox content = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final Label nameLabel = new Label();

            {
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                content.getChildren().addAll(imageView, nameLabel);
            }

            @Override
            protected void updateItem(Psychotherapist item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    if (item.getImageId() != 0) {
                        imageView.setImage(new Image(new ByteArrayInputStream(imageRepository.getImageDataById(item.getImageId()))));
                    } else {
                        Image plusImage = new Image(Objects.requireNonNull(getClass().getResource("/app/images/profile.png")).toExternalForm());
                        imageView.setImage(plusImage);

                    }
                    nameLabel.setText(item.getFullName());
                    setGraphic(content);
                }
            }
        });
    }

    private void loadPsychos() {
        List<Psychotherapist> psychotherapists = psychotherapeutRepository.getAllPsychotherapists();
        psychotherapistListView.getItems().clear();
        psychotherapistListView.getItems().addAll(psychotherapists);
    }


}