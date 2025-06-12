package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List; // List para el tipo de retorno del getter

/**
 * Maneja las tareas personales de un usuario autenticado.
 * Cada usuario tiene su propio archivo de tareas (ej. usuario_todo.txt).
 */
public class DatosSesion {

    private final String nombreArchivo; // archivo de tareas para el usuario actual
    private final List<Tarea> tareas;   // lista interna de objetos Tarea

    /**
     * Constructor para DatosSesion.
     *
     * @param usuario El nombre del usuario para el cual se gestionarán las tareas.
     */
    public DatosSesion(String usuario) {
        this.nombreArchivo = "src/main/resources/" + usuario + "_todo.txt";
        this.tareas = new ArrayList<>(); // inicia la lista de tareas
        crearArchivoSiNoExiste();       //  que el archivo exista
        cargarTareas();                 // tareas existentes
    }

    /**
     * Crea el archivo de tareas si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("Archivo de tareas creado para " + nombreArchivo);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de tareas " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }

    /**
     * Carga las tareas desde el archivo al inicializar la clase.
     * Lee el archivo y crea objetos Tarea.
     */
    private void cargarTareas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    tareas.add(new Tarea(linea.trim())); // crear objeto Tarea y lo añade a la lista
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de tareas " + nombreArchivo + ": " + e.getMessage());

        }
    }

    /**
     * Guarda la lista actual de tareas en el archivo, sobrescribiendo el contenido existente.
     * Esto asegura que la lista en memoria y en disco estén sincronizadas.
     */
    private void guardarTareasEnArchivo() {
        //  false en FileWriter para sobrescribir el archivo, no para añadir.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, false))) {
            for (Tarea tarea : tareas) {
                writer.write(tarea.getDescripcion());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar tareas en el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    /**
     * Agrega una nueva tarea a la lista y la guarda en el archivo.
     *
     * @param descripcion texto de la tarea
     */
    public void agregarTarea(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            tareas.add(new Tarea(descripcion.trim())); // crear un objeto Tarea y lo añade a la lista
            guardarTareasEnArchivo(); //  toda la lista actualizada en el archivo
        } else {
            System.out.println("No se puede agregar una tarea vacía.");
        }
    }

    /**
     * Devuelve la lista de tareas.
     *
     * @return lista de objetos Tarea
     */
    public List<Tarea> getTareas() {
        return tareas;
    }

    // mostrar Tareas se fue

}