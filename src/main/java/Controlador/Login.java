package Controlador;

import Modelo.DatosLogin;
import Modelo.Usuario;
import Modelo.GestorUsuarios; //para obtener la instancia de DatosLogin

public class Login {

    private final DatosLogin datosLogin;

    public Login() {
        // obtenemos la instancia de DatosLogin desde GestorUsuarios
        this.datosLogin = new GestorUsuarios().getDatosLogin();
    }

    public Usuario autenticar(String nombreUsuario, String clave) {
        //el objeto Usuario del mapa en DatosLogin
        Usuario usuario = datosLogin.getUsuario(nombreUsuario);

        if (usuario != null) {
            //si la clave proporcionada coincide con la clave del usuario cargado
            if (usuario.getClave().equals(clave)) {
                return usuario; //autenticacion exitosa, devuelve el objeto Usuario
            } else {
                System.out.println("Contraseña incorrecta para el usuario: " + nombreUsuario);
                return null;
            }
        } else {
            System.out.println("Usuario '" + nombreUsuario + "' no encontrado.");
            return null;
        }
    }


    public DatosLogin getDatosLogin() {
        return datosLogin;
    }
}