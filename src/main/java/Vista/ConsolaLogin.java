package Vista;

import Controlador.Login;
import Controlador.SesionActiva;
import Modelo.Usuario; //para manejar el objeto devuelto

import java.util.Scanner;

public class ConsolaLogin {

    private final Scanner scanner;
    private final Login loginControlador; // Instancia del controlador de Login

    public ConsolaLogin() {
        this.scanner = new Scanner(System.in);
        this.loginControlador = new Login(); //una instancia del controlador de Login
    }

    public void mostrarMenuPrincipal() {
        String opcion;
        do {
            System.out.println("\n--- BIENVENIDO AL SISTEMA DE TAREAS ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrar Nuevo Usuario (Solo Admin)");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    manejarLogin();
                    break;
                case "2":
                    manejarRegistro();
                    break;
                case "3":
                    System.out.println("Saliendo del sistema. ¡Hasta pronto!");
                    loginControlador.getDatosLogin().guardarUsuarios();
                    break;
                default:
                    System.out.println("Opción invalida. Por favor, ingrese 1, 2 o 3.");
                    break;
            }
        } while (!opcion.equals("3"));
        scanner.close(); // cerrar el scanner al salir del programa
    }

    private void manejarLogin() {
        System.out.print("Ingrese su nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String clave = scanner.nextLine();

        Usuario usuarioAutenticado = loginControlador.autenticar(nombreUsuario, clave);

        if (usuarioAutenticado != null) {
            // si la autenticacion es exitosa, se inicia la sesion activa
            SesionActiva sesion = new SesionActiva(usuarioAutenticado);
            sesion.menuSesion(); //el menu de la sesion activas.
            //se guardan todos los usuarios (incluidos los cambios en los perfiles)
            loginControlador.getDatosLogin().guardarUsuarios();
        } else {
            System.out.println("Inicio de sesion fallido. Intente de nuevo.");
        }
    }


    private void manejarRegistro() {
        System.out.print("Ingrese su nombre de usuario (solo 'admin' puede registrar): ");
        String adminNombre = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String adminClave = scanner.nextLine();


        Usuario adminUsuario = loginControlador.autenticar(adminNombre, adminClave);

        if (adminUsuario != null && adminUsuario.getNombre().equals("admin")) {
            System.out.println("Autenticación de administrador exitosa.");
            System.out.print("Ingrese el nombre del nuevo usuario: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Ingrese la contraseña del nuevo usuario: ");
            String nuevaClave = scanner.nextLine();
            System.out.print("Ingrese el correo del nuevo usuario: ");
            String nuevoCorreo = scanner.nextLine();


            new Modelo.GestorUsuarios().registrar(nuevoNombre, nuevaClave, nuevoCorreo);

        } else {
            System.out.println("Acceso denegado. Solo el administrador puede registrar nuevos usuarios.");
        }
    }
}