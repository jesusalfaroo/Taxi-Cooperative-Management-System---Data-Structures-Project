package cooperativa;
/** Tipos de servicio que puede solicitar un usuario y que puede atender un conductor. */


public enum TipoServicio {
    ESTANDAR,
    BAUL_O_PARRILLA,
    MASCOTAS;

    public static TipoServicio fromOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> ESTANDAR;
            case 2 -> BAUL_O_PARRILLA;
            case 3 -> MASCOTAS;
            default -> null;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case ESTANDAR -> "Taxi estándar";
            case BAUL_O_PARRILLA -> "Taxi con baúl o parrilla";
            case MASCOTAS -> "Taxi para mascotas";
        };
    }
}
