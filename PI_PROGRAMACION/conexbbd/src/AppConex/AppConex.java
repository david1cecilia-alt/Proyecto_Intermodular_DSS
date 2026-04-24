package AppConex;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import dao.*;
import vo.*;
public class AppConex {
    public static void main(String[] args) {
        Boolean salir=false;
        int vs=0;
        while (salir != true ) {
        Scanner sc = new Scanner(System.in);
        try (Connection conex = Conexion.getConexion()) {
        System.out.println("========================================");
        System.out.println("         SISTEMA AUTOESCUELA            ");
        System.out.println("========================================");
        
        System.out.print("DNI Usuario: ");
        String user = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

            // Validar credenciales
        UsuarioVO usuario = new UsuarioDAO().validarLogin(conex, user, pass);

        if (usuario != null) {
            if (usuario.getRol().equals("Gestor")) {
                System.out.println("[OK] Acceso correcto.");
            // SALUDO PERSONALIZADO
            System.out.println("----------------------------------------");
            System.out.println("¡Bienvenido administrador!"); 
            System.out.println("Perfil activo: " + usuario.getRol());
            System.out.println("----------------------------------------");
                
            ejecutarMenu(usuario, sc, conex);
            System.out.println("Introduce 0 si quiere logearse, 1 si quiere salir ");
            if (vs==0 ||vs==1) {
                if (vs==1) {
                    salir=true;
                }
            }
            else{
                System.out.println("[Error]: El caracter introducido debe ser 0 o 1");
            }
            }else{
            System.out.println("[OK] Acceso correcto.");
                // SALUDO PERSONALIZADO
            System.out.println("----------------------------------------");
            System.out.println("¡Bienvenido/a, " + usuario.getNombreReal() + "!"); 
            System.out.println("Perfil activo: " + usuario.getRol());
            System.out.println("----------------------------------------");
                
            ejecutarMenu(usuario, sc, conex);
            System.out.println("Introduce 0 si quiere logearse, 1 si quiere salir ");
            vs=sc.nextInt();
            if (vs==0 ||vs==1) {
                if (vs==1) {
                    salir=true;
                }
            }
            else{
                System.out.println("[Error]: El caracter introducido debe ser 0 o 1");
            }
            }
        } else {
            System.out.println("[ERROR] DNI o contraseña incorrectos.");
        }
    } catch (Exception e) {
        System.err.println("[ERROR] Fallo de conexión: " + e.getMessage());
    }
        }
        
    
    }
        

    private static void ejecutarMenu(UsuarioVO usuario, Scanner sc, Connection conex) throws Exception {
        int opcion = -1;
        String rol = usuario.getRol();

        while (opcion != 0) {
            System.out.println("--- MENÚ PRINCIPAL (" + rol.toUpperCase() + ") ---");
            
            if (rol.equals("Gestor")) {
                System.out.println("1. Listado global de Alumnos");//
                System.out.println("2. Ver todos los Pagos");//
                System.out.println("3. Estado de Vehiculos");//
                System.out.println("4. Crear Alumno");//
                System.out.println("5. Eliminar Alumno");//
                System.out.println("6. Crear Profesor");//
                System.out.println("7. Eliminar Profesor");//
                System.out.println("8. Pagos realizados con tarjeta");//
                System.out.println("9. Clases con alumno, profesor y vehículo");//
                System.out.println("10. Número de clases por profesor");//
                System.out.println("11. Profesores con disponibilidad");//
                System.out.println("12. Profesor con más clases impartidas");//
                System.out.println("13. Ingresos totales de la autoescuela");//
                System.out.println("0. Cerrar Sesión");//
            } else if (rol.equals("Profesor")) {
                System.out.println("1. Ver mis Clases Programadas");//
                System.out.println("2. Listar mis Alumnos (Tutorizados)");//
                System.out.println("3. Consultar mi Disponibilidad General");//
                System.out.println("4. Listar vehículos con su profesor asignado");//
                System.out.println("5. Alumnos en prácticas");//
                System.out.println("6. Exámenes con tipo y resultado");//
                System.out.println("7. Número de alumnos por estado");//
                System.out.println("8. Total pagado por cada alumno");//
                System.out.println("9. Alumnos que han suspendido algún examen");//
                System.out.println("10. Alumno que más ha pagado");
                System.out.println("11. Crear disponibilidad");//
                System.out.println("12. Consultar disponibilidad real");//
                System.out.println("13. Eliminar Disponibilidad");//
                System.out.println("0. Cerrar Sesión");//
            } else if (rol.equals("Alumno")) {
                System.out.println("1. Ver el estado de mis Bonos");//
                System.out.println("2. Consultar mis Exámenes");//
                System.out.println("3. Información de mi Autoescuela");//
                System.out.println("4. Clases de más de 45 minutos");//
                System.out.println("5. Consultar mis pagos");//
                System.out.println("6. Cambiar contraseña");//
                System.out.println("7. Consultar mis clases");//
                System.out.println("8. Añadir bono");//
                System.out.println("9. Comprar clases");//
                System.out.println("10. Pedir examen");//
                System.out.println("0. Cerrar Sesión");//
            }

            System.out.print("Opción: ");
            // Evitar error si se mete una letra
            while (!sc.hasNextInt()) {
                System.out.print("Por favor, introduce un número: ");
                sc.next();
            }
            opcion = sc.nextInt();
            System.out.println("----------------------------------------");

            procesarAccion(usuario, opcion, conex, sc);
        }
    }

