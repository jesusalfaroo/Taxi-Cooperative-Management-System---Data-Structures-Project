package cooperativa;

// Clase que representa una conexión entre dos zonas, con la distancia en metros 
// y un estado de activa o cerrada para indicar si la conexión está disponible para el cálculo de rutas.
public class Conexion {
    private final String zonaOrigen;
    private final String zonaDestino;
    private final int distanciaMetros;
    private boolean activa;

    // Construye una conexión con las zonas de origen y destino, la distancia en metros y la activa por defecto.
    public Conexion(String zonaOrigen, String zonaDestino, int distanciaMetros) {
        this.zonaOrigen = zonaOrigen.trim();
        this.zonaDestino = zonaDestino.trim();
        this.distanciaMetros = distanciaMetros;
        this.activa = true;
    }

    // Devuelve la zona de origen de la conexión.
    public String getZonaOrigen() {
        return zonaOrigen;
    }

    // Devuelve la zona de destino de la conexión.
    public String getZonaDestino() {
        return zonaDestino;
    }

    // Devuelve la distancia en metros entre las zonas de origen y destino.
    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    // Indica si la conexión está activa (habilitada) o cerrada (deshabilitada).
    public boolean isActiva() {
        return activa;
    }

    // Cambia el estado de la conexión a activa o cerrada según el valor proporcionado.
    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // Representa la conexión en formato legible, mostrando las zonas, distancia y estado para facilitar su visualización en el menú de consola.
    @Override
    public String toString() {
        return zonaOrigen + " <-> " + zonaDestino + " | " + distanciaMetros + " m | " + (activa ? "Activa" : "Cerrada");
    }
}
