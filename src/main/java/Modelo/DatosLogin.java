package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; // arraylist obejtos usuario
import java.util.List; // Se usa List para el tipo de retorno del getter

/**
 * Clase encargada de gestionar el acceso a los datos de login.
 * Carga los usuarios desde login.txt y los administra como objetos Usuario.
 */
public class DatosLogin {

    // lista interna de usuarios, encapsulamiento
    private final List<Usuario> usuarios;
    private final String archivo = "src/main/resources/login.txt"; // Ruta login.txt

    /**
     * Constructor que carga los usuarios desde login.txt.
     * Asegura que el archivo login.txt exista antes de intentar cargarlo.
     */
    public DatosLogin() {
        this.usuarios = new ArrayList<>(); // lista de usuario se inicializa
        crearArchivoSiNoExiste();
        cargarUsuarios();
    }

    /**
     * Crea el archivo login.txt si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File file = new File(archivo);
        if (!file.exists()) { // si el archivo no existe
            try {
                file.createNewFile();
                System.out.println("Archivo de credenciales 'login.txt' creado al iniciar DatosLogin.");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo login.txt: " + e.getMessage());
            }
        }
    }

    /**
     * Lee el archivo login.txt y agrega las líneas válidas a la lista de usuarios.
     * Ignora líneas vacías o mal formateadas.
     * Carga los pares usuario;contraseña y los encapsula en instancias de Usuario.
     */
    private void cargarUsuarios() {
        // try-with-resources para asegurar que BufferedReader y FileReader se cierren
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) { // lee línea por línea
                if (linea.trim().isEmpty() || !linea.contains(";")) {
                    // ignorar lineas vacias o con (;)
                    continue;
                }
                String[] partes = linea.split(";", 2); // divide en usuario y contraseña
                if (partes.length == 2) {
                    String nombreUsuario = partes[0].trim(); // para eliminar espacios en blanco
                    String claveUsuario = partes[1].trim();
                    if (!nombreUsuario.isEmpty() && !claveUsuario.isEmpty()) {
                        // los datos en una instancia usuario , los encapsula
                        usuarios.add(new Usuario(nombreUsuario, claveUsuario));
                    } else {
                        System.err.println("Advertencia: Usuario o contraseña vacíos en '" + archivo + "': " + linea);
                    }
                } else {
                    System.err.println("Advertencia: Formato incorrecto en '" + archivo + "': " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + archivo + ": " + e.getMessage());
        }
    }

    /**
     * Devuelve la lista de usuarios cargados.
     *
     * @return Lista de objetos Usuario.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                return u;
            }
        }
        return null; //  null si el usuario no se encuentra
    }
}