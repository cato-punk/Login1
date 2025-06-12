package Modelo;

/**
 * Clase que representa a un usuario del sistema.
 * Encapsula el nombre y la clave del usuario.
 */
public class Usuario {

    private String nombre; // priv para el nombre del usuario
    private String clave;  // priv para la clave (contraseña) del usuario

    /**
     * Constructor que inicializa los atributos del usuario.
     *
     * @param nombre nombre del usuario
     * @param clave clave del usuario
     */
    public Usuario(String nombre, String clave) {
        this.nombre = nombre; //  el nombre del usuario  lo inicializa
        this.clave = clave;   // inicializa la clave del usuario
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la clave (contraseña) del usuario.
     *
     * @return La clave del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece una nueva clave para el usuario.
     *
     * @param clave La nueva clave.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
}