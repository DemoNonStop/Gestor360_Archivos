package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conecctionSQL {
	private static final String URL = "jdbc:mysql://localhost:3306/gestor360_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error en la conexión a la base de datos");
        }
        return conn;
    }
}
