Este proyecto consiste en el diseño de una base de datos en MySQL orientada a la gestión de una autoescuela (Autoescuela Rapido). El objetivo principal es representar de forma estructurada todos los elementos que intervienen en su funcionamiento, como alumnos, profesores, vehículos, clases, exámenes y pagos, permitiendo simular un entorno real.

La base de datos se organiza en torno a la entidad principal, la autoescuela, de la cual dependen el resto de elementos. A partir de ella se gestionan los profesores, los alumnos y los vehículos, estableciendo relaciones que permiten mantener toda la información conectada y coherente.

El sistema permite gestionar a los alumnos desde su inscripción hasta la obtención del permiso. Cada alumno tiene asignado un profesor tutor y dispone de un estado que indica su progreso dentro de la autoescuela. Además, los alumnos pueden comprar bonos de clases, que incluyen un número determinado de sesiones que se van consumiendo a medida que se realizan.

Las clases son uno de los elementos más importantes del sistema, ya que relacionan a alumnos, profesores, vehículos y tipos de clase. Cada clase tiene una fecha, una duración y puede ser individual o grupal. Además, pueden estar asociadas a un bono, lo que permite descontar automáticamente las clases disponibles del alumno.

Por otro lado, los profesores no solo imparten clases, sino que también tienen asociada una disponibilidad horaria que permite organizar correctamente la planificación. Asimismo, los vehículos están vinculados a profesores, lo que facilita la gestión de las clases prácticas.

La base de datos también incluye la gestión económica mediante los pagos realizados por los alumnos, donde se registra la fecha, el importe y el método de pago. Esto permite llevar un control financiero del sistema.

En cuanto a la evaluación, se contemplan distintos tipos de exámenes, como teóricos y prácticos, y se registran los intentos y resultados de cada alumno. De esta forma, se puede hacer un seguimiento detallado de su evolución académica.

Finalmente, el sistema está preparado para integrarse con aplicaciones que requieran control de acceso, mediante una tabla de usuarios con distintos roles, como gestor, profesor o alumno.

En conjunto, esta base de datos proporciona una estructura completa que permite gestionar de forma eficiente todos los aspectos de una autoescuela, integrando la organización de recursos, la planificación de actividades y el seguimiento tanto académico como económico de los alumnos. 