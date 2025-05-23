package app.repository;

import app.controller.DatabaseConnection;
import app.model.Client;
import app.model.Payment;
import app.model.PaymentOverview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {
    private List<Payment> listPayments = new ArrayList<>();

    public List<Payment> getAllPayments() throws SQLException {
        String sql = """
            SELECT p.payment_id, p.payment_date, p.amount,
                   p.currency_inital, p.payment_method, p.is_first_installment,
                   p.purpose,
                   c.client_id, c.first_name, c.last_name
            FROM payment p
            JOIN client c ON p.client_id = c.client_id
            ORDER BY c.last_name, p.payment_date
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Client c = new Client();
                c.setClientId(rs.getInt("client_id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));

                Payment p = new Payment();
                p.setPaymentId(rs.getInt("payment_id"));
                p.setClient(c);
                p.setPaymentDate(rs.getDate("payment_date").toLocalDate());
                p.setAmount(rs.getDouble("amount"));
                p.setCurrencyInitial(rs.getString("currency_inital"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setFirstInstallment(rs.getBoolean("is_first_installment"));
                p.setPurpose(rs.getString("purpose"));

                listPayments.add(p);
            }
        }
        return listPayments;
    }

    public List<PaymentOverview> getPaymentOverview() throws SQLException {
        String sql = """
            SELECT c.client_id, c.first_name, c.last_name,
                   SUM(p.amount) AS total_paid,
                   SUM(CASE WHEN p.is_first_installment THEN 1 ELSE 0 END) AS first_cnt,
                   SUM(CASE WHEN NOT p.is_first_installment THEN 1 ELSE 0 END) AS second_cnt
            FROM payment p
            JOIN client c ON p.client_id = c.client_id
            GROUP BY c.client_id, c.first_name, c.last_name
            """;

        List<PaymentOverview> overviews = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("first_name") + " " + rs.getString("last_name");
                double total = rs.getDouble("total_paid");
                int first = rs.getInt("first_cnt");
                int second = rs.getInt("second_cnt");
                boolean pendingSecond = (first > 0 && second == 0);

                overviews.add(new PaymentOverview(name, total, pendingSecond));
            }
        }
        return overviews;
    }
}
