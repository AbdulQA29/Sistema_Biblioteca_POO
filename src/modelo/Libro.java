package modelo;

public class Libro {
    private int idLibro;
    private String titulo;
    private String autor;
    private String estado;
    
    public Libro(int idLibro, String titulo, String autor, String estado) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }
    
    public Libro(String titulo, String autor, String estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
    }
    
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}