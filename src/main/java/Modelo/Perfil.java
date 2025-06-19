package Modelo;

import java.time.LocalDate; // para manejar la fecha de creación
import java.time.format.DateTimeFormatter; // formatea la fecha a String


public class Perfil {

    private String correo;
    private LocalDate fechaCreacion; //  LocalDate para manejar la fecha de forma robusta
    private int tareasBajaPrioridad;
    private int tareasMediaPrioridad;
    private int tareasAltaPrioridad;


    public Perfil(String correo) {
        this.correo = correo;
        this.fechaCreacion = LocalDate.now(); // a la fecha actual
        this.tareasBajaPrioridad = 0;
        this.tareasMediaPrioridad = 0;
        this.tareasAltaPrioridad = 0;
    }

    /**
     * Constructor para cargar un Perfil existente desde un archivo.
     *
     * @param correo Correo electrónico.
     * @param fechaCreacionStr Fecha de creación en formato String (ej. "YYYY-MM-DD").
     * @param tareasBaja Cantidad de tareas de baja prioridad.
     * @param tareasMedia Cantidad de tareas de media prioridad.
     * @param tareasAlta Cantidad de tareas de alta prioridad.
     */
    public Perfil(String correo, String fechaCreacionStr, int tareasBaja, int tareasMedia, int tareasAlta) {
        this.correo = correo;

        try {
            this.fechaCreacion = LocalDate.parse(fechaCreacionStr);
        } catch (java.time.format.DateTimeParseException e) {
            System.err.println("Error al parsear la fecha de creación para el perfil: " + fechaCreacionStr + ". Usando fecha actual.");
            this.fechaCreacion = LocalDate.now(); // En caso de error, usa la fecha actual
        }
        this.tareasBajaPrioridad = tareasBaja;
        this.tareasMediaPrioridad = tareasMedia;
        this.tareasAltaPrioridad = tareasAlta;
    }

    // --- Getters ---

    public String getCorreo() {
        return correo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    // metodo para obtener la fecha de creacion como String (para guardar en archivo)
    public String getFechaCreacionAsString() {
        // Formato estándar ISO (ej. 2023-10-27)
        return fechaCreacion.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public int getTareasBajaPrioridad() {
        return tareasBajaPrioridad;
    }

    public int getTareasMediaPrioridad() {
        return tareasMediaPrioridad;
    }

    public int getTareasAltaPrioridad() {
        return tareasAltaPrioridad;
    }

    // Setters para los contadores de tareas

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // No se suele tener un setter para fechaCreacion en un perfil una vez creado,
    // pero si fuera necesario para carga, se podría añadir.
    // public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    /**
     * Incrementa el contador de tareas de la prioridad especificada.
     *
     * @param prioridad La prioridad de la tarea a incrementar.
     */
    public void incrementarContadorTarea(Prioridad prioridad) {
        switch (prioridad) {
            case BAJA:
                tareasBajaPrioridad++;
                break;
            case MEDIA:
                tareasMediaPrioridad++;
                break;
            case ALTA:
                tareasAltaPrioridad++;
                break;
        }
    }

    /**
     * Decrementa el contador de tareas de la prioridad especificada.
     * Útil si se elimina o cambia la prioridad de una tarea.
     *
     * @param prioridad La prioridad de la tarea a decrementar.
     */
    public void decrementarContadorTarea(Prioridad prioridad) {
        switch (prioridad) {
            case BAJA:
                if (tareasBajaPrioridad > 0) tareasBajaPrioridad--;
                break;
            case MEDIA:
                if (tareasMediaPrioridad > 0) tareasMediaPrioridad--;
                break;
            case ALTA:
                if (tareasAltaPrioridad > 0) tareasAltaPrioridad--;
                break;
        }
    }
}
//un usuario tiene un perfil (lo contiene)
//un perfil es de 1 usuario
//