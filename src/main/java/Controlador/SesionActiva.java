package Controlador;

import Modelo.Usuario;
import Modelo.DatosSesion;
import Modelo.GestorUsuarios; //
import java.util.Scanner;    //  para la entrada del usuari

/**
 * Representa la sesión de un usuario autenticado.
 */
public class SesionActiva {

    private final Usuario usuario; // el objeto Usuario autenticado
    private final Scanner scanner = new Scanner(System.in);
    private final DatosSesion datosSesion; // inst para manejar las tareas del usuario
    private GestorUsuarios gestorUsuarios; // inst para registrar usuarios (solo si es admin)

    /**
     * Constructor que inicializa el usuario y sus datos de sesión.
     *
     * @param usuario usuario autenticado (objeto Usuario)
     */
    public SesionActiva(Usuario usuario) {
        this.usuario = usuario; // inicializa el objeto Usuario
        // DatosSesion para el usuario actual, usando el nombre del usuario
        this.datosSesion = new DatosSesion(usuario.getNombre());

        // Solo GestorUsuarios si el usuario es admin
        if (usuario.getNombre().equals("admin")) {
            this.gestorUsuarios = new GestorUsuarios();
        }
    }

    /**
     * Muestra el menú interactivo de la sesión.
     * Controla el ciclo de operaciones disponibles en sesión.
     */
    public void menuSesion() {
        String opcion;
        do {
            mostrarOpcionesSesion();
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextLine();
            ejecutarOpcionSesion(opcion);
        } while (!opcion.equals("4"));
        System.out.println("Cerrando la sesion de " + usuario.getNombre() + ". Hasta Pronto");
    }

    /**
     * Muestra las opciones del menú de sesión.
     * Si el usuario es 'admin', muestra la opción de registrar nuevos usuarios.
     */
    private void mostrarOpcionesSesion() {
        System.out.println("\n--- MENÚ DE SESIÓN (" + usuario.getNombre() + ") ---");
        System.out.println("1. Mostrar mis tareas");
        System.out.println("2. Escribir nueva tarea");

        if (usuario.getNombre().equals("admin")) { // si el usuario es admin
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
                mostrarTareas(); // las tareas del usuario
                break;
            case "2":
                escribirTarea();
                break;
            case "3":
                if (usuario.getNombre().equals("admin")) {
                    registrarUsuario();
                } else {
                    System.out.println("Opción no valida. No tienes permisos para registrar usuarios.");
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
        String descripcionTarea = scanner.nextLine();
        // Ahora, DatosSesion tiene un método agregarTarea que toma un String
        datosSesion.agregarTarea(descripcionTarea); // la tarea a DatosSesion
        System.out.println("Tarea guardada correctamente.");
    }

    /**
     * Muestra todas las tareas del usuario.
     * Este método debería invocar a DatosSesion para obtener y mostrar las tareas.
     */
    private void mostrarTareas() {
        System.out.println("\n--- TAREAS DE " + usuario.getNombre() + " ---");
        if (datosSesion.getTareas().isEmpty()) { //  el getter de DatosSesion para obtener la lista de tareas
            System.out.println("(No hay tareas registradas)");
        } else {
            for (int i = 0; i < datosSesion.getTareas().size(); i++) {
                // a la descripción de cada objeto Tarea
                System.out.println((i + 1) + ". " + datosSesion.getTareas().get(i).getDescripcion());
            }
        }
        System.out.println("------------------------------------");
    }

    /**
     * Registra un nuevo usuario en el archivo login.txt.
     * Usa GestorUsuarios para registrar un nuevo usuario (solo para admin).
     */
    private void registrarUsuario() {
        if (gestorUsuarios == null) {
            System.out.println("Error interno: Gestor de usuarios no disponible.");
            return;
        }
        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
        System.out.print("Ingrese el nuevo nombre de usuario: ");
        String nuevoUsuarioNombre = scanner.nextLine();
        System.out.print("Ingrese la contraseña para el nuevo usuario: ");
        String nuevaClave = scanner.nextLine();

        if (nuevoUsuarioNombre.isEmpty() || nuevaClave.isEmpty()) {
            System.out.println("Usuario y contraseña no pueden estar vacíos.");
            return;
        }

        if (gestorUsuarios.registrar(nuevoUsuarioNombre, nuevaClave)) { // el registro a GestorUsuarios
            System.out.println("Usuario '" + nuevoUsuarioNombre + "' registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el usuario. Es posible que ya exista o haya un problema con el archivo.");
        }
    }
}