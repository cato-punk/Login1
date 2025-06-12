package Modelo;

import java.io.BufferedReader; //  para leer el archivo de forma eficiente
import java.io.File;           // para manejar archivos y verificar su existencia
import java.io.FileReader;     // para leer caracteres de un archivo
import java.io.IOException;    // para manejar excepciones de E/S
import java.util.HashMap;
import java.util.Map;

/**
 * Gestiona el archivo login.txt.
 * Lee y carga las credenciales desde el archivo login.txt.
 */
public class DatosLogin {

    // un HashMap para almacenar las credenciales (usuario como clave, contraseña como valor)
    private Map<String, String> credencialesMap;
    // la ruta del archivo login.txt, igual que en GestorUsuarios
    private final String archivo = "src/main/resources/login.txt";

    /**
     * Constructor que inicializa las credenciales desde el archivo.
     */
    public DatosLogin() {
        credencialesMap = new HashMap<>();
        crearArchivoSiNoExiste(); // que el archivo exista antes de intentar cargarlo
        cargarUsuarios(); // metodo para cargar usuarios al instanciar
    }

    /**
     * Crea el archivo login.txt si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File file = new File(archivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Archivo de credenciales 'login.txt' creado al iniciar DatosLogin.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo login.txt: " + e.getMessage());
            }
        }
    }

    /**
     * Lee el archivo login.txt y agrega las líneas válidas al mapa de credenciales.
     * Ignora líneas vacías o mal formateadas.
     * Cierra adecuadamente los recursos de E/S utilizando try-with-resources.
     */
    private void cargarUsuarios() {
        // try-with-resources para asegurar que BufferedReader y FileReader se cierren
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) { // lee línea por línea
                if (linea.trim().isEmpty() || !linea.contains(";")) {
                    // ignorar líneas vacias o que no contengan el (;)
                    continue;
                }
                String[] partes = linea.split(";", 2); // divide en usuario y contraseña
                if (partes.length == 2) {
                    String usuario = partes[0].trim(); // para eliminar espacios en blanco
                    String contrasena = partes[1].trim();
                    if (!usuario.isEmpty() && !contrasena.isEmpty()) {
                        credencialesMap.put(usuario, contrasena);
                    } else {
                        // ignorar si el usuario o la contraseña están vacíos después del split.
                        System.err.println("Advertencia: Usuario o contraseña vacíos en '" + archivo + "': " + linea);
                    }
                } else {
                    // ignorar lineas que no tengan la forma usuario;contraseña.
                    System.err.println("Advertencia: Formato incorrecto en '" + archivo + "': " + linea);
                }
            }
        } catch (IOException e) {
            // manejo de errores si el archivo no se encuentra o no se puede leer

            System.err.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }
    }

    /**
     * Provee un método para obtener la contraseña a partir del usuario.
     *
     * @param usuario El nombre de usuario.
     * @return La contraseña si el usuario existe, o null en caso contrario.
     */
    public String obtenerContrasena(String usuario) {
        return credencialesMap.get(usuario);
    }
}