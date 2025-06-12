package Controlador;

import Modelo.DatosLogin;
import Modelo.Usuario;    // la nueva clase Usuario
import java.util.List;    // List para trabajar con colecciones de usuarios

/**
 * Clase encargada de la lógica de autenticación.
 */
public class Login {

    /**
     * Verifica si las credenciales son válidas.
     * Se comunica con el paquete Modelo para obtener los datos.
     *
     * @param usuario nombre de usuario ingresado
     * @param clave   contraseña ingresada
     * @param datos   instancia de DatosLogin que contiene la lista de usuarios
     * @return Usuario autenticado si es válido, null si no
     */
    public Usuario autenticar(String usuario, String clave, DatosLogin datos) {
        // para evitar NullPointerException
        if (usuario == null || clave == null || datos == null) {
            return null;
        }

        // obtener la lista de usuarios desde DatosLogin
        List<Usuario> listaUsuarios = datos.getUsuarios();

        // Iterar sobre la lista de usuarios para buscar una coincidencia
        for (Usuario u : listaUsuarios) {
            // Comparar el nombre de usuario ignorando mayúsculas/minúsculas o espacios si es necesario,,
            // y la clave.  usa .equals() para la clave
            if (u.getNombre().equals(usuario) && u.getClave().equals(clave)) {
                return u; //  el objeto Usuario si las credenciales coinciden
            }
        }
        return null; //  null si no se encuentra ningún usuario con las credenciales
    }
}
