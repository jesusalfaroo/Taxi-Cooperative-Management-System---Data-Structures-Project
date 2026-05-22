package cooperativa;
// Representa el grafo de zonas y conexiones entre ellas, con métodos para agregar zonas, crear vías, 
// habilitar o cerrar conexiones y calcular rutas más cortas usando solo vías habilitadas.

// El grafo se implementa con una lista enlazada de nodos, donde cada nodo representa una zona y contiene una lista de conexiones hacia otras zonas.
public class GrafoZonas {

    // Clase interna que representa un nodo del grafo, con su zona y lista de conexiones hacia otras zonas.
    private static class NodoZona {
        Zona zona;
        ListaEnlazada<Conexion> conexiones = new ListaEnlazada<>();

        NodoZona(Zona zona) {
            this.zona = zona;
        }
    }

    private final ListaEnlazada<NodoZona> nodos = new ListaEnlazada<>();

    // Agrega una nueva zona al grafo si todavía no existe.
    public boolean agregarZona(String nombre) {
        if (buscarNodo(nombre) != null) {
            return false;
        }
        nodos.agregar(new NodoZona(new Zona(nombre)));
        return true;
    }

    // Verifica si una zona ya está registrada.
    public boolean existeZona(String nombre) {
        return buscarNodo(nombre) != null;
    }

    // Agrega una vía bidireccional entre dos zonas.
    public boolean agregarConexion(String origen, String destino, int distanciaMetros) {
        NodoZona nodoOrigen = buscarNodo(origen);
        NodoZona nodoDestino = buscarNodo(destino);
        if (nodoOrigen == null || nodoDestino == null || distanciaMetros <= 0 || origen.equalsIgnoreCase(destino)) {
            return false;
        }
        if (buscarConexion(origen, destino) != null) {
            return false;
        }
        Conexion ida = new Conexion(origen, destino, distanciaMetros);
        Conexion vuelta = new Conexion(destino, origen, distanciaMetros);
        nodoOrigen.conexiones.agregar(ida);
        nodoDestino.conexiones.agregar(vuelta);
        return true;
    }

    // Habilita o deshabilita una conexión existente.
    public boolean cambiarEstadoConexion(String origen, String destino, boolean activa) {
        Conexion c1 = buscarConexion(origen, destino);
        Conexion c2 = buscarConexion(destino, origen);
        if (c1 == null || c2 == null) {
            return false;
        }
        c1.setActiva(activa);
        c2.setActiva(activa);
        return true;
    }

    // Lista todas las zonas registradas en el sistema.
    public String listarZonas() {
        if (nodos.estaVacia()) {
            return "(sin zonas)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodos.tamanio(); i++) {
            NodoZona nz = nodos.obtener(i);
            sb.append(i + 1).append(". ").append(nz.zona.getNombre()).append("\n");
        }
        return sb.toString();
    }

    // Lista todas las conexiones por zona y su estado actual.
    public String listarConexiones() {
        if (nodos.estaVacia()) {
            return "(sin conexiones)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nodos.tamanio(); i++) {
            NodoZona nz = nodos.obtener(i);
            sb.append("Zona ").append(nz.zona.getNombre()).append(":\n");
            String lineas = nz.conexiones.mostrarEnLineas();
            if ("(vacío)".equals(lineas)) {
                sb.append("  (sin conexiones)\n");
            } else {
                String[] partes = lineas.split("\n");
                for (String p : partes) {
                    if (!p.isBlank()) {
                        sb.append("  ").append(p).append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    // Calcula la ruta más corta usando solo conexiones habilitadas.
    public ResultadoRuta rutaMasCorta(String origen, String destino) {
        int n = nodos.tamanio();
        int idxOrigen = indiceZona(origen);
        int idxDestino = indiceZona(destino);

        if (idxOrigen == -1 || idxDestino == -1) {
            return new ResultadoRuta(false, Integer.MAX_VALUE, "Zona inexistente");
        }
        if (idxOrigen == idxDestino) {
            return new ResultadoRuta(true, 0, origen);
        }

        // Distancias mínimas desde el origen a cada zona.
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visitado = new boolean[n];
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        dist[idxOrigen] = 0;

        // Selección iterativa del nodo no visitado con menor distancia conocida.
        for (int conteo = 0; conteo < n; conteo++) {
            int u = -1;
            int mejor = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visitado[i] && dist[i] < mejor) {
                    mejor = dist[i];
                    u = i;
                }
            }
            if (u == -1) {
                break;
            }
            visitado[u] = true;
            if (u == idxDestino) {
                break;
            }

            NodoZona nodo = nodos.obtener(u);
            // Recorre las conexiones activas del nodo actual.
            for (int j = 0; j < nodo.conexiones.tamanio(); j++) {
                Conexion c = nodo.conexiones.obtener(j);
                if (!c.isActiva()) {
                    continue;
                }
                int v = indiceZona(c.getZonaDestino());
                if (v == -1 || visitado[v]) {
                    continue;
                }
                if (dist[u] != Integer.MAX_VALUE && dist[u] + c.getDistanciaMetros() < dist[v]) {
                    dist[v] = dist[u] + c.getDistanciaMetros();
                    prev[v] = u;
                }
            }
        }

        // Si la distancia al destino sigue siendo infinito, no hay ruta habilitada.
        if (dist[idxDestino] == Integer.MAX_VALUE) {
            return new ResultadoRuta(false, Integer.MAX_VALUE, "No existe ruta habilitada");
        }

        String ruta = reconstruirRuta(prev, idxDestino);
        return new ResultadoRuta(true, dist[idxDestino], ruta);
    }

    // Busca una conexión específica entre dos zonas.
    public Conexion buscarConexion(String origen, String destino) {
        NodoZona nodo = buscarNodo(origen);
        if (nodo == null) {
            return null;
        }
        for (int i = 0; i < nodo.conexiones.tamanio(); i++) {
            Conexion c = nodo.conexiones.obtener(i);
            if (c.getZonaDestino().equalsIgnoreCase(destino)) {
                return c;
            }
        }
        return null;
    }

    // Reconstruye la ruta desde el arreglo de nodos previos generado por Dijkstra, formando una cadena legible para el menú de consola.
    private String reconstruirRuta(int[] prev, int destino) {
        int n = nodos.tamanio();
        int[] orden = new int[n];
        int contador = 0;
        int actual = destino;
        while (actual != -1) {
            orden[contador++] = actual;
            actual = prev[actual];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = contador - 1; i >= 0; i--) {
            sb.append(nodos.obtener(orden[i]).zona.getNombre());
            if (i > 0) {
                sb.append(" -> ");
            }
        }
        return sb.toString();
    }

    // Obtiene el índice de una zona en la lista de nodos, o -1 si no existe.
    private int indiceZona(String nombre) {
        for (int i = 0; i < nodos.tamanio(); i++) {
            if (nodos.obtener(i).zona.getNombre().equalsIgnoreCase(nombre.trim())) {
                return i;
            }
        }
        return -1;
    }

    // Busca un nodo por su nombre de zona, devolviendo el nodo completo o null si no se encuentra.
    private NodoZona buscarNodo(String nombre) {
        for (int i = 0; i < nodos.tamanio(); i++) {
            NodoZona nz = nodos.obtener(i);
            if (nz.zona.getNombre().equalsIgnoreCase(nombre.trim())) {
                return nz;
            }
        }
        return null;
    }
}
