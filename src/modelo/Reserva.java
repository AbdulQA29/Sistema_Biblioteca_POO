package modelo;

import java.util.Date;

public class Reserva {
    private int idReserva;
    private int idPersona;
    private int idLibro;
    private Date fechaReserva;
    private String estado;
    private String nombrePersona;
    private String tituloLibro;
    
    public Reserva(int idReserva, int idPersona, int idLibro, Date fechaReserva, String estado) {
        this.idReserva = idReserva;
        this.idPersona = idPersona;
        this.idLibro = idLibro;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }
    
    public Reserva(int idPersona, int idLibro, Date fechaReserva, String estado) {
        this.idPersona = idPersona;
        this.idLibro = idLibro;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
    }
    
    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public Date getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombrePersona() { return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { this.nombrePersona = nombrePersona; }
    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }
}