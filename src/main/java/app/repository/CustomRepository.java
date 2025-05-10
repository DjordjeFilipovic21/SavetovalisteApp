package app.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class CustomRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/glavna";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "krompir04";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
}
