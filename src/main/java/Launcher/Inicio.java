package Launcher;

import Vista.ConsolaLogin; // la clase ConsolaLogin desde el paquete Vista

/**
 * Clase principal del sistema.
 * Contiene el método main para lanzar la aplicación.
 */
public class Inicio {

    public static void main(String[] args) {
        ConsolaLogin consola = new ConsolaLogin(); // na instancia de ConsolaLogin
        consola.menu(); // Llama al método menu para iniciar el flujo de la aplicación
    }
}