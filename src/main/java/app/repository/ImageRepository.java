package app.repository;

import app.model.Session;
import app.repository.CustomRepository;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageRepository extends CustomRepository {

    public void uploadImageToDatabase(String fileName, FileInputStream imageStream) {
        String sql = "INSERT INTO Image (name, image_data) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, fileName);
            statement.setBinaryStream(2, imageStream, imageStream.available());
            statement.executeUpdate();

            System.out.println("Image uploaded successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getImageDataById(int id) {
        String sql = "SELECT image_data FROM Image WHERE id = ?";
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