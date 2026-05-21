package cooperativa;
/** Maneja la lectura segura de datos por consola. */


import java.util.Scanner;

public class Entrada {
    private final Scanner scanner = new Scanner(System.in);

    /** Lee texto desde la terminal. */
    public String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /** Lee un número entero desde la terminal, repitiendo la solicitud si hay error. */
    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = scanner.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
            }
        }
    }

    /** Lee un entero y valida que esté dentro de un rango permitido. */
    public int leerEnteroEnRango(String mensaje, int min, int max) {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.println("Debes ingresar un valor entre " + min + " y " + max + ".");
        }
    }

    /** Lee una respuesta sí/no desde la terminal. */
    public boolean leerSiNo(String mensaje) {
        while (true) {
            String r = leerTexto(mensaje + " (s/n): ").toLowerCase();
            if (r.equals("s") || r.equals("si") || r.equals("sí")) return true;
            if (r.equals("n") || r.equals("no")) return false;
            System.out.println("Responde s o n.");
        }
    }
}
