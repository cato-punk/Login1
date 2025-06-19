package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors; // para usar stream().collect(Collectors.toList())

public class DatosSesion {

    private final String nombreArchivoTareas; //  archivo de tareas para el usuario actual
    private final List<Tarea> tareas;         // lista interna de objetos Tarea
    private final Usuario usuarioSesion;
    private final HistorialSesion historial;  // asociacion con la clase HistorialSesion

    // delimitador usado en el archivo de tareas
    private static final String SEPARATOR = ";";

    public DatosSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
        this.nombreArchivoTareas = "src/main/resources/" + usuarioSesion.getNombre() + "_todo.txt";
        this.tareas = new ArrayList<>();
        this.historial = new HistorialSesion(usuarioSesion.getNombre()); // instancia el historial para este usuario
        crearArchivoSiNoExiste();
        cargarTareas(); // carga las tareas existentes al inicializar
    }


    private void crearArchivoSiNoExiste() {
        File archivo = new File(nombreArchivoTareas);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("archivo de tareas creado para " + nombreArchivoTareas);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de tareas " + nombreArchivoTareas + ": " + e.getMessage());
            }
        }
    }


    private void cargarTareas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivoTareas))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(SEPARATOR);
                    if (partes.length == 3) {
                        String descripcion = partes[0].trim();
                        String prioridadStr = partes[1].trim();
                        boolean finalizada = Boolean.parseBoolean(partes[2].trim());
                        tareas.add(new Tarea(descripcion, prioridadStr, finalizada));
                    } else {
                        System.err.println("Advertencia: Linea con formato invalido en archivo de tareas: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error al leer el archivo de tareas " + nombreArchivoTareas + ": " + e.getMessage());
        }
    }


    private void guardarTareasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivoTareas, false))) {
            for (Tarea tarea : tareas) {
                writer.write(tarea.getDescripcion() + SEPARATOR +
                        tarea.getPrioridad().name() + SEPARATOR + // guarda el nombre del Enum
                        tarea.isFinalizada());                     //  true o false lo guarda
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar tareas en el archivo " + nombreArchivoTareas + ": " + e.getMessage());
        }
    }


    public void agregarTarea(String descripcion, Prioridad prioridad) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            Tarea nuevaTarea = new Tarea(descripcion.trim(), prioridad);
            tareas.add(nuevaTarea);
            guardarTareasEnArchivo(); // guarda toda la lista actualizada en el archivo

            usuarioSesion.getPerfil().incrementarContadorTarea(prioridad);
            //los cambios en el Perfil deben guardarse al final de la sesion
            //se manejara en DatosLogin o en el cierre de SesionActiva
            historial.registrarEvento("Tarea añadida: '" + descripcion + "' con prioridad " + prioridad.name());
        } else {
            System.out.println("No se puede agregar una tarea vacia.");
        }
    }


    public boolean marcarTareaComoFinalizada(int indice) {
        if (indice >= 0 && indice < tareas.size()) {
            Tarea tarea = tareas.get(indice);
            if (!tarea.isFinalizada()) {
                tarea.marcarComoFinalizada();
                guardarTareasEnArchivo(); // Persiste el cambio de estado


                historial.registrarEvento("Tarea finalizada: '" + tarea.getDescripcion() + "'");
                return true;
            } else {
                System.out.println("la tarea ya estaba finalizada.");
                return false;
            }
        } else {
            System.out.println("indice de tarea invalido.");
            return false;
        }
    }

    public List<Tarea> getTareas() {
        return new ArrayList<>(tareas); // devuelve una copia para evitar modificaciones
    }

    public List<Tarea> getTareasActivas() {
        // Usa Streams API (Java 8+) para filtrar, es una forma moderna y concisa
        return tareas.stream()
                .filter(tarea -> !tarea.isFinalizada())
                .collect(Collectors.toList());
    }


    public List<Tarea> getTareasFinalizadas() {
        return tareas.stream()
                .filter(Tarea::isFinalizada) //  tarea -> tarea.isFinalizada()
                .collect(Collectors.toList());
    }


    public HistorialSesion getHistorial() {
        return historial;
    }


    public void cerrarSesion() {
        historial.registrarEvento("Cierre de sesión de usuario: " + usuarioSesion.getNombre());
        //  no se guarda el Perfil, porque la responsabilidad de guardar los usuarios (y sus perfiles)
        // res en DatosLogin o GestorUsuarios al cerrar la app o cuando se modifica el usuario
    }
}