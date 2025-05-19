package app.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class CustomRepository {
    private static final String URL = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7779626";
    private static final String DB_USER = "sql7779626";
    private static final String DB_PASSWORD = "HKfRkezhfV";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
    }
}
