package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    // Singleton Design Pattern
    private static DB instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:mysql://localhost:3306/salesmanagement";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "123!!";

    private DB(){
        // XAMPP
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DB();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }

}
