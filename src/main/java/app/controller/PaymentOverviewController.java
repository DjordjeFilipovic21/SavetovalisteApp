package app.controller;

import app.model.Payment;
import app.model.PaymentOverview;
import app.repository.PaymentRepository;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentOverviewController {
    @FXML
    private TableView<PaymentOverview> overviewTable;

    @FXML
    private TableColumn<PaymentOverview, String> clientCol;

    @FXML
    private TableColumn<PaymentOverview, Double> totalCol;

    @FXML
    private TableColumn<PaymentOverview, String> pendingCol;

    @FXML
    private TableView<Payment> detailTable;

    @FXML
    private TableColumn<Payment, String> dtClientCol;

    @FXML
    private TableColumn<Payment, LocalDate> dtDateCol;

    @FXML
    private TableColumn<Payment, Double> dtAmountCol;

    @FXML
    private TableColumn<Payment, String> dtCurrencyCol;

    @FXML
    private TableColumn<Payment, String> dtMethodCol;

    @FXML
    private TableColumn<Payment, Boolean> dtInstCol;

    @FXML
    private TableColumn<Payment, String> dtPurposeCol;

    @FXML
    private VBox contentArea;

    @FXML
    private PaymentRepository repo = new PaymentRepository();

    private void showClientPayment(){
        clientCol.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("totalPaid"));
        pendingCol.setCellValueFactory(cell -> {
            boolean pending = cell.getValue().isHasPendingSecondInstallment();
            return new ReadOnlyStringWrapper(pending ? "Yes" : "No");
        });

        try {
            List<PaymentOverview> overview = repo.getPaymentOverview();
            overviewTable.getItems().setAll(overview);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAllPayments(){
        dtClientCol.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getClient().getFullName()));
        dtDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        dtAmountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dtCurrencyCol.setCellValueFactory(new PropertyValueFactory<>("currencyInitial"));
        dtMethodCol.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        dtInstCol.setCellValueFactory(new PropertyValueFactory<>("firstInstallment"));
        dtPurposeCol.setCellValueFactory(new PropertyValueFactory<>("purpose"));

        try {
            List<Payment> details = repo.getAllPayments();
            detailTable.getItems().setAll(details);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        showClientPayment();
        showAllPayments();
        TopBarController.getInstance().setContentArea(contentArea);
    }
}
