package cooperativa;

// Representa a un conductor de taxi con su información personal, zona actual, disponibilidad y tipos de servicios que puede atender. 
// Proporciona métodos para verificar su capacidad de atención y representar su información de forma legible para el menú de consola.
public class Conductor {
    private final String placa;
    private final String nombre;
    private final String cedula;
    private String zonaActual;
    private boolean disponible;
    private boolean habilitadoEstandar;
    private boolean habilitadoBaul;
    private boolean habilitadoMascotas;

    // Construye un conductor con su información básica y permisos
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

    // Devuelve la placa del conductor.
    public String getPlaca() {
        return placa;
    }

    // Devuelve el nombre completo del conductor.
    public String getNombre() {
        return nombre;
    }

    // Devuelve la cédula del conductor.
    public String getCedula() {
        return cedula;
    }

    // Devuelve la zona actual del conductor.
    public String getZonaActual() {
        return zonaActual;
    }
    
    // Cambia la zona actual del conductor.
    public void setZonaActual(String zonaActual) {
        this.zonaActual = zonaActual.trim();
    }

    // Indica si el conductor está disponible para atender solicitudes.
    public boolean isDisponible() {
        return disponible;
    }

    // Cambia el estado de disponibilidad del conductor.
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Verifica si el conductor puede atender el tipo de servicio solicitado.
    public boolean puedeAtender(TipoServicio tipo) {
        return switch (tipo) {
            case ESTANDAR -> habilitadoEstandar;
            case BAUL_O_PARRILLA -> habilitadoBaul;
            case MASCOTAS -> habilitadoMascotas;
        };
    }

    // Resume los servicios para los cuales el conductor está habilitado.
    public String serviciosHabilitados() {
        StringBuilder sb = new StringBuilder();
        if (habilitadoEstandar) sb.append("Estandar, ");
        if (habilitadoBaul) sb.append("Baul/Parrilla, ");
        if (habilitadoMascotas) sb.append("Mascotas, ");
        if (sb.length() == 0) return "Ninguno";
        return sb.substring(0, sb.length() - 2);
    }

    // Representa al conductor como una cadena legible para el menú de consola.
    @Override
    public String toString() {
        return placa + " | " + nombre + " | CC " + cedula + " | Zona: " + zonaActual + " | " + (disponible ? "Disponible" : "Ocupado") + " | Servicios: " + serviciosHabilitados();
    }
}
