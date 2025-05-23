package app.repository;

import app.controller.DatabaseConnection;
import app.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository extends CustomRepository{
    private List<Payment> listPayments = new ArrayList<>();
    private List<PaymentOverview> overviews = new ArrayList<>();

    public List<Payment> getAllPayments() throws SQLException {
        listPayments.clear();

        String sql = """
                SELECT * from view_name
            """;


        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Client c = new Client();
                c.setClientId(rs.getInt("clientId"));
                c.setFirstName(rs.getString("firstName"));
                c.setLastName(rs.getString("lastName"));

                Payment p = new Payment();
                p.setPaymentId(rs.getInt("paymentId"));
                p.setClient(c);
                p.setPaymentDate(rs.getDate("paymentDate").toLocalDate());
                p.setAmount(rs.getDouble("amount"));
                p.setCurrencyInitial(rs.getString("currencyInitial"));
                p.setPaymentMethod(rs.getString("paymentMethod"));
                p.setFirstInstallment(rs.getBoolean("isFirstInstallment"));
                p.setPurpose(rs.getString("purpose"));

                listPayments.add(p);
            }
        }

        return listPayments;
    }

    public List<PaymentOverview> getPaymentOverview() throws SQLException {
        String sql = """
                SELECT
                            c.client_id        AS clientId,
                            CONCAT(c.first_name, ' ', c.last_name) AS clientName,
                            SUM(p.amount)      AS totalPaid,
                            SUM(CASE WHEN p.installment_number THEN 1 ELSE 0 END) AS firstCnt,
                            SUM(CASE WHEN NOT p.installment_number THEN 1 ELSE 0 END) AS secondCnt
                        FROM payment p
                        JOIN client c ON p.client_id = c.client_id
                        GROUP BY c.client_id, c.first_name, c.last_name
            """;

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("clientName");
                double total = rs.getDouble("totalPaid");
                int first = rs.getInt("firstCnt");
                int second = rs.getInt("secondCnt");
                boolean hasPendingSecond = (first > 0 && second == 0);

                overviews.add(new PaymentOverview(name, total, hasPendingSecond));
            }
        }
        return overviews;
    }
}
