package cooperativa; 

// Clase principal que gestiona el servicio de taxi, incluyendo conductores, operadores, solicitudes, rutas y el grafo de zonas. 
// Proporciona métodos para registrar zonas, conexiones, conductores y solicitudes, así como para atender,
public class ServicioTaxi {

    private final ListaEnlazada<Conductor> conductores = new ListaEnlazada<>();
    private final ListaEnlazada<String> operadores = new ListaEnlazada<>();
    private final Cola<Solicitud> solicitudesPendientes = new Cola<>();
    private final ListaEnlazada<Solicitud> solicitudesActivas = new ListaEnlazada<>();
    private final ListaEnlazada<Solicitud> historial = new ListaEnlazada<>();
    private final Pila<String> pilaOperaciones = new Pila<>();
    private final GrafoZonas grafo = new GrafoZonas();

    private int consecutivoSolicitud = 1;
    private int maxOperadores = 1;
    private int operadoresOcupados = 0;

    // Devuelve el grafo de zonas para permitir su manipulación desde el menú de consola.
    public GrafoZonas getGrafo() {
        return grafo;
    }

    // Devuelve la lista de conductores registrados.
    public String listarConductores() {
        return conductores.mostrarEnLineas();
    }

    // Devuelve la lista de operadores registrados.
    public String listarOperadores() {
        return operadores.mostrarEnLineas();
    }

    // Devuelve las solicitudes que siguen en cola.
    public String listarSolicitudesPendientes() {
        return solicitudesPendientes.mostrarEnLineas();
    }

    // Devuelve las solicitudes que ya fueron atendidas y siguen activas.
    public String listarSolicitudesActivas() {
        return solicitudesActivas.mostrarEnLineas();
    }

    // Devuelve el historial de solicitudes cerradas.
    public String listarHistorial() {
        return historial.mostrarEnLineas();
    }

    // Devuelve la pila de acciones operativas recientes.
    public String listarPilaOperaciones() {
        return pilaOperaciones.mostrarEnLineas();
    }

    // Registra un operador y deja evidencia en la pila de operaciones.
    public void agregarOperador(String nombre) {
        operadores.agregar(nombre);
        pilaOperaciones.apilar("Se registró el operador: " + nombre);
    }

    // Define la cantidad máxima de operadores que pueden atender al mismo tiempo.
    public void establecerMaxOperadores(int maxOperadores) {
        if (maxOperadores > 0) {
            this.maxOperadores = maxOperadores;
        }
    }

    // Devuelve la cantidad máxima de operadores que pueden atender al mismo tiempo.
    public int getMaxOperadores() {
        return maxOperadores;
    }

    // Devuelve la cantidad de operadores que actualmente están ocupados atendiendo solicitudes.
    public int getOperadoresOcupados() {
        return operadoresOcupados;
    }

    // Registra una nueva zona en el grafo.
    public boolean registrarZona(String nombre) {
        boolean ok = grafo.agregarZona(nombre);
        if (ok) {
            pilaOperaciones.apilar("Se registró la zona: " + nombre);
        }
        return ok;
    }

    // Registra una conexión entre dos zonas.
    public boolean registrarConexion(String origen, String destino, int distanciaMetros) {
        boolean ok = grafo.agregarConexion(origen, destino, distanciaMetros);
        if (ok) {
            pilaOperaciones.apilar("Se creó la conexión: " + origen + " <-> " + destino + " (" + distanciaMetros + " m)");
        }
        return ok;
    }

    // Cambia el estado de una vía a habilitada o cerrada.
    public boolean cambiarEstadoConexion(String origen, String destino, boolean activa) {
        boolean ok = grafo.cambiarEstadoConexion(origen, destino, activa);
        if (ok) {
            pilaOperaciones.apilar((activa ? "Se habilitó" : "Se cerró") + " la vía: " + origen + " <-> " + destino);
        }
        return ok;
    }

