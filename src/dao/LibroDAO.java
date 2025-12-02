package dao;

import conexion.ConexionBD;
import modelo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    
    public boolean insertar(Libro libro) {
        String sql = "INSERT INTO Libro (titulo, autor, estado) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getEstado());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }
    
    public List<Libro> listar() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libro ORDER BY titulo";
        
        try (Connection conn = ConexionBD.conectar();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("idLibro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("estado")
                );
                libros.add(libro);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
        
        return libros;
    }
}