package cooperativa;

// Clase que representa el resultado de una ruta calculada, indicando si se encontró una ruta válida, 
// la distancia total en metros y una representación textual de la ruta para facilitar su visualización en el menú de consola.
public class ResultadoRuta {
    private final boolean encontrada;
    private final int distanciaMetros;
    private final String rutaTexto;

    // Guarda el resultado de una ruta calculada.
    public ResultadoRuta(boolean encontrada, int distanciaMetros, String rutaTexto) {
        this.encontrada = encontrada;
        this.distanciaMetros = distanciaMetros;
        this.rutaTexto = rutaTexto;
    }

    // Indica si se encontró una ruta válida entre las zonas de origen y destino.
    public boolean isEncontrada() {
        return encontrada;
    }

    // Devuelve la distancia total en metros de la ruta calculada.
    public int getDistanciaMetros() {
        return distanciaMetros;
    }

    // Devuelve una representación textual de la ruta, mostrando las zonas por las que pasa y la distancia total para facilitar su visualización en el menú de consola.
    public String getRutaTexto() {
        return rutaTexto;
    }
}
