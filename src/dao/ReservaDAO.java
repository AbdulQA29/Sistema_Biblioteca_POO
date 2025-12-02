package dao;

import conexion.ConexionBD;
import modelo.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    
    public boolean insertar(Reserva reserva) {
        String sqlInsert = "INSERT INTO Reserva (idPersona, idLibro, fechaReserva, estado) VALUES (?, ?, ?, ?)";
        String sqlUpdateLibro = "UPDATE Libro SET estado = 'Reservado' WHERE idLibro = ? AND estado = 'Disponible'";
        String sqlVerificarLibro = "SELECT idLibro FROM Libro WHERE idLibro = ? AND estado = 'Disponible'";
        
        Connection conn = null;
        
        try {
            conn = ConexionBD.conectar();
            if (conn == null) return false;
            
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmtVerificar = conn.prepareStatement(sqlVerificarLibro)) {
                pstmtVerificar.setInt(1, reserva.getIdLibro());
                try (ResultSet rs = pstmtVerificar.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Error: El libro no existe o no está disponible.");
                        conn.rollback();
                        return false;
                    }
                }
            }
            
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateLibro)) {
                pstmtUpdate.setInt(1, reserva.getIdLibro());
                int filasActualizadas = pstmtUpdate.executeUpdate();
                if (filasActualizadas == 0) {
                    System.out.println("Error: No se pudo actualizar el estado del libro.");
                    conn.rollback();
                    return false;
                }
            }
            
            try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                pstmtInsert.setInt(1, reserva.getIdPersona());
                pstmtInsert.setInt(2, reserva.getIdLibro());
                pstmtInsert.setDate(3, new java.sql.Date(reserva.getFechaReserva().getTime()));
                pstmtInsert.setString(4, reserva.getEstado());
                
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
            System.out.println("Error al insertar reserva: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
            }
        }
    }
    
    public List<Reserva> listar() {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.idReserva, r.idPersona, r.idLibro, r.fechaReserva, " +
                    "r.estado, " +
                    "per.nombre + ' ' + per.apellido as nombrePersona, " +
                    "l.titulo as tituloLibro " +
                    "FROM Reserva r " +
                    "INNER JOIN Persona per ON r.idPersona = per.idPersona " +
                    "INNER JOIN Libro l ON r.idLibro = l.idLibro " +
                    "ORDER BY r.fechaReserva DESC";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Reserva reserva = new Reserva(
                    rs.getInt("idReserva"),
                    rs.getInt("idPersona"),
                    rs.getInt("idLibro"),
                    rs.getDate("fechaReserva"),
                    rs.getString("estado")
                );
                reserva.setNombrePersona(rs.getString("nombrePersona"));
                reserva.setTituloLibro(rs.getString("tituloLibro"));
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar reservas: " + e.getMessage());
        }
        
        return reservas;
    }
}