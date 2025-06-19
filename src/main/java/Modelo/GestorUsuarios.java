package Modelo;
public class GestorUsuarios {

    private final DatosLogin datosLogin; // Dependencia con DatosLogin

    public GestorUsuarios() {
        this.datosLogin = new DatosLogin(); //una instancia de DatosLogin
    }

    public boolean registrar(String nombre, String clave, String correo) {
        // primero verifica si el usuario ya existe
        if (datosLogin.existeUsuario(nombre)) {
            System.out.println("El nombre de usuario '" + nombre + "' ya está en uso.");
            return false;
        }

        // crea un nuevo objeto Usuario
        Usuario nuevoUsuario = new Usuario(nombre, clave, correo);

        // agrega el nuevo usuario a la lista en memoria de DatosLogin
        datosLogin.agregarNuevoUsuario(nuevoUsuario);

        datosLogin.guardarUsuarios();

        System.out.println("Usuario '" + nombre + "' registrado exitosamente.");
        return true;
    }


    public DatosLogin getDatosLogin() {
        return datosLogin;
    }
}