package app.repository;

import app.model.Session;
import app.repository.CustomRepository;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageRepository extends CustomRepository {

    public int uploadImageToDatabase(String fileName, FileInputStream imageStream) {
        String sql = "INSERT INTO image (name, image_data) VALUES (?, ?)";
        String[] generatedColumns = { "id" };

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, fileName);
            statement.setBinaryStream(2, imageStream, imageStream.available());
            statement.executeUpdate();

            // Retrieve the generated keys
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public byte[] getImageDataById(int id) {
        String sql = "SELECT image_data FROM image WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBytes("image_data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}