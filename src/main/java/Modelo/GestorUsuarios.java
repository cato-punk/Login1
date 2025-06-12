package Modelo;

import java.io.BufferedReader; // para la comprobacion de existencia en el registro
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;    // para la comprobacioon de existencia en el registro
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que permite registrar nuevos usuarios.
 */
public class GestorUsuarios {

    private final String archivo = "src/main/resources/login.txt"; // ruta archivo login.txt

    /**
     * Constructor por defecto. Verifica la existencia del archivo de usuarios.
     */
    public GestorUsuarios() {
        crearArchivoSiNoExiste(); // que el archivo exista
    }

    /**
     * Crea el archivo login.txt si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File file = new File(archivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Archivo de credenciales 'login.txt' creado al iniciar GestorUsuarios.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo login.txt: " + e.getMessage());
            }
        }
    }

    /**
     * Registra un nuevo usuario en el archivo login.txt.
     *
     * @param nombre nombre del usuario
     * @param clave  contraseña del usuario
     * @return true si el usuario se registró correctamente, false en caso contrario.
     */
    public boolean registrar(String nombre, String clave) {
        // Validación básica: asegura que usuario y clave no estén vacíos.
        if (nombre == null || nombre.trim().isEmpty() || clave == null || clave.trim().isEmpty()) {
            System.out.println("Error de registro: Usuario y contraseña no pueden estar vacíos.");
            return false;
        }

        // verifica que el usuario exista
        // lectura rapida para  duplicados
        if (usuarioExiste(nombre)) {
            System.out.println("Error de registro: El usuario '" + nombre + "' ya existe.");
            return false;
        }

        //  try-with-resources para asegurar que FileWriter y BufferedWriter se cierren
        // true en FileWriter constructor modo append (añadir al final del archivo)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(nombre + ";" + clave); // nuevo usuario contraseña
            writer.newLine();                    // salto de linea
            return true;
        } catch (IOException e) {
            System.err.println("Error al registrar el usuario en login.txt: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un usuario ya existe en el archivo login.txt.
     * @param nombreUsuario El nombre de usuario a verificar.
     * @return true si el usuario ya existe, false en caso contrario.
     */
    private boolean usuarioExiste(String nombreUsuario) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";", 2);
                if (partes.length == 2) {
                    // nombre de usuario se compara
                    if (partes[0].trim().equals(nombreUsuario)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar existencia de usuario en login.txt: " + e.getMessage());
            // si pasa , queda como que el usuario no existe (lo tengo que mejrrar)

        }
        return false;
    }
}