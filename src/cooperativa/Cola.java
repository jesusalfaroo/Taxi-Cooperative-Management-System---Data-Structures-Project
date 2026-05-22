package cooperativa;

// Implementación manual de una cola FIFO usando nodos enlazados.
import java.util.function.Predicate;

public class Cola<T> {

    // Nodo interno que representa cada elemento de la cola, con su dato y referencia al siguiente nodo.
    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;

        Nodo(T dato) {
            this.dato = dato;
        }
    }

    private Nodo<T> frente;
    private Nodo<T> finalCola;
    private int tamanio;

    // Agrega un elemento al final de la cola.
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (finalCola == null) {
            frente = nuevo;
            finalCola = nuevo;
        } else {
            finalCola.siguiente = nuevo;
            finalCola = nuevo;
        }
        tamanio++;
    }

    // Extrae y devuelve el elemento del frente de la cola.
    public T desencolar() {
        if (frente == null) {
            return null;
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            finalCola = null;
        }
        tamanio--;
        return dato;
    }

    // Devuelve el elemento del frente sin retirarlo.
    public T verFrente() {
        return frente == null ? null : frente.dato;
    }

    // Indica si la cola no tiene elementos.
    public boolean estaVacia() {
        return frente == null;
    }

    // Devuelve la cantidad de elementos almacenados.
    public int tamanio() {
        return tamanio;
    }

    // Elimina el primer elemento que cumpla una condición.
    public boolean eliminar(Predicate<T> condicion) {
        Nodo<T> actual = frente;
        Nodo<T> anterior = null;

        while (actual != null) {
            if (condicion.test(actual.dato)) {
                if (anterior == null) {
                    frente = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                if (actual == finalCola) {
                    finalCola = anterior;
                }
                tamanio--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    // Busca y devuelve el primer elemento que cumpla una condición.
    public T buscar(Predicate<T> condicion) {
        Nodo<T> actual = frente;
        while (actual != null) {
            if (condicion.test(actual.dato)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    // Muestra la cola de forma legible para el menú de consola.
    public String mostrarEnLineas() {
        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = frente;
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
