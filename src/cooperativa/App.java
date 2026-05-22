package cooperativa;

// Clase principal que contiene el método main para ejecutar el sistema de gestión de servicios de taxi, 
// mostrando un menú interactivo para realizar diversas acciones relacionadas con zonas, conexiones, operadores, conductores y solicitudes.
public class App {

    // El método main muestra un menú interactivo para gestionar zonas, conexiones, operadores, conductores y solicitudes de taxi, 
    // permitiendo al usuario realizar diversas acciones y ver el estado general del sistema.
    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        ServicioTaxi sistema = new ServicioTaxi();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n==============================================");
            System.out.println("   SISTEMA DE GESTIÓN DE SERVICIOS DE TAXI");
            System.out.println("==============================================");
            System.out.println("1. Cargar datos de demostración");
            System.out.println("2. Registrar zona");
            System.out.println("3. Registrar conexión");
            System.out.println("4. Habilitar o deshabilitar conexión");
            System.out.println("5. Registrar operador");
            System.out.println("6. Registrar conductor");
            System.out.println("7. Registrar solicitud");
            System.out.println("8. Atender siguiente solicitud");
            System.out.println("9. Finalizar solicitud");
            System.out.println("10. Cancelar solicitud");
            System.out.println("11. Ver datos registrados");
            System.out.println("12. Ver historial");
            System.out.println("13. Ver pila de operaciones");
            System.out.println("14. Ver estado general");
            System.out.println("0. Salir");

            int opcion = entrada.leerEnteroEnRango("Selecciona una opción: ", 0, 14);

