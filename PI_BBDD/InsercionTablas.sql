-- 1. AUTOESCUELA
INSERT INTO Autoescuela (nombre, cif, direccion, telefono) VALUES
('Autoescuela Rápido', 'A12345678', 'Calle Mayor 1', '600111222');

-- 2. PROFESORES
INSERT INTO Profesores (nombre, apellidos, dni, telefono, id_autoescuela) VALUES
('Carlos', 'García López', '11111111A', '600111111', 1),
('Ana', 'Martínez Ruiz', '22222222B', '600222222', 1);

-- 3. VEHICULOS
INSERT INTO Vehiculos (matricula, modelo, marca, id_profesor_asignado, id_autoescuela) VALUES
('1234ABC', 'Ibiza', 'SEAT', 1, 1),
('5678DEF', 'Clio', 'Renault', 2, 1);

-- 4. ALUMNOS
INSERT INTO Alumnos (nombre, apellidos, dni, fecha_nacimiento, telefono, email, estado, id_profesor_tutor, id_autoescuela) VALUES
('Luis', 'Pérez Gómez', '33333333C', '2000-05-10', '600333333', 'luis@gmail.com', 'Practicas', 1, 1),
('María', 'López Díaz', '44444444D', '1998-09-20', '600444444', 'maria@gmail.com', 'Inscrito', 2, 1);

-- 5. TIPOS DE CLASE
INSERT INTO TipoDeClase (nombre_clase, precio_hora) VALUES
('Práctica ciudad', 45.00),
('Práctica autopista', 45.00),
('Teórica', 5.00);

-- 6. BONOS
INSERT INTO Bonos (id_alumno, clases_totales, clases_restantes, fecha_compra) VALUES
(1, 10, 8, '2026-04-01'),
(2, 5, 5, '2026-04-10');

-- 7. CLASES
INSERT INTO Clases (id_alumno, id_profesor, id_vehiculo, id_tipo_clase, id_bono, fecha_hora, duracion_minutos, num_alum) VALUES
(1, 1, 1, 1, 1, '2026-04-15 10:00:00', 45, 1),
(1, 1, 1, 2, 1, '2026-04-16 12:00:00', 60, 1),
(2, 2, 2, 3, NULL, '2026-04-18 11:00:00', 45, 1),
(2, 2, 2, 1, 2, '2026-04-17 09:00:00', 45, 10);

-- 8. PAGOS
INSERT INTO Pagos (id_alumno, fecha_pago, importe, metodo) VALUES
(1, '2026-04-01 10:00:00', 200.00, 'Tarjeta'),
(2, '2026-04-10 11:00:00', 100.00, 'Efectivo');

-- 9. DISPONIBILIDAD (Rutina semanal: Lunes a Viernes de 8:00 a 14:00)
INSERT INTO Disponibilidad (id_profesor, dia_semana, hora_inicio, hora_fin) VALUES
(1, 1, '08:00:00', '14:00:00'), -- Carlos: Lunes
(1, 2, '08:00:00', '14:00:00'), -- Carlos: Martes
(1, 3, '08:00:00', '14:00:00'), -- Carlos: Miércoles
(2, 2, '09:00:00', '15:00:00'), -- Ana: Martes
(2, 4, '09:00:00', '15:00:00'), -- Ana: Jueves
(2, 5, '09:00:00', '15:00:00'); -- Ana: Viernes


-- 10. TIPOS DE EXAMEN
INSERT INTO TipoExamen (descripcion) VALUES
('Teórico'),
('Práctico');

-- 11. EXAMENES
INSERT INTO Examen (id_alumno, id_tipo_examen, fecha_examen, intento, resultado) VALUES
(1, 1, '2026-03-20', 1, 'Apto'),
(1, 2, '2026-04-20', 1, 'Pendiente'),
(2, 1, '2026-04-18', 1, 'No Apto');

-- 12. USUARIOS
INSERT INTO Usuarios (username, clave, rol) VALUES 
('admin', '1234', 'Gestor'),
('11111111A', '1234', 'Profesor'),
('22222222B', '1234', 'Profesor'),
('33333333C', '1234', 'Alumno'),
('44444444D', '1234', 'Alumno');    