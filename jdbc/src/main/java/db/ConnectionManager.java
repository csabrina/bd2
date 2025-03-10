package db;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager instance;
    private Connection connection;

    private ConnectionManager() {
        connect();
    }

    private void connect() {
        try {
            Dotenv dotenv = Dotenv.load();
            String DB_URL = "jdbc:postgresql://" + dotenv.get("PGHOST") + "/" + dotenv.get("PGDATABASE");
            String DB_USER = dotenv.get("PGUSER");
            String DB_PASSWORD = dotenv.get("PGPASSWORD");

            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar conexão: " + e.getMessage());
        }
        return connection;
    }
}