package Launcher;

import Vista.ConsolaLogin; // Importa la clase ConsolaLogin

public class Inicio {


    public static void main(String[] args) {
        ConsolaLogin consola = new ConsolaLogin(); // Crea una instancia de ConsolaLogin
        consola.mostrarMenuPrincipal(); // Inicia el menu principal de la aplicacion
    }
}