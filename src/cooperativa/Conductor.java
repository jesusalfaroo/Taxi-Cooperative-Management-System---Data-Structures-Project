package cooperativa;
/** Representa a un conductor y sus permisos para atender tipos de servicio. */


public class Conductor {
    private final String placa;
    private final String nombre;
    private final String cedula;
    private String zonaActual;
    private boolean disponible;
    private boolean habilitadoEstandar;
    private boolean habilitadoBaul;
    private boolean habilitadoMascotas;

    /** Construye un conductor con su información básica y permisos. */
    public Conductor(String placa, String nombre, String cedula, String zonaActual,
                     boolean habilitadoEstandar, boolean habilitadoBaul, boolean habilitadoMascotas) {
        this.placa = placa.trim();
        this.nombre = nombre.trim();
        this.cedula = cedula.trim();
        this.zonaActual = zonaActual.trim();
        this.habilitadoEstandar = habilitadoEstandar;
        this.habilitadoBaul = habilitadoBaul;
        this.habilitadoMascotas = habilitadoMascotas;
        this.disponible = true;
    }

    public String getPlaca() {
        return placa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getZonaActual() {
        return zonaActual;
    }

    public void setZonaActual(String zonaActual) {
        this.zonaActual = zonaActual.trim();
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /** Verifica si el conductor puede atender el tipo de servicio solicitado. */
    public boolean puedeAtender(TipoServicio tipo) {
        return switch (tipo) {
            case ESTANDAR -> habilitadoEstandar;
            case BAUL_O_PARRILLA -> habilitadoBaul;
            case MASCOTAS -> habilitadoMascotas;
        };
    }

    /** Resume los servicios para los cuales el conductor está habilitado. */
    public String serviciosHabilitados() {
        StringBuilder sb = new StringBuilder();
        if (habilitadoEstandar) sb.append("Estandar, ");
        if (habilitadoBaul) sb.append("Baul/Parrilla, ");
        if (habilitadoMascotas) sb.append("Mascotas, ");
        if (sb.length() == 0) return "Ninguno";
        return sb.substring(0, sb.length() - 2);
    }

    @Override
    public String toString() {
        return placa + " | " + nombre + " | CC " + cedula + " | Zona: " + zonaActual +
                " | " + (disponible ? "Disponible" : "Ocupado") +
                " | Servicios: " + serviciosHabilitados();
    }
}
