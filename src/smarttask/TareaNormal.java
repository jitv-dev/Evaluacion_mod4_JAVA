package smarttask;

/**
 * Clase {@code TareaNormal} — Lección 6: Herencia y Polimorfismo.
 *
 * <p>Representa una tarea estándar sin urgencia especial.
 * Extiende {@link Tarea} (hereda sus atributos y métodos) e
 * implementa {@link Accionable} (cumple el contrato de la interfaz).</p>
 *
 * <p>Principios aplicados:</p>
 * <ul>
 *   <li><strong>Herencia</strong>: {@code extends Tarea} hereda id, nombre, prioridad, completado.</li>
 *   <li><strong>Polimorfismo</strong>: {@code @Override} redefine comportamiento heredado.</li>
 *   <li><strong>Implementación de interfaz</strong>: {@code implements Accionable}.</li>
 * </ul>
 *
 * @author Javier Torres
 * @version 1.0
 */
public class TareaNormal extends Tarea implements Accionable {

    /**
     * Crea una tarea normal con los datos básicos.
     *
     * @param id        identificador único
     * @param nombre    descripción de la tarea
     * @param prioridad nivel de prioridad ("ALTA", "MEDIA" o "BAJA")
     */
    public TareaNormal(int id, String nombre, String prioridad) {
        // "super()" llama al constructor de la clase padre (Tarea)
        // Es OBLIGATORIO como primera línea cuando hay herencia
        super(id, nombre, prioridad);
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  @Override — implementar método abstracto de Tarea
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * {@inheritDoc}
     * Las tareas normales se etiquetan como "[NORMAL]".
     */
    @Override
    public String getEtiquetaTipo() {
        return "[NORMAL]  ";
    }

    // ─────────────────────────────────────────────────────────────────────────
    //  @Override — implementar métodos de la interfaz Accionable
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Ejecuta la tarea normal: la marca como completada si estaba pendiente.
     * Imprime el resultado en consola.
     */
    @Override
    public void ejecutar() {
        if (!isCompletado()) {
            setCompletado(true);
            System.out.println("  ✅ Tarea normal ejecutada y marcada como completada: " + getNombre());
        } else {
            System.out.println("  ⚠ La tarea ya estaba completada: " + getNombre());
        }
    }

    /**
     * Devuelve la descripción detallada de esta tarea normal.
     *
     * @return String con todos los datos de la tarea
     */
    @Override
    public String describir() {
        return String.format("Tarea Normal #%d | Nombre: %-28s | Prioridad: %-5s | Estado: %s",
                getId(), getNombre(), getPrioridad(),
                isCompletado() ? "Completada" : "Pendiente");
    }
}