    private static void procesarAccion(UsuarioVO usuario, int opcion, Connection conex, Scanner sc) throws Exception {
        if (opcion == 0) {
            System.out.println("Cerrando sesión... ¡Hasta pronto!");
            return;
        }

        String rol = usuario.getRol();
        String dni = usuario.getUsername(); // DNI del usuario logueado para los filtros

        switch (rol) {
            case "Gestor":
                if (opcion == 1) {
                    System.out.println(">>> LISTADO GLOBAL DE ALUMNOS:");
                    new AlumnoDAO().obtenerAlumnos(conex).forEach(System.out::println);
                } else if (opcion == 2) {
                    System.out.println(">>> HISTORIAL DE PAGOS:");
                    new PagoDAO().obtenerPagos(conex).forEach(System.out::println);
                } else if (opcion == 3) {
                    System.out.println(">>> ESTADO DE VEHÍCULOS:");
                    new VehiculoDAO().obtenerVehiculos(conex).forEach(System.out::println);
                } else if (opcion == 4) {
                    System.out.println(">>> CREAR NUEVO ALUMNO:");
                    System.out.print("Nombre: "); String nom = sc.next();
                    System.out.print("Apellidos: "); String ape = sc.next();
                    System.out.print("DNI: "); String dniA = sc.next();
                    System.out.print("Fecha Nacimiento (YYYY-MM-DD): "); String fechaStr = sc.next();
                    System.out.print("ID Profesor Tutor: "); int idProf = sc.nextInt();
                    System.out.println("Telefono: "); String telAlum = sc.next();
                    System.out.println("Email: "); String emailAlum = sc.next();
                    System.out.print("ID Autoescuela: "); int idAuto = sc.nextInt();

                    AlumnoVO nuevo = new AlumnoVO(0, nom, ape, dniA, "Inscrito", telAlum, emailAlum, java.sql.Date.valueOf(fechaStr), idProf, idAuto);

                    int filas = new AlumnoDAO().darAltaAlumno(conex, nuevo); 
                    if (filas > 0) System.out.println("[OK] Alumno creado con éxito.");

                } else if (opcion == 5) {
                    System.out.println(">>> ELIMINAR ALUMNO:");
                    System.out.print("Introduce el DNI del alumno a borrar: ");
                    String dniBorrar = sc.next();

                    int filas = new AlumnoDAO().eliminarAlumno(conex, dniBorrar); 
                    if (filas > 0) {
                        System.out.println("[OK] Alumno eliminado.");
                    } else {
                        System.out.println("[!] No se encontró ningún alumno con ese DNI.");
                    }
                }else if (opcion == 6) { 
                    System.out.println(">>> CREAR PROFESOR:");
                     
                    System.out.print("Nombre: "); String nP = sc.next();
                    System.out.print("Apellidos: "); String aP = sc.next();
                    System.out.print("DNI: "); String dP = sc.next();
                    System.out.print("ID Autoescuela: "); int idA = sc.nextInt();
                    System.out.println("Telefono: "); String telProf = sc.next();
                    
                    ProfesorVO nuevo = new ProfesorVO(0,nP, aP, dP, telProf, idA);

                    int filas = new ProfesorDAO().crearProfesor(conex, nuevo); 
                    if (filas > 0) System.out.println("[OK] Profesor creado con éxito.");
                    
                }else if (opcion == 7) {
                    System.out.println(">>> ELIMINAR PROFESOR:");
                    System.out.print("Introduce el DNI del profesor a borrar: ");
                    String dniBorrar = sc.next();

                    int filas = new ProfesorDAO().eliminarProfesor(conex, dniBorrar); 
                    if (filas > 0) {
                        System.out.println("[OK] Profesor eliminado.");
                    } else {
                        System.out.println("[!] No se encontró ningún profesor con ese DNI.");
                    }
                } else if (opcion == 8) {
                    System.out.println(">>> PAGOS REALIZADOS CON TARJETA:");
                    new PagoDAO().obtenerPagosTarjeta(conex).forEach(System.out::println);
                    
                } else if (opcion == 9) {
                    System.out.println(">>> DETALLE DE CLASES (Nombres y Marcas):");
                    new ClaseDAO().obtenerClaseDetallada(conex).forEach(System.out::println);
                }else if (opcion == 10) {
                    System.out.println(">>> NUMERO DE CLASES DEL PROFESOR:");
                    new ProfesorDAO().clasesPorProfesor(conex).forEach(System.out::println);
                } else if (opcion == 11) {
                    System.out.println(">>> PROFESORES CON DISPONIBILIDAD:");
                    new ProfesorDAO().profesoresDisponibles(conex).forEach(System.out::println);
                } else if (opcion == 12) {
                    System.out.println(">>> PROFESOR CON MÁS CLASES IMPARTIDAS:");
                    // Declaramos la variable 'p' con el resultado del método
                    ProfesorVO p = new ProfesorDAO().obtenerProfesorMasClases(conex);
                    
                    if (p != null) {
                        System.out.println(p);
                    } else {
                        System.out.println("No hay datos de profesores o clases.");
                    }
                }else if (opcion == 13) {
                    System.out.println(">>> INGRESOS TOTALES DE LA AUTOESCUELA:");
                    // Declaramos la variable 'total' (o el nombre que quieras)
                    double total = new PagoDAO().obtenerIngresosTotales(conex);
                    
                    System.out.println("El total recaudado es: " + total + " €");
                }break;
            
            case "Profesor":
                if (opcion == 1) {
                 System.out.println(">>> TUS PRÓXIMAS CLASES:");
        
                // Ahora llamamos al nuevo método que devuelve Strings ya montados
                    new ClaseDAO().obtenerProximasClasesNombres(conex, dni).forEach(System.out::println);
                }else if (opcion == 2) {
                    System.out.println(">>> TUS ALUMNOS ASIGNADOS:");
                    new ClaseDAO().obtenerMisAlumnosTutorizados(conex, dni).forEach(System.out::println);
                } else if (opcion == 3) {
                    System.out.println(">>> CONSULTAR DISPONIBILIDAD:");
                    System.out.print("Introduce fecha (YYYY-MM-DD): ");
                    String fechaStr = sc.next();
                    
                    try {
                        java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaStr);
                        System.out.print("¿Consultar huecos para clases de 45 o 60 min?: ");
                        int dur = sc.nextInt();
                        new DisponibilidadDAO().consultarDisponibilidadFiltrada(conex, fechaSQL, dur).forEach(System.out::println);

                    } catch (Exception e) {
                        System.out.println("[!] Formato de fecha incorrecto.");
                    }
                }else if (opcion == 4) {
                    System.out.println(">>> LISTADO DE VEHÍCULOS POR PROFESOR:");
                    new VehiculoDAO().listarVehiculosConProfesor(conex).forEach(System.out::println);
                }else if (opcion == 5) {
                    System.out.println(">>> LISTADO DE ALUMNOS EN PRÁCTICAS:");
                    new AlumnoDAO().obtenerAlumnosEnPracticas(conex).forEach(System.out::println);
                }else if (opcion == 6) {
                    System.out.println(">>> LISTADO DE EXÁMENES:");
                    new ExamenDAO().obtenerExamenesDetallados(conex).forEach(System.out::println);
                } else if (opcion == 7) {
                    System.out.println(">>> ALUMNOS POR ESTADO:");
                    new AlumnoDAO().alumnosPorEstado(conex).forEach(System.out::println);
                } else if (opcion == 8) {
                    System.out.println(">>> RECAUDACIÓN POR ALUMNO:");
                    new PagoDAO().totalPagadoPorAlumno(conex).forEach(System.out::println);
                } else if (opcion == 9) {
                    System.out.println(">>> ALUMNOS CON ALGÚN SUSPENSO:");
                    new ExamenDAO().obtenerAlumnosSuspensos(conex).forEach(System.out::println);
                } else if (opcion == 10) {
                    System.out.println(">>> RECORD DE PAGO:");
                    new PagoDAO().alumnoMasPagador(conex);
                }else if (opcion == 11) {
                System.out.println(">>> MI CONFIGURACIÓN DE HORARIO (RUTINA)");
                // Obtenemos el ID del profesor automáticamente mediante su DNI (username)
                int miId = new ProfesorDAO().obtenerIdPorDni(conex, usuario.getUsername());

                System.out.println("Días: 1-Lun, 2-Mar, 3-Mie, 4-Jue, 5-Vie, 6-Sab, 7-Dom");
                System.out.print("Introduce el número del día: ");
                int dia = sc.nextInt();

                // Comprobamos si ya tiene ese día configurado
                if (new DisponibilidadDAO().existeDisponibilidad(conex, miId, dia)) {
                    System.out.println("[ERROR] Ya tienes un horario asignado para ese día. Elimínalo antes de crear uno nuevo.");
                } else {
                    System.out.print("Hora inicio (HH:mm:ss): ");
                    String inicioStr = sc.next();
                    System.out.print("Hora fin (HH:mm:ss): ");
                    String finStr = sc.next();

                    try {
                        DisponibilidadVO nuevaDisp = new DisponibilidadVO(
                            miId, 
                            dia, 
                            java.sql.Time.valueOf(inicioStr), 
                            java.sql.Time.valueOf(finStr)
                        );
                        new DisponibilidadDAO().registrarRutina(conex, nuevaDisp);
                        System.out.println("[OK] Tu horario ha sido registrado correctamente.");
                    } catch (Exception e) {
                        System.out.println("[!] Error en el formato de hora. Usa HH:mm:ss (ej: 08:00:00)");
                    }
                }
            }
            else if (opcion == 12) {
                System.out.println(">>> CONSULTA DE DISPONIBILIDAD REAL");
                System.out.print("Introduce la fecha para consultar (YYYY-MM-DD): ");
                String fechaStr = sc.next(); 
                
                try {
                    java.sql.Date fechaSQL = java.sql.Date.valueOf(fechaStr);
                    System.out.println("--------------------------------------------------");
                    System.out.println("Estado de profesores para el día: " + fechaStr);
                    System.out.println("--------------------------------------------------");
                    
                    List<String> disponibilidad = new DisponibilidadDAO().consultarDisponibilidadReal(conex, fechaSQL);
                    
                    if (disponibilidad.isEmpty()) {
                        System.out.println("[!] No hay turnos de trabajo registrados para ese día de la semana.");
                    } else {
                        disponibilidad.forEach(System.out::println);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("[ERROR] Formato de fecha incorrecto. Usa: YYYY-MM-DD");
                }
            }
            else if (opcion == 13) {
                System.out.println(">>> ELIMINAR MI HORARIO");
                int miId = new ProfesorDAO().obtenerIdPorDni(conex, usuario.getUsername());
                
                System.out.print("¿Qué día de la semana quieres borrar? (1-7): ");
                int dia = sc.nextInt();

                System.out.print("¿Confirmas la eliminación de tu turno para el día " + dia + "? (s/n): ");
                String confirma = sc.next();

                if (confirma.equalsIgnoreCase("s")) {
                    int filas = new DisponibilidadDAO().eliminarDisponibilidad(conex, miId, dia);
                    if (filas > 0) {
                        System.out.println("[OK] Horario eliminado correctamente.");
                    } else {
                        System.out.println("[!] No tienes ningún horario configurado para ese día.");
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            }
            
            break;
            case "Alumno":
                if (opcion == 1) {
                    System.out.println(">>> TUS BONOS ACTIVOS:");
                    new AlumnoDAO().obtenerMisBonos(conex, dni).forEach(System.out::println);
                } else if (opcion == 2) {
                    System.out.println(">>> TUS NOTAS DE EXAMEN:");
                    // Se asume que ExamenDAO ya tiene el método filtrado por DNI
                    new ExamenDAO().obtenerExamenes(conex, dni).forEach(System.out::println);
                } else if (opcion == 3) {
                    System.out.println(">>> DATOS DE CONTACTO AUTOESCUELA:");
                    new AutoescuelaDAO().obtenerAutoescuelas(conex).forEach(System.out::println);
                }else if (opcion == 4) {
                    System.out.println(">>> CLASE DE MÁS DE 45 MINUTOS:");
                    new ClaseDAO().obtenerClasesLargas(conex).forEach(System.out::println);
                }else if (opcion == 5) {
                    System.out.println(">>> CONSULTAR MIS PAGOS:");
                    new ClaseDAO().consultarMisClases(conex, dni);
                }else if (opcion == 6) {                       
                    System.out.println(">>> CAMBIAR CONTRASEÑA:");
                    System.out.println("Introduce tu nueva contraseña :");
                    String nuevaclave= sc.next();
                    String user=usuario.getUsername();
                    new UsuarioDAO().cambiarContrasena(conex, user, nuevaclave);
                } else if (opcion == 7) {
                    System.out.println(">>> CONSULTAR MIS CLASES:");
                    new ClaseDAO().consultarMisClases(conex, dni).forEach(System.out::println);
                } else if (opcion == 8) {
                    System.out.println(">>> ADQUIRIR BONO CON DESCUENTO POR VOLUMEN");
                    int idA = new AlumnoDAO().obtenerIdPorDni(conex, dni);

                    System.out.print("¿Cuántas clases quieres incluir en el bono?: ");
                    int cant = sc.nextInt();
                    double precioUnitario;

                    // Cálculo del precio por rangos
                    if (cant >= 20) precioUnitario = 38.0;
                    else if (cant >= 10) precioUnitario = 40.0;
                    else if (cant >= 5) precioUnitario = 42.0;
                    else precioUnitario = 45.0;

                    double totalPagar = cant * precioUnitario;

                    System.out.printf("Precio por clase: %.2f€ | TOTAL A PAGAR: %.2f€\n", precioUnitario, totalPagar);
                    System.out.print("¿Confirmar compra y pago con tarjeta? (s/n): ");

                    if (sc.next().equalsIgnoreCase("s")) {
                        // 1. REGISTRAMOS EL BONO
                        BonoVO nuevoB = new BonoVO(0, idA, cant, cant, new java.sql.Date(System.currentTimeMillis()));
                        int filasBono = new BonoDAO().añadirBono(conex, nuevoB);

                        if (filasBono > 0) {
                            // Usamos el método comprarClases de PagoDAO que ya inserta en la tabla 'Pagos'
                            new PagoDAO().comprarClases(conex, idA, totalPagar, "Bono " + cant + " clases");
                            
                            System.out.println("--------------------------------------------------");
                            System.out.println("[OK] Bono activado correctamente.");
                            System.out.println("[OK] Pago de " + totalPagar + "€ registrado.");
                            System.out.println("--------------------------------------------------");
                        }
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                }
                else if (opcion == 9) {
                    System.out.println(">>> COMPRAR Y RESERVAR CLASES");
                    try {
                        // 1. Obtener IDs necesarios
                        int idA = new AlumnoDAO().obtenerIdPorDni(conex, dni);
                        int idProf = new AlumnoDAO().obtenerIdTutor(conex, idA);

                        if (idProf == 0) {
                            System.out.println("[!] Error: No tienes un tutor asignado. Contacta con el gestor.");
                        } else {
                            // 2. Pedir datos de la reserva
                            System.out.print("¿Duración de la clase? (45 o 60 min): ");
                            int duracion_minutos = sc.nextInt();
                            double precio = (duracion_minutos == 45) ? 30.0 : 40.0;

                            System.out.print("¿Para qué fecha? (YYYY-MM-DD): ");
                            String fechaStr = sc.next();
                            Date fecha = Date.valueOf(fechaStr);

                            // 3. VISUALIZACIÓN: Mostramos huecos con el método "Filtrada" (el que dice los minutos)
                            System.out.println("\n--- Disponibilidad de tu tutor para el día " + fechaStr + " ---");
                            List<String> info = new DisponibilidadDAO().consultarDisponibilidadFiltrada(conex, fecha, duracion_minutos);
                            if (info.isEmpty()) {
                                System.out.println("El profesor no trabaja este día.");
                            } else {
                                info.forEach(System.out::println);

                                // 4. ELECCIÓN DE HORA
                                System.out.print("\n¿A qué hora quieres empezar? (HH:mm): ");
                                String horaStr = sc.next();
                                if (horaStr.length() == 5) horaStr += ":00"; // Asegurar formato HH:mm:ss
                                Time horaElegida = Time.valueOf(horaStr);

                                // 5. VALIDACIÓN LÓGICA: Comprobamos si el hueco es real con "esHuecoValido"
                                boolean esPosible = new DisponibilidadDAO().esHuecoValido(conex, idProf, fecha, horaElegida, duracion_minutos);

                                if (esPosible) {
                                    System.out.println("Hueco libre confirmado. Precio: " + precio + "€");
                                    System.out.print("¿Confirmar pago y reserva? (s/n): ");
                                    
                                    if (sc.next().equalsIgnoreCase("s")) {
                                        // A) Registrar el pago
                                        new PagoDAO().comprarClases(conex, idA, precio, "Reserva clase " + duracion_minutos + "min");

                                        // B) Insertar la clase (Convertimos fecha y hora a Timestamp)
                                        Timestamp fechaHoraClase = Timestamp.valueOf(fechaStr + " " + horaStr);
                                        new ClaseDAO().insertarClase(conex, idA, idProf, fechaHoraClase, duracion_minutos);

                                        System.out.println("[OK] Proceso completado: Pago registrado y clase reservada.");
                                    }
                                } else {
                                    System.out.println("[!] ERROR: Esa hora ya está ocupada o está fuera del horario del profesor.");
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("[!] Error: Formato de fecha u hora incorrecto.");
                    }
                }


                else if (opcion == 10) {
                    int idA = new AlumnoDAO().obtenerIdPorDni(conex, dni);
                    
                    // 1. Mostrar disponibilidad
                    new ExamenDAO().verRutinaLibreTutor(conex, idA).forEach(System.out::println);

                    // 2. Pedir datos para el alta
                    System.out.print("Introduce la fecha elegida (YYYY-MM-DD): ");
                    String fechaStr = sc.next();
                    System.out.print("Tipo (1-Teórico, 2-Práctico): ");
                    int tipo = sc.nextInt();

                    // 3. Insertar en la BD
                    int filas = new ExamenDAO().solicitarExamen(conex, idA, tipo, java.sql.Date.valueOf(fechaStr));
                    if (filas > 0) System.out.println("[OK] Examen solicitado correctamente.");
                }

                break;

            default:
                System.out.println("[!] Opción no válida para tu perfil.");
        }
        System.out.println("----------------------------------------");
        
        
    }
}