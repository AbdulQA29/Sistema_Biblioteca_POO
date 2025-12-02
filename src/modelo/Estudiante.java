package modelo;

public class Estudiante extends Persona {

    private String codigoEstudiante;

    public Estudiante(int idPersona, String nombre, String apellido, String dni,
                        String correo, String estado, String codigoEstudiante) {
        super(idPersona, nombre, apellido, dni, correo, estado);
        this.codigoEstudiante = codigoEstudiante;
    }

    public Estudiante(String nombre, String apellido, String dni,
                        String correo, String estado, String codigoEstudiante) {
        super(nombre, apellido, dni, correo, estado);
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }
    
    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }
}