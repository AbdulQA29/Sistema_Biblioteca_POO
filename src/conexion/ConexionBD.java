package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:sqlserver://DESKTOP-31JACNV:1433;"
                                    + "databaseName=BibliotecaDB;"
                                    + "encrypt=true;"
                                    + "trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "nokia5200";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver JDBC cargado correctamente.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: driver JDBC no encontrado.");
        }
    }

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
