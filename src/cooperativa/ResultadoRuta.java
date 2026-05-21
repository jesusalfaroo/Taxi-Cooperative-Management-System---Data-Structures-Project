package cooperativa;
/** Guarda el resultado del cálculo de ruta: si existe, la distancia y el recorrido. */


public class ResultadoRuta {
    private final boolean encontrada;
    private final int distanciaMetros;
    private final String rutaTexto;

    /** Guarda el resultado de una ruta calculada. */
    public ResultadoRuta(boolean encontrada, int distanciaMetros, String rutaTexto) {
        this.encontrada = encontrada;
        this.distanciaMetros = distanciaMetros;
        this.rutaTexto = rutaTexto;
    }

    public boolean isEncontrada() {
        return encontrada;
    }

    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    public String getRutaTexto() {
        return rutaTexto;
    }
}
