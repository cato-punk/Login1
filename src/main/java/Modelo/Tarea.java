package Modelo;
public class Tarea {
    private String descripcion;
    private Prioridad prioridad; // atributo: prioridad de la tarea
    private boolean finalizada;  // nuevo atributo: estado de la tarea (finalizada o no)

    public Tarea(String descripcion, Prioridad prioridad) {
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.finalizada = false; // una tarea nueva no está finalizada
    }

    public Tarea(String descripcion, String prioridadStr, boolean finalizada) {
        this.descripcion = descripcion;
        try {
            this.prioridad = Prioridad.valueOf(prioridadStr.toUpperCase()); // Convierte String a Enum
        } catch (IllegalArgumentException e) {
            System.err.println("Prioridad inválida para la tarea '" + descripcion + "': " + prioridadStr + ". Asignando BAJA por defecto.");
            this.prioridad = Prioridad.BAJA; // Prioridad por defecto en caso de error
        }
        this.finalizada = finalizada;
    }

    // --- Getters ---

    public String getDescripcion() {
        return descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public boolean isFinalizada() { // isFinalizada() es la convención para getters de booleanos
        return finalizada;
    }

    // --- Setters / Modificadores ---

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Marca la tarea como finalizada.
     */
    public void marcarComoFinalizada() {
        this.finalizada = true;
    }

    /**
     * Marca la tarea como activa (no finalizada).
     */
    public void marcarComoActiva() {
        this.finalizada = false;
    }

    public void toggleFinalizada() {
        this.finalizada = !this.finalizada;
    }
}