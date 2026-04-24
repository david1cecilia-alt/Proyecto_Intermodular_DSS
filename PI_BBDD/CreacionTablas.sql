-- 0. BASE DE DATOS
CREATE DATABASE BDAUTOESCUELA;
USE BDAUTOESCUELA;

-- 1. AUTOESCUELA
CREATE TABLE Autoescuela (
    id_autoescuela INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    cif VARCHAR(20) UNIQUE NOT NULL,
    direccion TEXT,
    telefono VARCHAR(20)
);

-- 2. PROFESORES
CREATE TABLE Profesores (
    id_profesor INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    id_autoescuela INT NOT NULL,
    FOREIGN KEY (id_autoescuela) 
        REFERENCES Autoescuela(id_autoescuela)
        ON DELETE CASCADE
);

-- 3. VEHICULOS
CREATE TABLE Vehiculos (
    id_vehiculo INT PRIMARY KEY AUTO_INCREMENT,
    matricula VARCHAR(15) UNIQUE NOT NULL,
    modelo VARCHAR(50),
    marca VARCHAR(50),
    id_profesor_asignado INT,
    id_autoescuela INT NOT NULL,
    FOREIGN KEY (id_profesor_asignado) 
        REFERENCES Profesores(id_profesor)
        ON DELETE SET NULL,
    FOREIGN KEY (id_autoescuela) 
        REFERENCES Autoescuela(id_autoescuela)
        ON DELETE CASCADE
);

-- 4. ALUMNOS
CREATE TABLE Alumnos (
    id_alumno INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    dni VARCHAR(15) UNIQUE NOT NULL,
    fecha_nacimiento DATE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    estado ENUM(
        'Inscrito',
        'Teorico aprobado',
        'Practicas',
        'Aprobado',
        'Baja'
    ) DEFAULT 'Inscrito',
    id_profesor_tutor INT,
    id_autoescuela INT NOT NULL,
    FOREIGN KEY (id_profesor_tutor) 
        REFERENCES Profesores(id_profesor)
        ON DELETE SET NULL,
    FOREIGN KEY (id_autoescuela) 
        REFERENCES Autoescuela(id_autoescuela)
        ON DELETE CASCADE
);

-- 5. TIPOS DE CLASE
CREATE TABLE TipoDeClase (
    id_tipo_clase INT PRIMARY KEY AUTO_INCREMENT,
    nombre_clase VARCHAR(50) NOT NULL,
    precio_hora DECIMAL(10,2) NOT NULL CHECK (precio_hora > 0)
);

-- 6. BONOS
CREATE TABLE Bonos (
    id_bono INT PRIMARY KEY AUTO_INCREMENT,
    id_alumno INT NOT NULL,
    clases_totales INT NOT NULL CHECK (clases_totales > 0),
    clases_restantes INT NOT NULL CHECK (clases_restantes >= 0),
    fecha_compra DATE,
    FOREIGN KEY (id_alumno) 
        REFERENCES Alumnos(id_alumno)
        ON DELETE CASCADE
);

-- 7. CLASES
CREATE TABLE Clases (
    id_clase INT PRIMARY KEY AUTO_INCREMENT,
    id_alumno INT NOT NULL,
    id_profesor INT NOT NULL,
    id_vehiculo INT,
    id_tipo_clase INT NOT NULL,
    id_bono INT NULL,
    fecha_hora DATETIME NOT NULL,
    duracion_minutos INT DEFAULT 45 CHECK (duracion_minutos > 0),
    num_alum INT CHECK (num_alum > 0),
    FOREIGN KEY (id_alumno) 
        REFERENCES Alumnos(id_alumno)
        ON DELETE CASCADE,
    FOREIGN KEY (id_profesor) 
        REFERENCES Profesores(id_profesor)
        ON DELETE CASCADE,
    FOREIGN KEY (id_vehiculo) 
        REFERENCES Vehiculos(id_vehiculo)
        ON DELETE SET NULL,
    FOREIGN KEY (id_tipo_clase) 
        REFERENCES TipoDeClase(id_tipo_clase),
    FOREIGN KEY (id_bono) 
        REFERENCES Bonos(id_bono)
        ON DELETE SET NULL
);

-- 8. PAGOS
CREATE TABLE Pagos (
    id_pago INT PRIMARY KEY AUTO_INCREMENT,
    id_alumno INT NOT NULL,
    fecha_pago DATETIME NOT NULL,
    importe DECIMAL(10,2) NOT NULL CHECK (importe > 0),
    metodo ENUM('Efectivo', 'Tarjeta', 'Transferencia') NOT NULL,
    FOREIGN KEY (id_alumno) 
        REFERENCES Alumnos(id_alumno)
        ON DELETE CASCADE
);

-- 9. DISPONIBILIDAD
CREATE TABLE Disponibilidad (
    id_disponibilidad INT PRIMARY KEY AUTO_INCREMENT,
    id_profesor INT NOT NULL,
    dia_semana INT NOT NULL CHECK (dia_semana BETWEEN 1 AND 7),
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    FOREIGN KEY (id_profesor) REFERENCES Profesores(id_profesor) ON DELETE CASCADE,
    UNIQUE(id_profesor, dia_semana) 
);

-- 10. TIPOS DE EXAMEN
CREATE TABLE TipoExamen (
    id_tipo_examen INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(50) NOT NULL
);

-- 11. EXAMENES
CREATE TABLE Examen (
    id_examen INT PRIMARY KEY AUTO_INCREMENT,
    id_alumno INT NOT NULL,
    id_tipo_examen INT NOT NULL,
    fecha_examen DATE NOT NULL,
    intento INT DEFAULT 1 CHECK (intento > 0),
    resultado ENUM('Apto', 'No Apto', 'Pendiente') DEFAULT 'Pendiente',
    FOREIGN KEY (id_alumno) 
        REFERENCES Alumnos(id_alumno)
        ON DELETE CASCADE,
    FOREIGN KEY (id_tipo_examen) 
        REFERENCES TipoExamen(id_tipo_examen)
);

-- 12. USUARIOS
CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL, 
    clave VARCHAR(100) NOT NULL,
    rol ENUM('Gestor', 'Profesor', 'Alumno') NOT NULL
);