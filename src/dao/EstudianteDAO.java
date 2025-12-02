package dao;

import conexion.ConexionBD;
import modelo.Estudiante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    
    public boolean insertar(Estudiante estudiante) {
        String sql = "INSERT INTO Persona (nombre, apellido, dni, correo, estado, tipo) VALUES (?, ?, ?, ?, ?, 'Estudiante')";
        String sqlEstudiante = "INSERT INTO Estudiante (idPersona, codigoEstudiante) VALUES (?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.conectar();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getApellido());
            pstmt.setString(3, estudiante.getDni());
            pstmt.setString(4, estudiante.getCorreo());
            pstmt.setString(5, estudiante.getEstado());
            
            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                conn.rollback();
                return false;
            }

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idPersona = rs.getInt(1);
                pstmt2 = conn.prepareStatement(sqlEstudiante);
                pstmt2.setInt(1, idPersona);
                pstmt2.setString(2, estudiante.getCodigoEstudiante());
                
                filas = pstmt2.executeUpdate();
                if (filas == 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) {}
            }
            System.out.println("Error al insertar estudiante: " + e.getMessage());
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (pstmt2 != null) pstmt2.close(); } catch (SQLException e) {}
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
        }
    }
    
    public List<Estudiante> listar() {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT p.idPersona, p.nombre, p.apellido, p.dni, p.correo, p.estado, e.codigoEstudiante " +
                    "FROM Persona p INNER JOIN Estudiante e ON p.idPersona = e.idPersona";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Estudiante estudiante = new Estudiante(
                    rs.getInt("idPersona"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("correo"),
                    rs.getString("estado"),
                    rs.getString("codigoEstudiante")
                );
                estudiantes.add(estudiante);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage());
        }
        
        return estudiantes;
    }
}