    // Registra un conductor y valida que su zona exista y que la placa no esté repetida.
    public boolean registrarConductor(String placa, String nombre, String cedula, String zonaActual,
                                      boolean estandar, boolean baul, boolean mascotas) {
        if (!grafo.existeZona(zonaActual)) {
            return false;
        }
        if (buscarConductorPorPlaca(placa) != null) {
            return false;
        }
        conductores.agregar(new Conductor(placa, nombre, cedula, zonaActual, estandar, baul, mascotas));
        pilaOperaciones.apilar("Se registró el conductor: " + nombre + " - " + placa);
        return true;
    }

    // Encola una nueva solicitud de servicio.
    public boolean registrarSolicitud(String origen, String destino, TipoServicio tipoServicio) {

        // Valida que las zonas de origen y destino existan en el grafo antes de registrar la solicitud.
        if (!grafo.existeZona(origen) || !grafo.existeZona(destino)) {
            return false;
        }
        Solicitud solicitud = new Solicitud(consecutivoSolicitud++, origen, destino, tipoServicio);
        solicitudesPendientes.encolar(solicitud);
        pilaOperaciones.apilar("Se registró la solicitud #" + solicitud.getId() + " en cola");
        return true;
    }

    // Atiende la primera solicitud de la cola si hay conductor y ruta disponibles.
    public Solicitud atenderSiguienteSolicitud() {

        if (solicitudesPendientes.estaVacia()) {
            return null;
        }
        if (operadoresOcupados >= maxOperadores) {
            return null;
        }
        Solicitud solicitud = solicitudesPendientes.desencolar();
        if (solicitud == null) {
            return null;
        }

        operadoresOcupados++;
        solicitud.setEstado(EstadoSolicitud.EN_ATENCION);

        // Se elige el conductor más conveniente según disponibilidad, servicio y cercanía.
        Conductor candidato = seleccionarMejorConductor(solicitud);
        if (candidato == null) {
            solicitud.setEstado(EstadoSolicitud.EN_ESPERA);
            solicitudesPendientes.encolar(solicitud);
            operadoresOcupados--;
            pilaOperaciones.apilar("No se encontró conductor disponible para la solicitud #" + solicitud.getId());
            return null;
        }

        // Ruta desde el conductor hasta el pasajero.
        ResultadoRuta rutaRecogida = grafo.rutaMasCorta(candidato.getZonaActual(), solicitud.getZonaOrigen());
        // Ruta del viaje solicitado por el usuario.
        ResultadoRuta rutaServicio = grafo.rutaMasCorta(solicitud.getZonaOrigen(), solicitud.getZonaDestino());

        // Si alguna de las rutas no es posible por cierres viales, se devuelve la solicitud a la cola de pendientes y se libera al operador.
        if (!rutaRecogida.isEncontrada() || !rutaServicio.isEncontrada()) {
            solicitud.setEstado(EstadoSolicitud.EN_ESPERA);
            solicitudesPendientes.encolar(solicitud);
            operadoresOcupados--;
            pilaOperaciones.apilar("La solicitud #" + solicitud.getId() + " quedó en espera por cierre vial.");
            return null;
        }

        // Se asigna el conductor a la solicitud, se actualiza su estado a ocupado y se registran los detalles de la ruta y tiempos estimados para mostrar al operador en el menú de consola.
        candidato.setDisponible(false);
        solicitud.setPlacaConductor(candidato.getPlaca());
        solicitud.setNombreConductor(candidato.getNombre());
        solicitud.setCedulaConductor(candidato.getCedula());
        solicitud.setZonaConductor(candidato.getZonaActual());
        solicitud.setDistanciaRecogidaMetros(rutaRecogida.getDistanciaMetros());
        solicitud.setRutaRecogida(rutaRecogida.getRutaTexto());

        // Tiempo estimado de llegada al pasajero.
        int tiempoRecogida;
        if (candidato.getZonaActual().equalsIgnoreCase(solicitud.getZonaOrigen())) {
            tiempoRecogida = 5;
        } else {
            tiempoRecogida = Math.max(5, (int) Math.ceil(rutaRecogida.getDistanciaMetros() / 333.0));
        }
        solicitud.setTiempoEstimadoRecogidaMin(tiempoRecogida);

        solicitud.setDistanciaViajeMetros(rutaServicio.getDistanciaMetros());
        solicitud.setRutaServicio(rutaServicio.getRutaTexto());
        solicitud.setTiempoEstimadoViajeMin(Math.max(1, (int) Math.ceil(rutaServicio.getDistanciaMetros() / 333.0)));
        solicitud.setTarifaCalculada(calcularTarifa(rutaServicio.getDistanciaMetros()));

        solicitudesActivas.agregar(solicitud);
        pilaOperaciones.apilar("Se asignó el conductor " + candidato.getNombre() + " a la solicitud #" + solicitud.getId());
        return solicitud;
    }

