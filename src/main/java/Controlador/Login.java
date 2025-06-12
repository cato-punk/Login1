package Controlador;

import Modelo.DatosLogin;

public class Login {

    /**
     * Verifica si el usuario y la contraseña coinciden con los datos almacenados.
     *
     * @param usuario nombre de usuario ingresado
     * @param clave   contraseña ingresada
     * @param datos   objeto DatosLogin que contiene las credenciales cargadas
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean autenticar(String usuario, String clave, DatosLogin datos) {
        if (usuario == null || clave == null || datos == null) {
            return false; // evitar NullPointerException
        }

        // los datos en DatosLogi
        String contrasenaAlmacenada = datos.obtenerContrasena(usuario);

        if (contrasenaAlmacenada != null) {
            // si la autenticación fue exitosa
            return contrasenaAlmacenada.equals(clave);
        }
        return false; // seria usuario no encontrado
    }
}