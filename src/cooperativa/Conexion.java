package cooperativa;
/** Representa una vía entre dos zonas con su distancia y estado de habilitación. */


public class Conexion {
    private final String zonaOrigen;
    private final String zonaDestino;
    private final int distanciaMetros;
    private boolean activa;

    /** Construye una conexión activa entre dos zonas. */
    public Conexion(String zonaOrigen, String zonaDestino, int distanciaMetros) {
        this.zonaOrigen = zonaOrigen.trim();
        this.zonaDestino = zonaDestino.trim();
        this.distanciaMetros = distanciaMetros;
        this.activa = true;
    }

    public String getZonaOrigen() {
        return zonaOrigen;
    }

    public String getZonaDestino() {
        return zonaDestino;
    }

    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    @Override
    public String toString() {
        return zonaOrigen + " <-> " + zonaDestino + " | " + distanciaMetros + " m | " + (activa ? "Activa" : "Cerrada");
    }
}
