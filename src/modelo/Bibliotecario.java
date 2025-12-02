package modelo;

public class Bibliotecario extends Persona {
    
    private String codigoBibliotecario;
    private String turno;

    public Bibliotecario(int idPersona, String nombre, String apellido, String dni,
                        String correo, String estado, String codigoBibliotecario, String turno) {
        super(idPersona, nombre, apellido, dni, correo, estado);
        this.codigoBibliotecario = codigoBibliotecario;
        this.turno = turno;
    }

    public String getCodigoBibliotecario() {
        return codigoBibliotecario;
    }
    
    public void setCodigoBibliotecario(String codigoBibliotecario) {
        this.codigoBibliotecario = codigoBibliotecario;
    }
    
    public String getTurno() {
        return turno;
    }
    
    public void setTurno(String turno) {
        this.turno = turno;
    }
}