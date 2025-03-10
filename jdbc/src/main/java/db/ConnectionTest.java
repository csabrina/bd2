package db;

import java.sql.Connection;

public class ConnectionTest {
    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManager.getInstance();
        Connection connection = connectionManager.getConnection();

        if (connection != null) {
            System.out.println("Conexão bem-sucedida!");
        } else {
            System.out.println("Falha na conexão!");
        }
    }
}
