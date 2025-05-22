package app.repository;

import app.model.Publication;
import app.model.Session;

import java.sql.*;
import java.util.Optional;

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

    public Optional<Publication> getPublicationForSession(int sessionId) {
        String sql = "SELECT * FROM publication WHERE session_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sessionId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Publication pub = new Publication();
                    pub.setSession(new Session(sessionId));
                    pub.setPublicationDate(rs.getDate("publication_date").toLocalDate());
                    pub.setNote(rs.getString("note"));
                    pub.setPublishedTo(rs.getString("published_to"));
                    return Optional.of(pub);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