    // Finaliza una solicitud activa, liberando al conductor y registrando el motivo de cierre en el historial.
    public boolean finalizarSolicitud(int id) {
        Solicitud solicitud = solicitudesActivas.buscar(s -> s.getId() == id);
        if (solicitud == null) {
            return false;
        }
        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitud.setMotivoCierre("Servicio completado exitosamente");
        solicitudesActivas.eliminar(s -> s.getId() == id);
        historial.agregar(solicitud);
        Conductor conductor = buscarConductorPorPlaca(solicitud.getPlacaConductor());
        if (conductor != null) {
            conductor.setDisponible(true);
            conductor.setZonaActual(solicitud.getZonaDestino());
        }
        operadoresOcupados = Math.max(0, operadoresOcupados - 1);
        pilaOperaciones.apilar("Se finalizó la solicitud #" + id);
        return true;
    }

    // Cancela una solicitud activa o pendiente por medio de su ID, solicitando un motivo de cancelación y mostrando un mensaje de éxito o error según corresponda.
    public boolean cancelarSolicitud(int id, String motivo) {
        Solicitud solicitudActiva = solicitudesActivas.buscar(s -> s.getId() == id);
        if (solicitudActiva != null) {
            solicitudActiva.setEstado(EstadoSolicitud.CANCELADA);
            solicitudActiva.setMotivoCierre(motivo);
            solicitudesActivas.eliminar(s -> s.getId() == id);
            historial.agregar(solicitudActiva);
            Conductor conductor = buscarConductorPorPlaca(solicitudActiva.getPlacaConductor());
            if (conductor != null) {
                conductor.setDisponible(true);
                conductor.setZonaActual(solicitudActiva.getZonaOrigen());
            }
            operadoresOcupados = Math.max(0, operadoresOcupados - 1);
            pilaOperaciones.apilar("Se canceló la solicitud #" + id);
            return true;
        }

        Solicitud solicitudPendiente = solicitudesPendientes.buscar(s -> s.getId() == id);
        if (solicitudPendiente != null) {
            solicitudesPendientes.eliminar(s -> s.getId() == id);
            solicitudPendiente.setEstado(EstadoSolicitud.CANCELADA);
            solicitudPendiente.setMotivoCierre(motivo);
            historial.agregar(solicitudPendiente);
            pilaOperaciones.apilar("Se canceló la solicitud en cola #" + id);
            return true;
        }
        return false;
    }

    // Busca una solicitud dentro de las activas.
    public Solicitud buscarSolicitudActivaPorId(int id) {
        return solicitudesActivas.buscar(s -> s.getId() == id);
    }

    // Busca una solicitud dentro de la cola de pendientes.
    public Solicitud buscarSolicitudEnPendientesPorId(int id) {
        return solicitudesPendientes.buscar(s -> s.getId() == id);
    }

    // Busca un conductor por su placa.
    public Conductor buscarConductorPorPlaca(String placa) {
        return conductores.buscar(c -> c.getPlaca().equalsIgnoreCase(placa.trim()));
    }

