package dao;

import conexion.ConexionBD;
import modelo.Multa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultaDAO {
    
    public boolean insertar(Multa multa) {
        String sql = "INSERT INTO Multa (idPersona, monto, motivo, estado) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, multa.getIdPersona());
            pstmt.setDouble(2, multa.getMonto());
            pstmt.setString(3, multa.getMotivo());
            pstmt.setString(4, multa.getEstado());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar multa: " + e.getMessage());
            return false;
        }
    }
    
    public List<Multa> listar() {
        List<Multa> multas = new ArrayList<>();
        String sql = "SELECT m.idMulta, m.idPersona, m.monto, m.motivo, m.estado, " +
                    "per.nombre + ' ' + per.apellido as nombrePersona " +
                    "FROM Multa m " +
                    "INNER JOIN Persona per ON m.idPersona = per.idPersona " +
                    "ORDER BY m.idMulta DESC";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Multa multa = new Multa(
                    rs.getInt("idMulta"),
                    rs.getInt("idPersona"),
                    rs.getDouble("monto"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                );
                multa.setNombrePersona(rs.getString("nombrePersona"));
                multas.add(multa);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar multas: " + e.getMessage());
        }
        
        return multas;
    }
}