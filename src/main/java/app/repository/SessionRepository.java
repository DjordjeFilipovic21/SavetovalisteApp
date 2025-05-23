package app.repository;

import app.model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository extends CustomRepository {

    public List<Session> getAllSessions(int psyId) {
        List<Session> sessions = new ArrayList<>();
        String query = "SELECT * FROM sessions " +
                "WHERE ses_psy_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, psyId); // Bind the parameter

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    sessions.add(new Session(
                            resultSet.getInt("ses_id"),
                            resultSet.getString("ses_title"),
                            resultSet.getString("ses_desc"),
                            resultSet.getDate("ses_date")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }
}