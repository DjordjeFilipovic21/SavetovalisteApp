package app.repository;

import app.model.Psychotherapist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PsychotherapeutRepository extends CustomRepository {

    public int validateCredentials(String username, String password) {
        String query = "SELECT * FROM psychotherapeuts WHERE psy_username = ? AND psy_password = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("psy_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int getPsychotherapistImageById(int psyId) {
        String query = "SELECT psy_img_id FROM psychotherapeuts WHERE psy_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, psyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("psy_img_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public Psychotherapist getPsychotherapistById(int psyId) {
        String query = "SELECT psy_username, psy_full_name, psy_password FROM psychotherapeuts WHERE psy_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, psyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Psychotherapist psychotherapist = new Psychotherapist();
                psychotherapist.setUsername(resultSet.getString("psy_username"));
                psychotherapist.setFullName(resultSet.getString("psy_full_name"));
                psychotherapist.setPassword(resultSet.getString("psy_password"));
                return psychotherapist;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateImgId(int userId, Integer imgId) {
        String sql = "UPDATE psychotherapeuts SET psy_img_id = ? WHERE psy_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (imgId == null) {
                statement.setNull(1, java.sql.Types.INTEGER);
            } else {
                statement.setInt(1, imgId);
            }
            statement.setInt(2, userId);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertPsychotherapeut(String username, String email, String password, String fullname) {
        String insertQuery = "INSERT INTO psychotherapeuts (psy_username, psy_email, psy_password, psy_full_name) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Start transaction

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, fullname);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        connection.commit(); // Commit transaction
                        return generatedId;
                    }
                }
                connection.rollback(); // Rollback transaction
            } catch (SQLException e) {
                connection.rollback(); // Rollback transaction on error
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    public List<Integer> getAllPsychotherapistIdsBySupervisorId(int supervisorId) {
        List<Integer> psychotherapistIds = new ArrayList<>();
        String query = "SELECT psy_id FROM psychotherapeuts WHERE psy_psy_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, supervisorId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                psychotherapistIds.add(resultSet.getInt("psy_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return psychotherapistIds;
    }

    public List<Psychotherapist> getAllPsychotherapists() {
        List<Psychotherapist> psychotherapists = new ArrayList<>();
        String query = "SELECT * FROM psychotherapeuts";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Psychotherapist psychotherapist = new Psychotherapist();
                psychotherapist.setUsername(resultSet.getString("psy_username"));
                psychotherapist.setFullName(resultSet.getString("psy_full_name"));
                psychotherapist.setImageId(resultSet.getInt("psy_img_id"));
                psychotherapists.add(psychotherapist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return psychotherapists;
    }

    public void updatePsychotherapist(Psychotherapist psychotherapist) {
        String query = "UPDATE psychotherapeuts SET psy_username = ?, psy_full_name = ?, psy_password = ? WHERE psy_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, psychotherapist.getUsername());
            statement.setString(2, psychotherapist.getFullName());
            statement.setString(3, psychotherapist.getPassword());
            statement.setInt(4, psychotherapist.getPsyId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
