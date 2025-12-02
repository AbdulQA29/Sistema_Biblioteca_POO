package modelo;

public class Profesor extends Persona {
    private String codigoProfesor;
    
    public Profesor(int idPersona, String nombre, String apellido, String dni, 
        String correo, String estado, String codigoProfesor) {
        super(idPersona, nombre, apellido, dni, correo, estado);
        this.codigoProfesor = codigoProfesor;
    }
    
    public Profesor(String nombre, String apellido, String dni,
        String correo, String estado, String codigoProfesor) {
        super(nombre, apellido, dni, correo, estado);
        this.codigoProfesor = codigoProfesor;
    }
    
    public String getCodigoProfesor() { return codigoProfesor; }
    public void setCodigoProfesor(String codigoProfesor) { this.codigoProfesor = codigoProfesor; }
}