package Controlador;

import Modelo.DatosSesion;
import Modelo.Prioridad;
import Modelo.Tarea;
import Modelo.Usuario; // ahora importa Usuario para acceder a sus datos (nombre, perfil)
import java.util.List;
import java.util.Scanner;


public class SesionActiva {

    private final Usuario usuarioAutenticado;
    private final DatosSesion datosSesion;
    private final Scanner scanner;


    public SesionActiva(Usuario usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
        this.datosSesion = new DatosSesion(usuarioAutenticado); // pasa el objeto Usuario a DatosSesion
        this.scanner = new Scanner(System.in);
        System.out.println("¡Sesión iniciada para " + usuarioAutenticado.getNombre() + "!");
        mostrarDatosPerfil(); //  al iniciar sesion
    }


    private void mostrarDatosPerfil() {
        System.out.println("\n--- PERFIL DE USUARIO ---");
        System.out.println("Usuario: " + usuarioAutenticado.getNombre());
        System.out.println("Correo: " + usuarioAutenticado.getPerfil().getCorreo());
        System.out.println("Miembro desde: " + usuarioAutenticado.getPerfil().getFechaCreacionAsString());
        System.out.println("Tareas por Prioridad (añadidas):");
        System.out.println("  Baja: " + usuarioAutenticado.getPerfil().getTareasBajaPrioridad());
        System.out.println("  Media: " + usuarioAutenticado.getPerfil().getTareasMediaPrioridad());
        System.out.println("  Alta: " + usuarioAutenticado.getPerfil().getTareasAltaPrioridad());
        System.out.println("-------------------------");
    }


    public void menuSesion() {
        String opcion;
        do {
            mostrarOpcionesSesion();
            opcion = scanner.nextLine();
            ejecutarOpcionSesion(opcion);
        } while (!opcion.equals("6"));


        datosSesion.cerrarSesion(); //el cierre de sesion en el historial
        datosSesion.getHistorial().mostrarHistorial(); // muestra el historial al salir
        System.out.println("Sesion de " + usuarioAutenticado.getNombre() + " cerrada. Volviendo al menu principal.");
    }


    private void mostrarOpcionesSesion() {
        System.out.println("\n--- MENÚ DE TAREAS (" + usuarioAutenticado.getNombre() + ") ---");
        System.out.println("1. Agregar nueva tarea");
        System.out.println("2. Ver todas las tareas");
        System.out.println("3. Ver tareas activas");
        System.out.println("4. Ver tareas finalizadas");
        System.out.println("5. Marcar tarea como finalizada");
        System.out.println("6. Cerrar Sesion");
        System.out.print("_____Ingrese una opcion: ");
    }


    private void ejecutarOpcionSesion(String opcion) {
        switch (opcion) {
            case "1":
                manejarAgregarTarea();
                break;
            case "2":
                mostrarTareas(datosSesion.getTareas(), "TODAS LAS TAREAS");
                break;
            case "3":
                mostrarTareas(datosSesion.getTareasActivas(), "TAREAS ACTIVAS");
                break;
            case "4":
                mostrarTareas(datosSesion.getTareasFinalizadas(), "TAREAS FINALIZADAS");
                break;
            case "5":
                manejarMarcarTareaFinalizada();
                break;
            case "6":
                break;
            default:
                System.out.println("Opcion invalida. Por favor, ingrese una opcion entre 1 y 6.");
                break;
        }
    }

    private void manejarAgregarTarea() {
        System.out.print("Ingrese la descripcion de su tarea: ");
        String descripcion = scanner.nextLine();

        Prioridad prioridad = solicitarPrioridad();

        if (prioridad != null) {
            datosSesion.agregarTarea(descripcion, prioridad);
            System.out.println("Tarea agregada exitosamente con prioridad " + prioridad.name() + ".");
        } else {
            System.out.println("No se pudo agregar la tarea debido a una prioridad invalida.");
        }
    }


    private Prioridad solicitarPrioridad() {
        System.out.println("Seleccione la prioridad:");
        System.out.println("  1. BAJA");
        System.out.println("  2. MEDIA");
        System.out.println("  3. ALTA");
        System.out.print("Ingrese el numero de la prioridad: ");
        String opcionPrioridad = scanner.nextLine();

        switch (opcionPrioridad) {
            case "1":
                return Prioridad.BAJA;
            case "2":
                return Prioridad.MEDIA;
            case "3":
                return Prioridad.ALTA;
            default:
                System.out.println("Opcion de prioridad invalida. Por favor, ingrese 1, 2 o 3.");
                return null;
        }
    }


    private void mostrarTareas(List<Tarea> tareas, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas para mostrar en esta categoria.");
        } else {
            for (int i = 0; i < tareas.size(); i++) {
                Tarea tarea = tareas.get(i);
                String estado = tarea.isFinalizada() ? "[FINALIZADA]" : "[ACTIVA]";
                System.out.println((i + 1) + ". " + estado + " (Prioridad: " + tarea.getPrioridad().name() + ") - " + tarea.getDescripcion());
            }
        }
        System.out.println("-----------------------------");
    }


    private void manejarMarcarTareaFinalizada() {
        List<Tarea> todasLasTareas = datosSesion.getTareas(); //  todas las tareas para listarlas
        if (todasLasTareas.isEmpty()) {
            System.out.println("No hay tareas para marcar como finalizadas.");
            return;
        }

        mostrarTareas(todasLasTareas, "SELECCIONE TAREA A FINALIZAR");

        System.out.print("Ingrese el numero de la tarea a marcar como finalizada: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine());
            if (datosSesion.marcarTareaComoFinalizada(indice - 1)) { // Resta 1 porque el usuario ve 1-based
                System.out.println("Tarea marcada como finalizada exitosamente.");
            } else {
                // El mensaje de error específico ya lo da datosSesion.marcarTareaComoFinalizada
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Por favor, ingrese un numero.");
        }
    }

}