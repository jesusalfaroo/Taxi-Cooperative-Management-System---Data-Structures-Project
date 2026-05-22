package cooperativa;

// Clase que representa una solicitud de taxi, con información sobre el origen, destino, tipo de servicio, estado actual, conductor asignado (si lo hay), detalles de la ruta y tarifas calculadas para facilitar su gestión en el sistema.
public class Solicitud {
    private final int id;
    private final String zonaOrigen;
    private final String zonaDestino;
    private final TipoServicio tipoServicio;
    private EstadoSolicitud estado;
    private String placaConductor;
    private String nombreConductor;
    private String cedulaConductor;
    private String zonaConductor;
    private int distanciaRecogidaMetros;
    private int tiempoEstimadoRecogidaMin;
    private int distanciaViajeMetros;
    private int tiempoEstimadoViajeMin;
    private int tarifaCalculada;
    private String rutaRecogida;
    private String rutaServicio;
    private String motivoCierre;

    // Crea una solicitud nueva en estado de espera.
    public Solicitud(int id, String zonaOrigen, String zonaDestino, TipoServicio tipoServicio) {
        this.id = id;
        this.zonaOrigen = zonaOrigen.trim();
        this.zonaDestino = zonaDestino.trim();
        this.tipoServicio = tipoServicio;
        this.estado = EstadoSolicitud.EN_ESPERA;
    }

    // Getters y setters para acceder y modificar la información de la solicitud, como el estado, conductor asignado, detalles de la ruta y tarifas calculadas, permitiendo su gestión a lo largo del ciclo de vida de la solicitud.
    public int getId() {
        return id;
    }

    // Devuelve la zona de origen de la solicitud.
    public String getZonaOrigen() {
        return zonaOrigen;
    }

    // Devuelve la zona de destino de la solicitud.
    public String getZonaDestino() {
        return zonaDestino;
    }

    // Devuelve el tipo de servicio solicitado (estándar, con baúl o con mascotas).
    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    // Devuelve el estado actual de la solicitud (en espera, en atención, cancelada o finalizada).
    public EstadoSolicitud getEstado() {
        return estado;
    }

    // Cambia el estado de la solicitud a medida que avanza en su ciclo de vida, permitiendo reflejar su progreso desde la creación hasta la finalización o cancelación.
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public String getPlacaConductor() {
        return placaConductor;
    }

    public void setPlacaConductor(String placaConductor) {
        this.placaConductor = placaConductor;
    }

    public String getNombreConductor() {
        return nombreConductor;
    }

    public void setNombreConductor(String nombreConductor) {
        this.nombreConductor = nombreConductor;
    }

    public String getCedulaConductor() {
        return cedulaConductor;
    }

    public void setCedulaConductor(String cedulaConductor) {
        this.cedulaConductor = cedulaConductor;
    }

    public String getZonaConductor() {
        return zonaConductor;
    }

    public void setZonaConductor(String zonaConductor) {
        this.zonaConductor = zonaConductor;
    }

    public int getDistanciaRecogidaMetros() {
        return distanciaRecogidaMetros;
    }

    public void setDistanciaRecogidaMetros(int distanciaRecogidaMetros) {
        this.distanciaRecogidaMetros = distanciaRecogidaMetros;
    }

    public int getTiempoEstimadoRecogidaMin() {
        return tiempoEstimadoRecogidaMin;
    }

    public void setTiempoEstimadoRecogidaMin(int tiempoEstimadoRecogidaMin) {
        this.tiempoEstimadoRecogidaMin = tiempoEstimadoRecogidaMin;
    }

    public int getDistanciaViajeMetros() {
        return distanciaViajeMetros;
    }

    public void setDistanciaViajeMetros(int distanciaViajeMetros) {
        this.distanciaViajeMetros = distanciaViajeMetros;
    }

    public int getTiempoEstimadoViajeMin() {
        return tiempoEstimadoViajeMin;
    }

    public void setTiempoEstimadoViajeMin(int tiempoEstimadoViajeMin) {
        this.tiempoEstimadoViajeMin = tiempoEstimadoViajeMin;
    }

    public int getTarifaCalculada() {
        return tarifaCalculada;
    }

    public void setTarifaCalculada(int tarifaCalculada) {
        this.tarifaCalculada = tarifaCalculada;
    }

    public String getRutaRecogida() {
        return rutaRecogida;
    }

    public void setRutaRecogida(String rutaRecogida) {
        this.rutaRecogida = rutaRecogida;
    }

    public String getRutaServicio() {
        return rutaServicio;
    }

    public void setRutaServicio(String rutaServicio) {
        this.rutaServicio = rutaServicio;
    }

    public String getMotivoCierre() {
        return motivoCierre;
    }

    public void setMotivoCierre(String motivoCierre) {
        this.motivoCierre = motivoCierre;
    }

    // Representa la solicitud en formato legible para mostrar en el menú de consola, incluyendo información clave como el ID, zonas, tipo de servicio, 
    // estado y conductor asignado (si lo hay) para facilitar su identificación y seguimiento.
    @Override
    public String toString() {
        
        String conductor = (placaConductor == null) ? "Sin asignar" : placaConductor + " - " + nombreConductor;
        return "Solicitud #" + id +
                " | Origen: " + zonaOrigen +
                " | Destino: " + zonaDestino +
                " | Servicio: " + tipoServicio +
                " | Estado: " + estado +
                " | Conductor: " + conductor;
    }

    // Proporciona una descripción detallada de la solicitud, incluyendo toda la información relevante sobre el viaje, conductor asignado, 
    // rutas y tarifas, para facilitar su revisión completa en el menú de consola.
    public String detalleCompleto() {

        StringBuilder sb = new StringBuilder();
        sb.append("Solicitud #").append(id).append("\n");
        sb.append("Origen: ").append(zonaOrigen).append("\n");
        sb.append("Destino: ").append(zonaDestino).append("\n");
        sb.append("Servicio: ").append(tipoServicio).append("\n");
        sb.append("Estado: ").append(estado).append("\n");

        // Si hay un conductor asignado, se muestran sus detalles junto con la información de la ruta y tarifas calculadas, proporcionando una visión completa del servicio asociado a la solicitud.
        if (placaConductor != null) {
            sb.append("Conductor: ").append(nombreConductor).append(" | ").append(cedulaConductor).append(" | ").append(placaConductor).append("\n");
            sb.append("Zona actual del conductor: ").append(zonaConductor).append("\n");
            sb.append("Ruta recogida: ").append(rutaRecogida).append("\n");
            sb.append("Distancia recogida: ").append(distanciaRecogidaMetros).append(" m\n");
            sb.append("Tiempo estimado de recogida: ").append(tiempoEstimadoRecogidaMin).append(" min\n");
            sb.append("Ruta del servicio: ").append(rutaServicio).append("\n");
            sb.append("Distancia del viaje: ").append(distanciaViajeMetros).append(" m\n");
            sb.append("Tiempo estimado del viaje: ").append(tiempoEstimadoViajeMin).append(" min\n");
            sb.append("Tarifa calculada: $").append(String.format("%,d", tarifaCalculada)).append(" COP\n");
        }

        // Si la solicitud fue cancelada o finalizada con un motivo de cierre, se muestra esa información para proporcionar contexto adicional sobre el estado de la solicitud.
        if (motivoCierre != null) {
            sb.append("Motivo de cierre: ").append(motivoCierre).append("\n");
        }

        // El método devuelve la cadena completa que representa el detalle de la solicitud, lista para ser mostrada en el menú de consola.
        return sb.toString();
    }
}
