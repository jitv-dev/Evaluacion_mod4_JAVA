package smarttask;

/**
 * Clase abstracta {@code Tarea} — Lecciones 3 y 5: Sintaxis Java y POO.
 *
 * <p>Modela una tarea genérica con encapsulamiento completo.
 * Es abstracta porque no queremos instanciar "Tarea" directamente,
 * sino usar sus subclases concretas: {@link TareaNormal} o {@link TareaUrgente}.</p>
 *
 * <p>Conceptos aplicados:</p>
 * <ul>
 *   <li><strong>Encapsulamiento</strong>: todos los atributos son {@code private}.</li>
 *   <li><strong>Abstracción</strong>: {@code abstract} obliga a las subclases a definir {@code getEtiquetaTipo()}.</li>
 *   <li><strong>Herencia</strong>: TareaNormal y TareaUrgente extienden esta clase.</li>
 * </ul>
 *
 * @author Javier Torres
 * @version 1.0
 */
public abstract class Tarea {

    // ─────────────────────────────────────────────────────────────────────────
    //  ATRIBUTOS — private = encapsulamiento
    //  Solo se acceden desde fuera a través de getters y setters.
    // ─────────────────────────────────────────────────────────────────────────

    /** Identificador único de la tarea (asignado automáticamente). */
    private int id;

    /** Nombre o descripción breve de la tarea. */
    private String nombre;

    /**
     * Prioridad de la tarea.
     * Valores válidos: {@code "ALTA"}, {@code "MEDIA"}, {@code "BAJA"}.
     */
    private String prioridad;

    /** Estado de la tarea: {@code true} = completada, {@code false} = pendiente. */
    private boolean completado;

    // ─────────────────────────────────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Crea una nueva tarea con los datos proporcionados.
     * El campo {@code completado} empieza en {@code false} siempre.
     *
     * @param id        identificador único
     * @param nombre    descripción de la tarea
     * @param prioridad nivel de prioridad ("ALTA", "MEDIA" o "BAJA")
     */
    public Tarea(int id, String nombre, String prioridad) {
        this.id         = id;
        this.nombre     = nombre;
        // toUpperCase() normaliza la prioridad: "alta" → "ALTA"
        this.prioridad  = prioridad.toUpperCase();
        this.completado = false; // toda tarea nueva empieza pendiente
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  MÉTODO ABSTRACTO — las subclases DEBEN implementarlo
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Devuelve la etiqueta del tipo de tarea para mostrarla en consola.
     * Cada subclase define su propia etiqueta.
     *
     * @return String con el tipo (ej: "[NORMAL]  " o "[URGENTE] ")
     */
    public abstract String getEtiquetaTipo();

    // ─────────────────────────────────────────────────────────────────────────
    //  GETTERS — permiten leer los atributos desde fuera
    // ─────────────────────────────────────────────────────────────────────────

    /** @return el id de la tarea */
    public int getId()            { return id; }

    /** @return el nombre de la tarea */
    public String getNombre()     { return nombre; }

    /** @return la prioridad de la tarea */
    public String getPrioridad()  { return prioridad; }

    /** @return {@code true} si la tarea está completada */
    public boolean isCompletado() { return completado; }

    // ─────────────────────────────────────────────────────────────────────────
    //  SETTERS — permiten modificar los atributos desde fuera
    // ─────────────────────────────────────────────────────────────────────────

    /** @param nombre nuevo nombre de la tarea */
    public void setNombre(String nombre)          { this.nombre    = nombre; }

    /** @param prioridad nueva prioridad ("ALTA", "MEDIA" o "BAJA") */
    public void setPrioridad(String prioridad)    { this.prioridad = prioridad.toUpperCase(); }

    /** @param completado {@code true} para marcar como completada */
    public void setCompletado(boolean completado) { this.completado = completado; }

    // ─────────────────────────────────────────────────────────────────────────
    //  toString()
    //  @Override: sobrescribe el método toString() que hereda de Object
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Representación en formato tabla de la tarea.
     * Usa {@code String.format} para alinear columnas con ancho fijo.
     *
     * @return fila formateada para mostrar en la lista de tareas
     */
    @Override
    public String toString() {
        String estado = completado ? "✅ Completada" : "⏳ Pendiente ";

        String iconoPrioridad;
        switch (prioridad) {
            case "ALTA":  iconoPrioridad = "🔴"; break;
            case "MEDIA": iconoPrioridad = "🟡"; break;
            case "BAJA":  iconoPrioridad = "🟢"; break;
            default:      iconoPrioridad = "⚪"; break;
        }

        return String.format("| %-4d | %-10s | %-28s | %s %-5s | %s |",
                id, getEtiquetaTipo(), nombre, iconoPrioridad, prioridad, estado);
    }
}