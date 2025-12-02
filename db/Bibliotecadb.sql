Create database BibliotecaDB;
USE BibliotecaDB;

CREATE TABLE Persona (
    idPersona INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    correo VARCHAR(100),
    estado VARCHAR(20) DEFAULT 'Activo',
    tipo VARCHAR(20)
);


CREATE TABLE Estudiante (
    idEstudiante INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT UNIQUE NOT NULL,
    codigoEstudiante VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona)
);


CREATE TABLE Profesor (
    idProfesor INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT UNIQUE NOT NULL,
    codigoProfesor VARCHAR(20) UNIQUE NOT NULL,
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona)
);


CREATE TABLE Bibliotecario (
    idBibliotecario INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT UNIQUE NOT NULL,
    codigoBibliotecario VARCHAR(20) UNIQUE NOT NULL,
    turno VARCHAR(20),
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona)
);

CREATE TABLE Libro (
    idLibro INT IDENTITY(1,1) PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    estado VARCHAR(20) DEFAULT 'Disponible'
);

CREATE TABLE Prestamo (
    idPrestamo INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT NOT NULL,
    idLibro INT NOT NULL,
    fechaPrestamo DATE NOT NULL,
    fechaDevolucion DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activo',
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona),
    FOREIGN KEY (idLibro) REFERENCES Libro(idLibro)
);


CREATE TABLE Reserva (
    idReserva INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT NOT NULL,
    idLibro INT NOT NULL,
    fechaReserva DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'Activa',
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona),
    FOREIGN KEY (idLibro) REFERENCES Libro(idLibro)
);


CREATE TABLE Multa (
    idMulta INT IDENTITY(1,1) PRIMARY KEY,
    idPersona INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    motivo VARCHAR(200),
    estado VARCHAR(20) DEFAULT 'Activa',
    fechaRegistro DATE DEFAULT GETDATE(),
    FOREIGN KEY (idPersona) REFERENCES Persona(idPersona)
);