package cooperativa;

// Enum que representa los posibles estados de una solicitud de taxi, como en espera, en atención, cancelada o finalizada.
public enum EstadoSolicitud {
    EN_ESPERA,
    EN_ATENCION,
    CANCELADA,
    FINALIZADA;

    // Representa el estado de la solicitud en formato legible, mostrando su nombre para facilitar su visualización en el menú de consola.
    @Override
    public String toString() {
        return switch (this) {
            case EN_ESPERA -> "En espera";
            case EN_ATENCION -> "En atención";
            case CANCELADA -> "Cancelada";
            case FINALIZADA -> "Finalizada";
        };
    }
}
