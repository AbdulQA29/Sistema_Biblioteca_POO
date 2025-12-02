package dao;

import conexion.ConexionBD;
import modelo.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    
    public boolean insertar(Prestamo prestamo) {
        String sqlInsert = "INSERT INTO Prestamo (idPersona, idLibro, fechaPrestamo, fechaDevolucion, estado) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateLibro = "UPDATE Libro SET estado = 'Prestado' WHERE idLibro = ? AND estado = 'Disponible'";
        String sqlVerificarLibro = "SELECT idLibro FROM Libro WHERE idLibro = ? AND estado = 'Disponible'";
        
        Connection conn = null;
        
        try {
            conn = ConexionBD.conectar();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmtVerificar = conn.prepareStatement(sqlVerificarLibro)) {
                pstmtVerificar.setInt(1, prestamo.getIdLibro());
                try (ResultSet rs = pstmtVerificar.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: El libro no existe o no está disponible.");
                        conn.rollback();
                        return false;
                    }
                }
            }
            
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateLibro)) {
                pstmtUpdate.setInt(1, prestamo.getIdLibro());
                int filasActualizadas = pstmtUpdate.executeUpdate();
                if (filasActualizadas == 0) {
                    System.out.println("Error: No se pudo actualizar el estado del libro.");
                    conn.rollback();
                    return false;
                }
            }
            
            try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                pstmtInsert.setInt(1, prestamo.getIdPersona());
                pstmtInsert.setInt(2, prestamo.getIdLibro());
                pstmtInsert.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
                pstmtInsert.setDate(4, new java.sql.Date(prestamo.getFechaDevolucion().getTime()));
                pstmtInsert.setString(5, prestamo.getEstado());
                
                int filasInsertadas = pstmtInsert.executeUpdate();
                if (filasInsertadas == 0) {
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
            System.out.println("Error al insertar préstamo: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
            }
        }
    }
    
    public List<Prestamo> listar() {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT p.idPrestamo, p.idPersona, p.idLibro, p.fechaPrestamo, " +
                    "p.fechaDevolucion, p.estado, " +
                    "per.nombre + ' ' + per.apellido as nombrePersona, " +
                    "l.titulo as tituloLibro " +
                    "FROM Prestamo p " +
                    "INNER JOIN Persona per ON p.idPersona = per.idPersona " +
                    "INNER JOIN Libro l ON p.idLibro = l.idLibro " +
                    "ORDER BY p.fechaPrestamo DESC";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Prestamo prestamo = new Prestamo(
                    rs.getInt("idPrestamo"),
                    rs.getInt("idPersona"),
                    rs.getInt("idLibro"),
                    rs.getDate("fechaPrestamo"),
                    rs.getDate("fechaDevolucion"),
                    rs.getString("estado")
                );
                prestamo.setNombrePersona(rs.getString("nombrePersona"));
                prestamo.setTituloLibro(rs.getString("tituloLibro"));
                prestamos.add(prestamo);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }
        
        return prestamos;
    }
}