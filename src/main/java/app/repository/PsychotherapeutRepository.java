package app.repository;

import app.model.Psychotherapist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
