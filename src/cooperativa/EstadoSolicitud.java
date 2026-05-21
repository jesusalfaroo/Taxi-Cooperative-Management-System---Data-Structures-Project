package cooperativa;
/** Estados posibles de una solicitud dentro del sistema. */


public enum EstadoSolicitud {
    EN_ESPERA,
    EN_ATENCION,
    CANCELADA,
    FINALIZADA;

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
