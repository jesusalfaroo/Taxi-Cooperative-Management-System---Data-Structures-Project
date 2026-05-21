package cooperativa;
/** Implementación manual de una pila LIFO usando nodos enlazados. */


public class Pila<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> tope;
    private int tamanio;

    /** Inserta un elemento en el tope de la pila. */
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamanio++;
    }

    /** Retira y devuelve el elemento del tope de la pila. */
    public T desapilar() {
        if (tope == null) {
            return null;
        }
        T dato = tope.dato;
        tope = tope.siguiente;
        tamanio--;
        return dato;
    }

    /** Devuelve el elemento del tope sin retirarlo. */
    public T verTope() {
        return tope == null ? null : tope.dato;
    }

    /** Indica si la pila está vacía. */
    public boolean estaVacia() {
        return tope == null;
    }

    /** Devuelve el número de elementos de la pila. */
    public int tamanio() {
        return tamanio;
    }

    /** Muestra la pila en orden desde el tope hacia abajo. */
    public String mostrarEnLineas() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = tope;
        int i = 1;
        if (actual == null) {
            return "(vacío)";
        }
        while (actual != null) {
            sb.append(i).append(". ").append(actual.dato).append("\n");
            actual = actual.siguiente;
            i++;
        }
        return sb.toString();
    }
}
