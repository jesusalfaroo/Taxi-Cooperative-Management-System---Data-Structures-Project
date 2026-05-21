package cooperativa;
/** Implementación manual de una lista enlazada simple para almacenar entidades del sistema. */


import java.util.function.Predicate;

public class ListaEnlazada<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> cabeza;
    private int tamanio;

    /** Agrega un elemento al final de la lista. */
    public void agregar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
        tamanio++;
    }

    /** Elimina el primer elemento que cumpla la condición recibida. */
    public boolean eliminar(Predicate<T> condicion) {
        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        while (actual != null) {
            if (condicion.test(actual.dato)) {
                if (anterior == null) {
                    cabeza = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    /** Busca un elemento según una condición. */
    public T buscar(Predicate<T> condicion) {
        Nodo<T> actual = cabeza;
        while (actual != null) {
            if (condicion.test(actual.dato)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    /** Obtiene el elemento almacenado en la posición indicada. */
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    /** Devuelve la cantidad de elementos de la lista. */
    public int tamanio() {
        return tamanio;
    }

    /** Indica si la lista no tiene elementos. */
    public boolean estaVacia() {
        return tamanio == 0;
    }

    /** Vacía por completo la lista. */
    public void limpiar() {
        cabeza = null;
        tamanio = 0;
    }

    /** Muestra la lista en formato de líneas para consola. */
    public String mostrarEnLineas() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = cabeza;
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
