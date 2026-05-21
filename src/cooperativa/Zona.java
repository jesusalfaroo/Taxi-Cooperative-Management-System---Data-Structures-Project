package cooperativa;
/** Representa una zona geográfica de la ciudad. */


public class Zona {
    private final String nombre;

    /** Construye una zona a partir de su nombre. */
    public Zona(String nombre) {
        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
