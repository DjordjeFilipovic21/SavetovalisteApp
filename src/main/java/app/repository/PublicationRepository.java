package app.repository;

import app.model.Publication;

import java.sql.*;

public class PublicationRepository extends CustomRepository{
    public void savePublication(Publication pub) {
        String sql = "INSERT INTO publication (session_id, publication_date, note, published_to) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pub.getSession().getId());
            ps.setDate(2, Date.valueOf(pub.getPublicationDate()));
            ps.setString(3, pub.getNote());
            ps.setString(4, pub.getPublishedTo());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
