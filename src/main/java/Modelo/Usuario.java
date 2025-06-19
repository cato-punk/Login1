package Modelo;


 // ahora incluye su nombre, clave y un objeto Perfil asociado.

public class Usuario {
    private String nombre;
    private String clave;
    private Perfil perfil; //  asociacion 1:1 con la clase Perfil

    public Usuario(String nombre, String clave, String correo) {
        this.nombre = nombre;
        this.clave = clave;
        this.perfil = new Perfil(correo);
    }

    /**
     * Constructor para cargar un usuario existente (por ejemplo, desde login.txt).
     * Este constructor asumiría que el perfil se cargaría por separado o se reconstruiría.
     * Sin embargo, para mantener la cohesión de Usuario y Perfil, es mejor que este constructor
     * también reciba los datos necesarios para reconstruir el Perfil.
     *
     * @param nombre             Nombre del usuario.
     * @param clave              Contraseña del usuario.
     * @param correo             Correo del perfil.
     * @param fechaCreacionStr   Fecha de creación del perfil como String.
     * @param tareasBaja         Cantidad de tareas de baja prioridad.
     * @param tareasMedia        Cantidad de tareas de media prioridad.
     * @param tareasAlta         Cantidad de tareas de alta prioridad.
     */
    public Usuario(String nombre, String clave, String correo, String fechaCreacionStr,
                   int tareasBaja, int tareasMedia, int tareasAlta) {
        this.nombre = nombre;
        this.clave = clave;
        this.perfil = new Perfil(correo, fechaCreacionStr, tareasBaja, tareasMedia, tareasAlta); //con los datos dados
    }

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public String getClave() {
        return clave;
    }

    public Perfil getPerfil() { // nuevo getter para acceder al objeto Perfil
        return perfil;
    }

    // --- Setters ( para clave )

    public void setClave(String clave) {
        this.clave = clave;
    }


    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}