package dao;

import conexion.ConexionBD;
import java.sql.*;

public class PersonaDAO {
    
    public static boolean existePersona(int idPersona) {
        String sql = "SELECT COUNT(*) FROM Persona WHERE idPersona = ?";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPersona);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error al verificar persona: " + e.getMessage());
        }
        
        return false;
    }
}