            // Cada opción del menú ejecuta una acción del sistema, mostrando mensajes de éxito o error según corresponda.
            switch (opcion) {
                case 1 -> {
                    System.out.println(sistema.cargarDatosDemostracion());
                }
                case 2 -> {
                    String zona = entrada.leerTexto("Nombre de la nueva zona: ");
                    System.out.println(sistema.registrarZona(zona)
                            ? "Zona registrada correctamente."
                            : "No fue posible registrar la zona.");
                }
                case 3 -> {
                    String origen = entrada.leerTexto("Zona de origen: ");
                    String destino = entrada.leerTexto("Zona de destino: ");
                    int distancia = entrada.leerEntero("Distancia en metros: ");
                    System.out.println(sistema.registrarConexion(origen, destino, distancia)
                            ? "Conexión registrada correctamente."
                            : "No fue posible registrar la conexión.");
                }
                case 4 -> {
                    String origen = entrada.leerTexto("Zona de origen de la vía: ");
                    String destino = entrada.leerTexto("Zona de destino de la vía: ");
                    boolean activa = entrada.leerSiNo("¿Deseas habilitar la conexión?");
                    System.out.println(sistema.cambiarEstadoConexion(origen, destino, activa)
                            ? "Estado de la conexión actualizado."
                            : "No se encontró la conexión.");
                }
                case 5 -> {
                    String nombre = entrada.leerTexto("Nombre del operador: ");
                    sistema.agregarOperador(nombre);
                    System.out.println("Operador registrado.");
                }
                case 6 -> registrarConductor(entrada, sistema);
                case 7 -> registrarSolicitud(entrada, sistema);
                case 8 -> atenderSolicitud(sistema);
                case 9 -> finalizarSolicitud(entrada, sistema);
                case 10 -> cancelarSolicitud(entrada, sistema);
                case 11 -> verDatos(sistema);
                case 12 -> {
                    System.out.println("\n=== HISTORIAL ===");
                    System.out.println(sistema.listarHistorial());
                }
                case 13 -> {
                    System.out.println("\n=== PILA DE OPERACIONES ===");
                    System.out.println(sistema.listarPilaOperaciones());
                }
                case 14 -> {
                    System.out.println(sistema.verEstadoGeneral());
                }
                case 0 -> salir = true;
            }
        }
        System.out.println("Gracias por usar el sistema.");
    }

    // Captura los datos de un conductor y los envía al sistema para su registro, mostrando un mensaje de éxito o error según corresponda.
    private static void registrarConductor(Entrada entrada, ServicioTaxi sistema) {
        String placa = entrada.leerTexto("Placa: ");
        String nombre = entrada.leerTexto("Nombre completo: ");
        String cedula = entrada.leerTexto("Cédula: ");
        String zona = entrada.leerTexto("Zona actual: ");
        System.out.println("Servicios habilitados para este conductor:");
        boolean estandar = entrada.leerSiNo("¿Estandar?");
        boolean baul = entrada.leerSiNo("¿Baul o parrilla?");
        boolean mascotas = entrada.leerSiNo("¿Mascotas?");
        boolean ok = sistema.registrarConductor(placa, nombre, cedula, zona, estandar, baul, mascotas);
        System.out.println(ok ? "Conductor registrado correctamente." : "No se pudo registrar el conductor. Verifica la zona o la placa.");
    }

    // Captura los datos de una solicitud y la coloca en la cola de solicitudes pendientes, mostrando un mensaje de éxito o error según corresponda.
    private static void registrarSolicitud(Entrada entrada, ServicioTaxi sistema) {
        String origen = entrada.leerTexto("Zona de origen: ");
        String destino = entrada.leerTexto("Zona de destino: ");
        System.out.println("Tipo de servicio:");
        System.out.println("1. Taxi estándar");
        System.out.println("2. Taxi con baúl o parrilla");
        System.out.println("3. Taxi para mascotas");
        TipoServicio tipo = null;
        // El bucle asegura que el usuario seleccione un tipo de servicio válido antes de continuar.
        while (tipo == null) {
            int op = entrada.leerEnteroEnRango("Selecciona: ", 1, 3);
            tipo = TipoServicio.fromOpcion(op);
        }
        boolean ok = sistema.registrarSolicitud(origen, destino, tipo);
        System.out.println(ok ? "Solicitud encolada correctamente." : "No fue posible registrar la solicitud. Revisa las zonas.");
    }

    // Atiende la siguiente solicitud disponible y muestra el detalle completo de la asignación, o un mensaje de error si no se pudo atender.
    private static void atenderSolicitud(ServicioTaxi sistema) {
        Solicitud solicitud = sistema.atenderSiguienteSolicitud();
        // Si no hay solicitudes pendientes, conductores disponibles o capacidad de operadores, se muestra un mensaje informativo.
        if (solicitud == null) {
            System.out.println("No se pudo atender la solicitud. Puede no haber solicitudes, conductores disponibles o capacidad de operadores.");
            return;
        }
        System.out.println("\n=== SOLICITUD ATENDIDA ===");
        System.out.println(solicitud.detalleCompleto());
    }

    // Finaliza una solicitud activa por medio de su ID, mostrando un mensaje de éxito o error según corresponda.
    private static void finalizarSolicitud(Entrada entrada, ServicioTaxi sistema) {
        int id = entrada.leerEntero("ID de la solicitud a finalizar: ");
        boolean ok = sistema.finalizarSolicitud(id);
        System.out.println(ok ? "Solicitud finalizada." : "No se encontró una solicitud activa con ese ID.");
    }

    // Cancela una solicitud activa o pendiente por medio de su ID, solicitando un motivo de cancelación y mostrando un mensaje de éxito o error según corresponda.
    private static void cancelarSolicitud(Entrada entrada, ServicioTaxi sistema) {
        int id = entrada.leerEntero("ID de la solicitud a cancelar: ");
        String motivo = entrada.leerTexto("Motivo de la cancelación: ");
        boolean ok = sistema.cancelarSolicitud(id, motivo);
        System.out.println(ok ? "Solicitud cancelada." : "No se encontró una solicitud activa o pendiente con ese ID.");
    }

    // Muestra la información principal almacenada en el sistema, incluyendo conductores, operadores, zonas, conexiones y solicitudes, de forma legible para el menú de consola.
    private static void verDatos(ServicioTaxi sistema) {
        System.out.println("\n=== CONDUCTORES ===");
        System.out.println(sistema.listarConductores());

        System.out.println("=== OPERADORES ===");
        System.out.println(sistema.listarOperadores());

        System.out.println("=== ZONAS ===");
        System.out.println(sistema.getGrafo().listarZonas());

        System.out.println("=== CONEXIONES ===");
        System.out.println(sistema.getGrafo().listarConexiones());

        System.out.println("=== SOLICITUDES PENDIENTES ===");
        System.out.println(sistema.listarSolicitudesPendientes());

        System.out.println("=== SOLICITUDES EN ATENCIÓN ===");
        System.out.println(sistema.listarSolicitudesActivas());
    }
}
