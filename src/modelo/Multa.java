package modelo;

public class Multa {
    private int idMulta;
    private int idPersona;
    private double monto;
    private String motivo;
    private String estado;
    private String nombrePersona;
    
    public Multa(int idMulta, int idPersona, double monto, String motivo, String estado) {
        this.idMulta = idMulta;
        this.idPersona = idPersona;
        this.monto = monto;
        this.motivo = motivo;
        this.estado = estado;
    }
    
    public Multa(int idPersona, double monto, String motivo, String estado) {
        this.idPersona = idPersona;
        this.monto = monto;
        this.motivo = motivo;
        this.estado = estado;
    }
    
    public int getIdMulta() { return idMulta; }
    public void setIdMulta(int idMulta) { this.idMulta = idMulta; }
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getNombrePersona() { return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { this.nombrePersona = nombrePersona; }
}