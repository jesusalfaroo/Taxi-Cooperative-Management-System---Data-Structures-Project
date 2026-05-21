package cooperativa;
/** Modela una solicitud de taxi, su estado y la información calculada durante la atención. */


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

    /** Crea una solicitud nueva en estado de espera. */
    public Solicitud(int id, String zonaOrigen, String zonaDestino, TipoServicio tipoServicio) {
        this.id = id;
        this.zonaOrigen = zonaOrigen.trim();
        this.zonaDestino = zonaDestino.trim();
        this.tipoServicio = tipoServicio;
        this.estado = EstadoSolicitud.EN_ESPERA;
    }

    public int getId() {
        return id;
    }

    public String getZonaOrigen() {
        return zonaOrigen;
    }

    public String getZonaDestino() {
        return zonaDestino;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

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

    @Override
    /** Devuelve un resumen corto de la solicitud. */
    public String toString() {
        String conductor = (placaConductor == null) ? "Sin asignar" : placaConductor + " - " + nombreConductor;
        return "Solicitud #" + id +
                " | Origen: " + zonaOrigen +
                " | Destino: " + zonaDestino +
                " | Servicio: " + tipoServicio +
                " | Estado: " + estado +
                " | Conductor: " + conductor;
    }

    /** Devuelve la información completa que se muestra al atender o consultar la solicitud. */
    public String detalleCompleto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solicitud #").append(id).append("\n");
        sb.append("Origen: ").append(zonaOrigen).append("\n");
        sb.append("Destino: ").append(zonaDestino).append("\n");
        sb.append("Servicio: ").append(tipoServicio).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
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
        if (motivoCierre != null) {
            sb.append("Motivo de cierre: ").append(motivoCierre).append("\n");
        }
        return sb.toString();
    }
}
