import dao.*;
import modelo.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    public static void main(String[] args) {
        int opcion;
        
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    registrarEstudiante();
                    break;
                case 2:
                    registrarProfesor();
                    break;
                case 3:
                    registrarLibro();
                    break;
                case 4:
                    registrarPrestamo();
                    break;
                case 5:
                    registrarReserva();
                    break;
                case 6:
                    registrarMulta();
                    break;
                case 7:
                    listarEstudiantes();
                    break;
                case 8:
                    listarProfesores();
                    break;
                case 9:
                    listarLibros();
                    break;
                case 10:
                    listarPrestamos();
                    break;
                case 11:
                    listarReservas();
                    break;
                case 12:
                    listarMultas();
                    break;
                case 13:
                    System.out.println("¡Gracias por usar el sistema de biblioteca!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            
            if (opcion != 13) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcion != 13);
        
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("           SISTEMA BIBLIOTECA");
        System.out.println("========================================");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Registrar Profesor");
        System.out.println("3. Registrar Libro");
        System.out.println("4. Registrar Prestamo");
        System.out.println("5. Registrar Reserva");
        System.out.println("6. Registrar Multa");
        System.out.println("---------------------------------------------------");
        System.out.println("7. Listar Estudiantes");
        System.out.println("8. Listar Profesores");
        System.out.println("9. Listar Libros");
        System.out.println("10. Listar Prestamos");
        System.out.println("11. Listar Reservas");
        System.out.println("12. Listar Multas");
        System.out.println("13. Salir");
        System.out.println("========================================");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private static void registrarEstudiante() {
        System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Código de Estudiante: ");
        String codigoEstudiante = scanner.nextLine();
        
        Estudiante estudiante = new Estudiante(nombre, apellido, dni, correo, "Activo", codigoEstudiante);
        EstudianteDAO dao = new EstudianteDAO();
        
        if (dao.insertar(estudiante)) {
            System.out.println("Estudiante registrado exitosamente.");
        } else {
            System.out.println("Error al registrar estudiante.");
        }
    }

    private static void registrarProfesor() {
        System.out.println("\n--- REGISTRAR PROFESOR ---");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Código de Profesor: ");
        String codigoProfesor = scanner.nextLine();
        
        Profesor profesor = new Profesor(nombre, apellido, dni, correo, "Activo", codigoProfesor);
        ProfesorDAO dao = new ProfesorDAO();
        
        if (dao.insertar(profesor)) {
            System.out.println("Profesor registrado exitosamente.");
        } else {
            System.out.println("Error al registrar profesor.");
        }
    }

    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");
        
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        
        System.out.print("Autor: ");
        String autor = scanner.nextLine();
        
        Libro libro = new Libro(titulo, autor, "Disponible");
        LibroDAO dao = new LibroDAO();
        
        if (dao.insertar(libro)) {
            System.out.println("Libro registrado exitosamente.");
        } else {
            System.out.println("Error al registrar libro.");
        }
    }

    private static void registrarPrestamo() {
        System.out.println("\n--- REGISTRAR PRÉSTAMO ---");
        
        int idPersona = leerEntero("ID de la persona: ");
        
        if (!PersonaDAO.existePersona(idPersona)) {
            System.out.println("Error: La persona con ID " + idPersona + " no existe.");
            return;
        }
        
        int idLibro = leerEntero("ID del libro: ");
        
        System.out.print("Fecha de préstamo (dd/MM/yyyy) [hoy]: ");
        String fechaPrestamoStr = scanner.nextLine();
        Date fechaPrestamo;
        
        try {
            if (fechaPrestamoStr.isEmpty()) {
                fechaPrestamo = new Date();
            } else {
                fechaPrestamo = sdf.parse(fechaPrestamoStr);
            }
        } catch (Exception e) {
            System.out.println("Fecha inválida. Usando fecha de hoy.");
            fechaPrestamo = new Date();
        }
        
        System.out.print("Fecha de devolución (dd/MM/yyyy): ");
        String fechaDevolucionStr = scanner.nextLine();
        Date fechaDevolucion;
        
        try {
            fechaDevolucion = sdf.parse(fechaDevolucionStr);
        } catch (Exception e) {
            System.out.println("Fecha inválida. No se pudo registrar el préstamo.");
            return;
        }
        
        Prestamo prestamo = new Prestamo(idPersona, idLibro, fechaPrestamo, fechaDevolucion, "Activo");
        PrestamoDAO dao = new PrestamoDAO();
        
        if (dao.insertar(prestamo)) {
            System.out.println("Préstamo registrado exitosamente.");
        } else {
            System.out.println("Error al registrar préstamo. Verifique que el libro exista y esté disponible.");
        }
    }

    private static void registrarReserva() {
        System.out.println("\n--- REGISTRAR RESERVA ---");
        
        int idPersona = leerEntero("ID de la persona: ");
        
        if (!PersonaDAO.existePersona(idPersona)) {
            System.out.println("Error: La persona con ID " + idPersona + " no existe.");
            return;
        }
        
        int idLibro = leerEntero("ID del libro: ");
        
        System.out.print("Fecha de reserva (dd/MM/yyyy) [hoy]: ");
        String fechaReservaStr = scanner.nextLine();
        Date fechaReserva;
        
        try {
            if (fechaReservaStr.isEmpty()) {
                fechaReserva = new Date();
            } else {
                fechaReserva = sdf.parse(fechaReservaStr);
            }
        } catch (Exception e) {
            System.out.println("Fecha inválida. Usando fecha de hoy.");
            fechaReserva = new Date();
        }
        
        Reserva reserva = new Reserva(idPersona, idLibro, fechaReserva, "Activa");
        ReservaDAO dao = new ReservaDAO();
        
        if (dao.insertar(reserva)) {
            System.out.println("Reserva registrada exitosamente.");
        } else {
            System.out.println("Error al registrar reserva. Verifique que el libro exista y esté disponible.");
        }
    }

    private static void registrarMulta() {
        System.out.println("\n--- REGISTRAR MULTA ---");
        
        int idPersona = leerEntero("ID de la persona: ");
        
        if (!PersonaDAO.existePersona(idPersona)) {
            System.out.println("Error: La persona con ID " + idPersona + " no existe.");
            return;
        }
        
        double monto = leerDouble("Monto de la multa: ");
        
        System.out.print("Motivo de la multa: ");
        String motivo = scanner.nextLine();
        
        Multa multa = new Multa(idPersona, monto, motivo, "Activa");
        MultaDAO dao = new MultaDAO();
        
        if (dao.insertar(multa)) {
            System.out.println("Multa registrada exitosamente.");
        } else {
            System.out.println("Error al registrar multa.");
        }
    }


    private static void listarEstudiantes() {
        System.out.println("\n--- LISTA DE ESTUDIANTES ---");
        EstudianteDAO dao = new EstudianteDAO();
        List<Estudiante> estudiantes = dao.listar();
        
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-10s %-20s %-15s %-10s%n",
                "ID", "Nombre", "Apellido", "DNI", "Correo", "Código", "Estado");
            System.out.println("--------------------------------------------------------------------------------");
            
            for (Estudiante e : estudiantes) {
                System.out.printf("%-5d %-15s %-15s %-10s %-20s %-15s %-10s%n",
                    e.getIdPersona(), e.getNombre(), e.getApellido(),
                    e.getDni(), e.getCorreo(), e.getCodigoEstudiante(), e.getEstado());
            }
        }
    }

    private static void listarProfesores() {
        System.out.println("\n--- LISTA DE PROFESORES ---");
        ProfesorDAO dao = new ProfesorDAO();
        List<Profesor> profesores = dao.listar();
        
        if (profesores.isEmpty()) {
            System.out.println("No hay profesores registrados.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-10s %-20s %-15s %-10s%n",
                "ID", "Nombre", "Apellido", "DNI", "Correo", "Código", "Estado");
            System.out.println("--------------------------------------------------------------------------------");
            
            for (Profesor p : profesores) {
                System.out.printf("%-5d %-15s %-15s %-10s %-20s %-15s %-10s%n",
                    p.getIdPersona(), p.getNombre(), p.getApellido(),
                    p.getDni(), p.getCorreo(), p.getCodigoProfesor(), p.getEstado());
            }
        }
    }

    private static void listarLibros() {
        System.out.println("\n--- LISTA DE LIBROS ---");
        LibroDAO dao = new LibroDAO();
        List<Libro> libros = dao.listar();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.printf("%-5s %-30s %-20s %-12s%n",
                "ID", "Título", "Autor", "Estado");
            System.out.println("----------------------------------------------------------------");
            
            for (Libro l : libros) {
                System.out.printf("%-5d %-30s %-20s %-12s%n",
                    l.getIdLibro(), l.getTitulo(), l.getAutor(), l.getEstado());
            }
        }
    }

    private static void listarPrestamos() {
        System.out.println("\n--- LISTA DE PRÉSTAMOS ---");
        PrestamoDAO dao = new PrestamoDAO();
        List<Prestamo> prestamos = dao.listar();
        
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            System.out.printf("%-5s %-25s %-30s %-15s %-15s %-10s%n",
                "ID", "Persona", "Libro", "Fecha Préstamo", "Fecha Devol.", "Estado");
            System.out.println("----------------------------------------------------------------------------------------------");
            
            for (Prestamo p : prestamos) {
                System.out.printf("%-5d %-25s %-30s %-15s %-15s %-10s%n",
                    p.getIdPrestamo(),
                    p.getNombrePersona(),
                    p.getTituloLibro(),
                    sdf.format(p.getFechaPrestamo()),
                    sdf.format(p.getFechaDevolucion()),
                    p.getEstado());
            }
        }
    }

    private static void listarReservas() {
        System.out.println("\n--- LISTA DE RESERVAS ---");
        ReservaDAO dao = new ReservaDAO();
        List<Reserva> reservas = dao.listar();
        
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.printf("%-5s %-25s %-30s %-15s %-10s%n",
                "ID", "Persona", "Libro", "Fecha Reserva", "Estado");
            System.out.println("---------------------------------------------------------------------------");
            
            for (Reserva r : reservas) {
                System.out.printf("%-5d %-25s %-30s %-15s %-10s%n",
                    r.getIdReserva(),
                    r.getNombrePersona(),
                    r.getTituloLibro(),
                    sdf.format(r.getFechaReserva()),
                    r.getEstado());
            }
        }
    }

    private static void listarMultas() {
        System.out.println("\n--- LISTA DE MULTAS ---");
        MultaDAO dao = new MultaDAO();
        List<Multa> multas = dao.listar();
        
        if (multas.isEmpty()) {
            System.out.println("No hay multas registradas.");
        } else {
            System.out.printf("%-5s %-25s %-10s %-30s %-10s%n",
                "ID", "Persona", "Monto", "Motivo", "Estado");
            System.out.println("-----------------------------------------------------------------------");
            
            for (Multa m : multas) {
                System.out.printf("%-5d %-25s S/.%-8.2f %-30s %-10s%n",
                    m.getIdMulta(),
                    m.getNombrePersona(),
                    m.getMonto(),
                    m.getMotivo(),
                    m.getEstado());
            }
        }
    }
}