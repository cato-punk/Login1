package Controlador;

import Modelo.DatosSesion;
import Modelo.GestorUsuarios;
import java.util.Scanner;


public class SesionActiva {

    private final String usuario; //  el nombre del usuario que inicia sesion
    private final Scanner scanner = new Scanner(System.in);
    private final DatosSesion datosSesion; // instancia para  las tareas del usuario
    private GestorUsuarios gestorUsuarios; // instancia para registrar usuarios (solo si es admin)

    /**
     * Constructor para SesionActiva.
     *
     * @param usuario El nombre del usuario que ha iniciado sesión.
     */
    public SesionActiva(String usuario) {
        this.usuario = usuario;
        this.datosSesion = new DatosSesion(usuario); // inicializa DatosSesion para el usuario actual
        // solo inicializa GestorUsuarios si el usuario es admin
        if (usuario.equals("admin")) {
            this.gestorUsuarios = new GestorUsuarios();
        }
    }

    /**
     * Ciclo de operaciones disponibles en sesión.
     */
    public void menuSesion() {
        String opcion;
        do {
            mostrarOpcionesSesion();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            ejecutarOpcionSesion(opcion);
        } while (!opcion.equals("4"));
        System.out.println("Cerrando sesión de " + usuario + ". ¡Hasta la próxima!");
        // no cerrar el scanner aquí,  el scanner principal en ConsolaLogin lo cerrara al salir del programa
        // si se cerrara aca, causaria un error 'NoSuchElementException' al intentar leer de nuevo desde ConsolaLogin (ya me paso)
    }

    /**
     * Muestra las opciones del menú de sesión.
     * Si el usuario es 'admin', muestra la opción de registrar nuevos usuarios.
     */
    private void mostrarOpcionesSesion() {
        System.out.println("\n--- MENÚ DE SESIÓN (" + usuario + ") ---");
        System.out.println("1. Mostrar mis tareas");
        System.out.println("2. Escribir nueva tarea");
        if (usuario.equals("admin")) { // solo muestra esta opcion si el usuario es admin
            System.out.println("3. Registrar nuevo usuario");
        }
        System.out.println("4. Cerrar sesión");
        System.out.println("----------------------------------");
    }

    /**
     * Ejecuta la opción seleccionada por el usuario en la sesión activa.
     *
     * @param opcion La opción ingresada por el usuario.
     */
    private void ejecutarOpcionSesion(String opcion) {
        switch (opcion) {
            case "1":
                datosSesion.mostrarTareas();
                break;
            case "2":
                escribirTarea();
                break;
            case "3":
                if (usuario.equals("admin")) { // permite registrar si el usuario es admin
                    registrarUsuario();
                } else {
                    System.out.println("Opción no válida. No tienes permisos para registrar usuarios.");
                }
                break;
            case "4":

                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
                break;
        }
    }

    /**
     * Pide una tarea al usuario y la delega a DatosSesion para su escritura.
     */
    private void escribirTarea() {
        System.out.print("Ingrese la nueva tarea: ");
        String tarea = scanner.nextLine();
        if (datosSesion.escribirTarea(tarea)) { //  la escritura a DatosSesion
            System.out.println("Tarea guardada correctamente.");
        } else {
            System.out.println("Error al guardar la tarea.");
        }
    }

    /**
     * Usa GestorUsuarios para registrar un nuevo usuario (solo para admin).
     */
    private void registrarUsuario() {
        if (gestorUsuarios == null) {
            System.out.println("Error interno: Gestor de usuarios no disponible.");
            return;
        }
        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
        System.out.print("Ingrese el nuevo nombre de usuario: ");
        String nuevoUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña para el nuevo usuario: ");
        String nuevaClave = scanner.nextLine();


        if (nuevoUsuario.isEmpty() || nuevaClave.isEmpty()) {
            System.out.println("Usuario y contraseña no pueden estar vacíos.");
            return;
        }

        if (gestorUsuarios.registrar(nuevoUsuario, nuevaClave)) { // el registro a GestorUsuarios
            System.out.println("Usuario '" + nuevoUsuario + "' registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el usuario. Es posible que ya exista o haya un problema con el archivo.");
        }
    }
}