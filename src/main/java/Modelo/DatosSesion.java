package Modelo; // Define el paquete al que pertenece la clase

import java.io.BufferedReader; //  para leer el archivo de forma eficiente
import java.io.BufferedWriter; // para escribir en el archivo de forma eficiente
import java.io.File;           //  para manejar archivos y verificar su existencia
import java.io.FileReader;     // para leer caracteres de un archivo
import java.io.FileWriter;     //  para escribir caracteres en un archivo
import java.io.IOException;    //  para manejar excepciones de E/S

/**
 * Maneja las tareas personales de un usuario autenticado.
 * Cada usuario tiene su propio archivo de tareas (ej. usuario1_todo.txt).
 */
public class DatosSesion {

    private final String nombreArchivo; // nombre del archivo de tareas para el usuario actual

    /**
     * Constructor para DatosSesion.
     *
     * @param usuario El nombre del usuario para el cual se gestionarán las tareas.
     */
    public DatosSesion(String usuario) {
        // asigna el nombre del archivo de tareas  <usuario>_todo.txt.
        this.nombreArchivo = "src/main/resources/" + usuario + "_todo.txt";
        crearArchivoSiNoExiste();
    }

    /**
     * Crea el archivo de tareas si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File archivo = new File(nombreArchivo); // crea un objeto File para el archivo de tareas
        // TODO: Verificar existencia del archivo y crearlo si no existe.
        if (!archivo.exists()) { // si el archivo no existe
            try {
                archivo.createNewFile(); // intenta crear un nuevo archivo
                System.out.println("Archivo de tareas creado para " + nombreArchivo);
            } catch (IOException e) {
                // Manejo de la excepción si ocurre un error al crear el archivo.
                System.err.println("Error al crear el archivo de tareas " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }

    /**
     * Escribe una nueva tarea al final del archivo.
     *
     * @param tarea Texto de la tarea.
     * @return true si se guardó correctamente, false si ocurrió un error.
     */
    public boolean escribirTarea(String tarea) {
        // try-with-resources para asegurar que FileWriter y BufferedWriter se cierren
        // true en FileWriter constructor indica modo append (añadir al final del archivo)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(tarea);   // escribe la tarea
            writer.newLine();      // añade un salto de linea dsp de la tarea
            return true;           // retorna true si la escritura fue con exito
        } catch (IOException e) {
            // la excepcion si ocurre un error al escribir en el archivo
            System.err.println("Error al escribir en el archivo de tareas " + nombreArchivo + ": " + e.getMessage());
            return false;          // Retorna false si ocurre un error
        }
    }

    /**
     * Muestra todas las tareas almacenadas en el archivo.
     */
    public void mostrarTareas() {
        System.out.println("\n--- TAREAS DE " + nombreArchivo.replace("src/main/resources/", "").replace("_todo.txt", "") + " ---");
        //  try-with-resources para asegurar que FileReader y BufferedReader se cierren
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            boolean hayTareas = false;
            // lee linea por linea y las imprime en consola
            while ((linea = reader.readLine()) != null) {
                System.out.println("- " + linea); // muestra cada tarea con un guion
                hayTareas = true;
            }
            if (!hayTareas) {
                System.out.println("(No hay tareas registradas)");
            }
        } catch (IOException e) {
            // excepcion n si ocurre un error al leer el archivo
            System.err.println("Error al leer el archivo de tareas " + nombreArchivo + ": " + e.getMessage());
        }
        System.out.println("------------------------------------");
    }
}