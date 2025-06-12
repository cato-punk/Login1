package Vista;

import Controlador.Login;
import Controlador.SesionActiva;
import Modelo.DatosLogin;
import Modelo.Usuario; //  clase Usuario ya que Login.autenticar ahora devuelve un Usuario

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
        this.datos = new DatosLogin(); // los datos de usuarios desde login.txt
        this.login = new Login(); //  instancia de Login
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
        scanner.close(); // aqui se cierra el programa
    }

    /**
     * Muestra las opciones disponibles para el usuario.
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
                System.out.println("Opción invalida. Por favor, ingrese 1 o 2.");
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
        String usuarioStr = scanner.nextLine(); // el usuario como String
        System.out.print("Ingrese contraseña: ");
        String contrasenaStr = scanner.nextLine(); // la contraseña como String

        // Llama a login.autenticar() que ahora devuelve un objeto Usuario o null
        Usuario usuarioAutenticado = login.autenticar(usuarioStr, contrasenaStr, datos);

        if (usuarioAutenticado != null) { // si objeto Usuario no es null, la autenticacion salio bien
            System.out.println("¡Autenticación exitosa! Bienvenido, " + usuarioAutenticado.getNombre() + ".");
            // si resulta la atenticacion inicia SesionActiva con el objeto Usuario autenticado
            SesionActiva sesion = new SesionActiva(usuarioAutenticado);
            sesion.menuSesion(); //llama
        } else {
            System.out.println("Error: Usuario o contraseña incorrectos."); // error si falla
        }
    }
}