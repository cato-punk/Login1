package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime; // registrar la fecha y hora de los eventos
import java.time.format.DateTimeFormatter; // formatear la fecha y hora

//aqui se ven cierre e inicio de sesion
public class HistorialSesion {

    private final String nombreArchivo; //  archivo de historial para el usuario actual
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); //  para la fecha/hora


    public HistorialSesion(String usuario) {//asocia el historial con un usuario en especifico
        // La ruta del archivo
        this.nombreArchivo = "src/main/resources/" + usuario + "_historial.txt";
        crearArchivoSiNoExiste();
        registrarEvento("Inicio de sesion de usuario: " + usuario); // el inicio de sesion se registra
    }


    private void crearArchivoSiNoExiste() {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("Archivo de historial creado para " + nombreArchivo);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de historial " + nombreArchivo + ": " + e.getMessage());
            }
        }
    }


    public void registrarEvento(String evento) {
        // true en FileWriter para añadir al final del archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            String timestamp = LocalDateTime.now().format(FORMATTER);
            writer.write("[" + timestamp + "] " + evento);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al registrar evento en el historial " + nombreArchivo + ": " + e.getMessage());
        }
    }


    public void mostrarHistorial() {
        System.out.println("\n--- HISTORIAL DE SESION (" + nombreArchivo.substring(nombreArchivo.lastIndexOf("/") + 1) + ") ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            boolean historialVacio = true;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
                historialVacio = false;
            }
            if (historialVacio) {
                System.out.println("(Historial vacio)");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el historial " + nombreArchivo + ": " + e.getMessage());
        }
        System.out.println("------------------------------------");
    }
}