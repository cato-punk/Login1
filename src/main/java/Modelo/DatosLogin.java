package Modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatosLogin {

    private final String archivoUsuarios = "src/main/resources/login.txt";
    // almacena usuarios por su nombre
    private final Map<String, Usuario> usuarios;

    private static final String SEPARATOR = ";";

    public DatosLogin() {
        this.usuarios = new HashMap<>();
        crearArchivoSiNoExiste();
        cargarUsuarios();
    }

    private void crearArchivoSiNoExiste() {
        File archivo = new File(archivoUsuarios);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("Archivo de usuarios creado: " + archivoUsuarios);
                // Añadir un usuario admin por defecto si el archivo es nuevo
                Usuario admin = new Usuario("admin", "adminpass", "admin@sistema.com"); // Crea un nuevo Perfil para admin
                usuarios.put(admin.getNombre(), admin);
                guardarUsuarios();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
    }

    private void cargarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivoUsuarios))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(SEPARATOR);
                    if (partes.length == 7) {
                        String nombre = partes[0].trim();
                        String clave = partes[1].trim();
                        String correo = partes[2].trim();
                        String fechaCreacionStr = partes[3].trim();
                        int tareasBaja = Integer.parseInt(partes[4].trim());
                        int tareasMedia = Integer.parseInt(partes[5].trim());
                        int tareasAlta = Integer.parseInt(partes[6].trim());

                        Usuario usuario = new Usuario(nombre, clave, correo, fechaCreacionStr,
                                tareasBaja, tareasMedia, tareasAlta);
                        usuarios.put(nombre, usuario);
                    } else {
                        System.err.println("Advertencia: linea con formato invalido en login.txt: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios " + archivoUsuarios + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico en login.txt: " + e.getMessage());
        }
    }

    public void guardarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoUsuarios, false))) { // false para sobrescribir
            for (Usuario usuario : usuarios.values()) {
                Perfil perfil = usuario.getPerfil();
                writer.write(usuario.getNombre() + SEPARATOR +
                        usuario.getClave() + SEPARATOR +
                        perfil.getCorreo() + SEPARATOR +
                        perfil.getFechaCreacionAsString() + SEPARATOR + // Formato String de fecha
                        perfil.getTareasBajaPrioridad() + SEPARATOR +
                        perfil.getTareasMediaPrioridad() + SEPARATOR +
                        perfil.getTareasAltaPrioridad());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar usuarios en el archivo " + archivoUsuarios + ": " + e.getMessage());
        }
    }


    public Usuario getUsuario(String nombreUsuario) {
        return usuarios.get(nombreUsuario);
    }

    public boolean agregarNuevoUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getNombre())) {
            return false; //usuario ya existe
        }
        usuarios.put(usuario.getNombre(), usuario);
        //  debe llamar a guardarUsuarios()
        return true;
    }

    public boolean existeUsuario(String nombreUsuario) {
        return usuarios.containsKey(nombreUsuario);
    }
}