package app;

import app.model.Session;
import app.repository.CustomRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionRepository extends CustomRepository {

    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<>();
        String query = "SELECT * FROM sessions";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                sessions.add(new Session(
                        resultSet.getInt("ses_id"),
                        resultSet.getString("ses_title"),
                        resultSet.getString("ses_desc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessions;
    }
}