    // Resume en texto el estado general del sistema.
    public String verEstadoGeneral() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTADO GENERAL ===\n");
        sb.append("Operadores registrados: ").append(operadores.tamanio()).append("\n");
        sb.append("Capacidad simultánea: ").append(maxOperadores).append("\n");
        sb.append("Operadores ocupados: ").append(operadoresOcupados).append("\n");
        sb.append("Conductores registrados: ").append(conductores.tamanio()).append("\n");
        sb.append("Solicitudes pendientes: ").append(solicitudesPendientes.tamanio()).append("\n");
        sb.append("Solicitudes en atención: ").append(solicitudesActivas.tamanio()).append("\n");
        sb.append("Solicitudes en historial: ").append(historial.tamanio()).append("\n");
        return sb.toString();
    }

    // Carga datos de ejemplo para demostrar el funcionamiento del sistema.
    public String cargarDatosDemostracion() {

        StringBuilder sb = new StringBuilder(); // Se utiliza un StringBuilder para acumular mensajes sobre la carga de datos de demostración, que se mostrarán al usuario en el menú de consola.
        registrarZona("Centro");
        registrarZona("Norte");
        registrarZona("Sur");
        registrarZona("Occidente");
        registrarZona("Aeropuerto");
        registrarZona("Universidad");

        registrarConexion("Centro", "Norte", 1800);
        registrarConexion("Centro", "Sur", 2200);
        registrarConexion("Centro", "Occidente", 2500);
        registrarConexion("Norte", "Aeropuerto", 4200);
        registrarConexion("Sur", "Universidad", 3100);
        registrarConexion("Occidente", "Universidad", 2800);
        registrarConexion("Norte", "Universidad", 3600);
        registrarConexion("Sur", "Aeropuerto", 5000);

        agregarOperador("Operador 1");
        agregarOperador("Operador 2");
        establecerMaxOperadores(2);

        registrarConductor("ABC123", "Carlos Gómez", "100200300", "Centro", true, true, false);
        registrarConductor("XYZ789", "Laura Pérez", "200300400", "Norte", true, false, true);
        registrarConductor("QWE456", "Andrés Ruiz", "300400500", "Sur", true, true, true);
        registrarConductor("RTY321", "Marta Díaz", "400500600", "Occidente", true, false, false);

        registrarSolicitud("Centro", "Aeropuerto", TipoServicio.ESTANDAR);
        registrarSolicitud("Sur", "Universidad", TipoServicio.MASCOTAS);

        sb.append("Datos de demostración cargados correctamente.");
        return sb.toString();
    }

    // Selecciona el mejor conductor disponible para atender una solicitud, considerando su disponibilidad, habilitación para el tipo de servicio solicitado y cercanía a la zona de origen del pasajero.
    private Conductor seleccionarMejorConductor(Solicitud solicitud) {
        Conductor mejor = null;
        int mejorDistancia = Integer.MAX_VALUE;

        // Se recorre la lista de conductores para encontrar el más adecuado según los criterios mencionados, utilizando el grafo de zonas para calcular la distancia desde su ubicación actual hasta la zona de origen de la solicitud.
        for (int i = 0; i < conductores.tamanio(); i++) {
            Conductor conductor = conductores.obtener(i);
            if (conductor == null || !conductor.isDisponible() || !conductor.puedeAtender(solicitud.getTipoServicio())) {
                continue;
            }

            if (conductor.getZonaActual().equalsIgnoreCase(solicitud.getZonaOrigen())) {
                return conductor;
            }

            ResultadoRuta ruta = grafo.rutaMasCorta(conductor.getZonaActual(), solicitud.getZonaOrigen());
            if (ruta.isEncontrada() && ruta.getDistanciaMetros() < mejorDistancia) {
                mejorDistancia = ruta.getDistanciaMetros();
                mejor = conductor;
            }
        }
        return mejor;
    }

    // Calcula la tarifa final según la distancia recorrida.
    private int calcularTarifa(int distanciaMetros) {
        int base = 5000;
        int adicional;
        if (distanciaMetros <= 1000) {
            adicional = 2000;
        } else if (distanciaMetros <= 3000) {
            adicional = 4000;
        } else if (distanciaMetros <= 6000) {
            adicional = 7000;
        } else if (distanciaMetros <= 10000) {
            adicional = 10000;
        } else {
            adicional = 12000;
        }
        return base + adicional;
    }
}
