package Vista;

import Controlador.Login;
import Controlador.SesionActiva;
import Modelo.DatosLogin;

import java.util.Scanner;

/**
 * Vista principal del sistema.
 * Interactúa con el usuario mediante consola.
 */
public class ConsolaLogin {

    private final Scanner scanner;
    private final DatosLogin datos;
    private final Login login;

    public ConsolaLogin() {
        this.scanner = new Scanner(System.in);
        // inicializa DatosLogin, que carga las credenciales
        this.datos = new DatosLogin();
        // inicializa Login, que usa DatosLogin para autenticar
        this.login = new Login();
    }

    /**
     * Controla el ciclo principal del menú del sistema.
     * Permite múltiples intentos de login mostrando el menú nuevamente.
     */
    public void menu() {
        String opcion;
        do {
            mostrarOpciones();
            opcion = scanner.nextLine();
            ejecutarOpcion(opcion);
        } while (!opcion.equals("2"));
        System.out.println("Saliendo del sistema. ¡Hasta pronto!");
        scanner.close();
    }

    /**
     * Muestra las opciones disponibles para el usuario
     */
    private void mostrarOpciones() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Salir");
        System.out.print("Ingrese una opción: ");
    }

    /**
     * Ejecuta la opción seleccionada por el usuario.
     *
     * @param opcion opción ingresada por el usuario
     */
    private void ejecutarOpcion(String opcion) {
        switch (opcion) {
            case "1":
                manejarLogin();
                break;
            case "2":
                break;
            default:
                System.out.println("Opción inválida. Por favor, ingrese 1 o 2.");
                break;
        }
    }

    /**
     * Solicita usuario y contraseña, y maneja la autenticación.
     * Delega la verificación de credenciales a la clase Login.
     */
    private void manejarLogin() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String contrasena = scanner.nextLine();

        // llama a login.autenticar() y muestra mensaje según resultado
        if (login.autenticar(usuario, contrasena, datos)) {
            System.out.println("¡Autenticación exitosa! Bienvenido, " + usuario + ".");
            // Si la autenticación es exitosa, inicia SesionActiva
            SesionActiva sesion = new SesionActiva(usuario);
            sesion.menuSesion(); // llama al men de la sesion activa
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos."); // Mensaje de error si falla
            // menu principal se encarga de permitir  intentos al volver al inicio del bucle
        }
    }
}