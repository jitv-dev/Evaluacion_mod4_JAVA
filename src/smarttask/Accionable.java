package smarttask;

/**
 * Interfaz {@code Accionable} — Lección 6: Polimorfismo y principios de diseño.
 *
 * <p>Define el contrato que deben cumplir todas las clases que representan
 * una tarea operable. Cualquier clase que la implemente garantiza que tendrá
 * las operaciones {@link #ejecutar()} y {@link #describir()}, sin importar
 * su tipo concreto.</p>
 *
 * <p>Principio aplicado: <em>Programar hacia la interfaz, no hacia la implementación.</em></p>
 *
 * @author Javier Torres
 * @version 1.0
 */
public interface Accionable {

    /**
     * Ejecuta la acción principal de la tarea.
     * Cada tipo de tarea define qué significa "ejecutar" para ella.
     */
    void ejecutar();

    /**
     * Devuelve una descripción detallada y formateada de la tarea.
     *
     * @return String con la descripción completa
     */
    String describir();
}
