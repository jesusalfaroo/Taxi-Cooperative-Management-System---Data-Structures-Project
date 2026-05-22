package cooperativa;

// Clase que representa una zona geográfica dentro de la ciudad, identificada por su nombre, y que se utiliza para definir las áreas de origen y destino en las solicitudes de taxi.
public class Zona {

    private final String nombre;


    // Construye una zona con el nombre proporcionado, eliminando espacios en blanco al inicio y al final para asegurar la consistencia en la representación de las zonas.
    public Zona(String nombre) {
        this.nombre = nombre.trim();
    }

    // Devuelve el nombre de la zona.
    public String getNombre() {
        return nombre;
    }

    // Representa la zona en formato legible, mostrando su nombre para facilitar su visualización en el menú de consola.
    @Override
    public String toString() {
        return nombre;
    }
}
