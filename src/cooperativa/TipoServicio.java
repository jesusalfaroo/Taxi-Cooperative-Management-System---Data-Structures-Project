package cooperativa;

// Enumeración que define los tipos de servicio disponibles para las solicitudes de taxi.
public enum TipoServicio {
    ESTANDAR,
    BAUL_O_PARRILLA,
    MASCOTAS;

    // Convierte una opción numérica en un tipo de servicio, facilitando la selección del tipo de taxi requerido por el usuario a través del menú de consola.
    public static TipoServicio fromOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> ESTANDAR;
            case 2 -> BAUL_O_PARRILLA;
            case 3 -> MASCOTAS;
            default -> null;
        };
    }

    // Representa el tipo de servicio en formato legible, mostrando su nombre para facilitar su visualización en el menú de consola.
    @Override
    public String toString() {
        return switch (this) {
            case ESTANDAR -> "Taxi estándar";
            case BAUL_O_PARRILLA -> "Taxi con baúl o parrilla";
            case MASCOTAS -> "Taxi para mascotas";
        };
    }
}
