package Launcher;

import Vista.ConsolaLogin; // Importa la clase ConsolaLogin desde el paquete Vista

/**
 * Clase principal del sistema.
 * Contiene el método main para lanzar la aplicación.
 */
public class Inicio {

    public static void main(String[] args) {
        ConsolaLogin consola = new ConsolaLogin(); // Crea una instancia de ConsolaLogin
        consola.menu();
    }
}