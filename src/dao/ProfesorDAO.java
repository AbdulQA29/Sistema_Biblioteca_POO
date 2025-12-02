package dao;

import conexion.ConexionBD;
import modelo.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfesorDAO {
    
    public boolean insertar(Profesor profesor) {
        String sql = "INSERT INTO Persona (nombre, apellido, dni, correo, estado, tipo) VALUES (?, ?, ?, ?, ?, 'Profesor')";
        String sqlProfesor = "INSERT INTO Profesor (idPersona, codigoProfesor) VALUES (?, ?)";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        
        try {
            conn = ConexionBD.conectar();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, profesor.getNombre());
            pstmt.setString(2, profesor.getApellido());
            pstmt.setString(3, profesor.getDni());
            pstmt.setString(4, profesor.getCorreo());
            pstmt.setString(5, profesor.getEstado());
            
            int filas = pstmt.executeUpdate();
            if (filas == 0) {
                conn.rollback();
                return false;
            }
            
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idPersona = rs.getInt(1);
                
                pstmt2 = conn.prepareStatement(sqlProfesor);
                pstmt2.setInt(1, idPersona);
                pstmt2.setString(2, profesor.getCodigoProfesor());
                
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
            System.out.println("Error al insertar profesor: " + e.getMessage());
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if (pstmt2 != null) pstmt2.close(); } catch (SQLException e) {}
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException e) {}
        }
    }
    
    public List<Profesor> listar() {
        List<Profesor> profesores = new ArrayList<>();
        String sql = "SELECT p.idPersona, p.nombre, p.apellido, p.dni, p.correo, p.estado, pr.codigoProfesor " +
                    "FROM Persona p INNER JOIN Profesor pr ON p.idPersona = pr.idPersona";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Profesor profesor = new Profesor(
                    rs.getInt("idPersona"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("dni"),
                    rs.getString("correo"),
                    rs.getString("estado"),
                    rs.getString("codigoProfesor")
                );
                profesores.add(profesor);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar profesores: " + e.getMessage());
        }
        
        return profesores;
    }
}