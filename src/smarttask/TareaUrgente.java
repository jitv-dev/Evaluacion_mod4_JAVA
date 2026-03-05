package smarttask;

/**
 * Clase {@code TareaUrgente} — Lección 6: Herencia y Polimorfismo.
 *
 * <p>Representa una tarea con carácter urgente que tiene una fecha límite
 * y un nivel de alerta adicional. Extiende {@link Tarea} e implementa
 * {@link Accionable}.</p>
 *
 * <p>A diferencia de {@link TareaNormal}, agrega el atributo {@code fechaLimite}
 * y sobreescribe {@code toString()} para mostrar esa información extra.</p>
 *
 * @author Javier Torres
 * @version 1.0
 */
public class TareaUrgente extends Tarea implements Accionable {

    // ─────────────────────────────────────────────────────────────────────────
    //  ATRIBUTO PROPIO — solo TareaUrgente tiene fecha límite
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Fecha límite de la tarea en formato libre (ej: "2026-03-01").
     * Es un atributo específico de TareaUrgente, no existe en Tarea.
     */
    private String fechaLimite;

    // ─────────────────────────────────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Crea una tarea urgente con fecha límite.
     *
     * @param id          identificador único
     * @param nombre      descripción de la tarea
     * @param prioridad   nivel de prioridad (siempre se recomienda "ALTA")
     * @param fechaLimite fecha límite en formato "AAAA-MM-DD"
     */
    public TareaUrgente(int id, String nombre, String prioridad, String fechaLimite) {
        super(id, nombre, prioridad); // llama al constructor de Tarea
        this.fechaLimite = fechaLimite;
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  GETTER propio
    // ─────────────────────────────────────────────────────────────────────────

    /** @return la fecha límite de esta tarea urgente */
    public String getFechaLimite() { return fechaLimite; }

    /** @param fechaLimite nueva fecha límite */
    public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }

    // ─────────────────────────────────────────────────────────────────────────
    //  @Override — método abstracto de Tarea
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     * Las tareas urgentes se etiquetan como "[URGENTE]".
     */
    @Override
    public String getEtiquetaTipo() {
        return "[URGENTE] ";
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  @Override — métodos de la interfaz Accionable
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Ejecuta la tarea urgente con un aviso especial de urgencia.
     * Si ya estaba completada, lo informa.
     */
    @Override
    public void ejecutar() {
        if (!isCompletado()) {
            setCompletado(true);
            System.out.println("  🚨 URGENTE completada: " + getNombre()
                    + " | Fecha límite era: " + fechaLimite);
        } else {
            System.out.println("  ⚠ La tarea urgente ya estaba completada: " + getNombre());
        }
    }

    /**
     * Devuelve la descripción completa incluyendo la fecha límite.
     *
     * @return String con todos los datos de la tarea urgente
     */
    @Override
    public String describir() {
        return String.format("Tarea Urgente #%d | Nombre: %-28s | Prioridad: %-5s | Límite: %-12s | Estado: %s",
                getId(), getNombre(), getPrioridad(), fechaLimite,
                isCompletado() ? "Completada" : "Pendiente");
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  @Override toString() — sobreescribir para agregar la fecha
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Sobreescribe {@code toString()} de {@link Tarea} agregando la fecha límite.
     * Esto es polimorfismo en acción: mismo método, distinto comportamiento.
     *
     * @return fila formateada con fecha límite incluida
     */
    @Override
    public String toString() {
        // Llamamos al toString() de la clase padre y le agregamos info
        return super.toString() + " Límite: " + fechaLimite + " |";
    }
}
