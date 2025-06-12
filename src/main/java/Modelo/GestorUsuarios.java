package Modelo;

import java.io.BufferedWriter; // para escribir en el archivo de forma eficiente
import java.io.File;           // para manejar archivos y verificar su existencia
import java.io.FileWriter;     // para escribir caracteres en un archivo
import java.io.IOException;    // para manejar excepciones de E/S

/**
 * Registra nuevos usuarios en login.txt.
 */
public class GestorUsuarios {

    private final String archivo = "src/main/resources/login.txt"; // ruta del archivo login.txt

    /**
     * Constructor de GestorUsuarios.
     * Asegura que el archivo login.txt exista al crear una instancia.
     */
    public GestorUsuarios() {
        crearArchivoSiNoExiste();
    }

    /**
     * Crea el archivo login.txt si no existe.
     */
    private void crearArchivoSiNoExiste() {
        File file = new File(archivo); // crea un objeto File para el archivo login.txt
        // TODO: Crear archivo si no existe.
        if (!file.exists()) { //  si el archivo no existe
            try {
                file.createNewFile(); //  crear un nuevo archivo
                System.out.println("Archivo de credenciales 'login.txt' creado en " + archivo);
            } catch (IOException e) {
                // excepción si ocurre un error al crear el archivo.
                System.err.println("Error al crear el archivo login.txt: " + e.getMessage());
            }
        }
    }

    /**
     * Registra un nuevo usuario agregándolo al archivo login.txt.
     *
     * @param usuario El nombre del nuevo usuario.
     * @param clave   La contraseña del nuevo usuario.
     * @return true si el usuario se registró correctamente, false en caso contrario.
     */
    public boolean registrar(String usuario, String clave) {
        //  que usuario y clave no estén vacíos.
        if (usuario == null || usuario.trim().isEmpty() || clave == null || clave.trim().isEmpty()) {
            System.out.println("Error: Usuario y contraseña no pueden estar vacíos.");
            return false;
        }

        //a si el usuario ya existe para evitar duplicados

        DatosLogin datosLoginTemp = new DatosLogin(); // carga las credenciales actuales
        String contrasenaExistente = datosLoginTemp.obtenerContrasena(usuario);

        if (contrasenaExistente != null) {
            System.out.println("Error: El usuario '" + usuario + "' ya existe.");
            return false;
        }


        //  try-with-resources
        // true en FileWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            writer.write(usuario + ";" + clave); //  el nuevo usuario y contraseña
            writer.newLine();                    // aañade un salto de linea para el siguiente par de credenciales
            return true;                         // retorna true
        } catch (IOException e) {
            //  excepción si ocurre un error al escribir en el archivo
            System.err.println("Error al registrar el usuario en login.txt: " + e.getMessage());
            return false;                        //  false si ocurre un error
        }
    }
}
