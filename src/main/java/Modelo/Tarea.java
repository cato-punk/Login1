package Modelo;

/**
 * Representa una tarea individual del usuario.
 * Encapsula la descripción de la tarea.
 */
public class Tarea {

    private String descripcion; // priv para lo que contiene  de la tarea

    /**
     * Constructor que inicializa la descripción de la tarea.
     *
     * @param descripcion contenido de la tarea
     */
    public Tarea(String descripcion) {
        this.descripcion = descripcion; // inicializa la descripción de la tarea
    }

    /**
     * Devuelve la descripción de la tarea.
     *
     * @return La descripción de la tarea.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una nueva descripción para la tarea.
     *
     * @param descripcion La nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}