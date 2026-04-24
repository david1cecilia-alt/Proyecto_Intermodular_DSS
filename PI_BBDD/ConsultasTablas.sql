-- Listar todos los alumnos
SELECT * FROM Alumnos;

-- Listar profesores con su autoescuela
SELECT p.nombre, p.apellidos, a.nombre AS autoescuela
FROM Profesores p
JOIN Autoescuela a ON p.id_autoescuela = a.id_autoescuela;

-- Listar vehículos con su profesor asignado
SELECT v.matricula, v.modelo, p.nombre
FROM Vehiculos v
LEFT JOIN Profesores p ON v.id_profesor_asignado = p.id_profesor;

-- Alumnos en prácticas
SELECT * FROM Alumnos
WHERE estado = 'Practicas';

-- Clases de más de 45 minutos
SELECT * FROM Clases
WHERE duracion_minutos > 45;

-- Pagos realizados con tarjeta
SELECT * FROM Pagos
WHERE metodo = 'Tarjeta';

-- Clases con alumno, profesor y vehículo
SELECT 
    c.id_clase,
    a.nombre AS alumno,
    p.nombre AS profesor,
    v.matricula,
    c.fecha_hora
FROM Clases c
JOIN Alumnos a ON c.id_alumno = a.id_alumno
JOIN Profesores p ON c.id_profesor = p.id_profesor
LEFT JOIN Vehiculos v ON c.id_vehiculo = v.id_vehiculo;

-- Exámenes con tipo y resultado
SELECT 
    a.nombre AS alumno,
    t.descripcion AS tipo_examen,
    e.resultado
FROM Examen e
JOIN Alumnos a ON e.id_alumno = a.id_alumno
JOIN TipoExamen t ON e.id_tipo_examen = t.id_tipo_examen;

-- Número de alumnos por estado
SELECT estado, COUNT(*) AS total
FROM Alumnos
GROUP BY estado;

-- Total pagado por cada alumno
SELECT a.nombre, SUM(p.importe) AS total_pagado
FROM Pagos p
JOIN Alumnos a ON p.id_alumno = a.id_alumno
GROUP BY a.id_alumno;

-- Número de clases por profesor
SELECT p.nombre, COUNT(c.id_clase) AS total_clases
FROM Profesores p
LEFT JOIN Clases c ON p.id_profesor = c.id_profesor
GROUP BY p.id_profesor;

-- Alumnos que han suspendido algún examen
SELECT DISTINCT a.nombre, a.apellidos
FROM Alumnos a
JOIN Examen e ON a.id_alumno = e.id_alumno
WHERE e.resultado = 'No Apto';

-- Bonos con clases restantes
SELECT a.nombre, b.clases_restantes
FROM Bonos b
JOIN Alumnos a ON b.id_alumno = a.id_alumno
WHERE b.clases_restantes > 0;

-- Clases realizadas por un alumno concreto
SELECT c.fecha_hora, t.nombre_clase
FROM Clases c
JOIN TipoDeClase t ON c.id_tipo_clase = t.id_tipo_clase
WHERE c.id_alumno = 1;

-- Profesores con disponibilidad
SELECT p.nombre, d.fecha_hora_inicio, d.fecha_hora_fin
FROM Disponibilidad d
JOIN Profesores p ON d.id_profesor = p.id_profesor;

-- Alumno que más ha pagado
SELECT a.nombre, SUM(p.importe) AS total
FROM Pagos p
JOIN Alumnos a ON p.id_alumno = a.id_alumno
GROUP BY a.id_alumno
ORDER BY total DESC
LIMIT 1;

-- Profesor con más clases impartidas
SELECT p.nombre, COUNT(c.id_clase) AS total_clases
FROM Profesores p
JOIN Clases c ON p.id_profesor = c.id_profesor
GROUP BY p.id_profesor
ORDER BY total_clases DESC
LIMIT 1;

-- Ingresos totales de la autoescuela
SELECT SUM(importe) AS ingresos_totales
FROM Pagos;