package smarttask;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase {@code GestorTareas} — Lección 5: Paradigma de Orientación a Objetos.
 *
 * <p>Contiene toda la lógica de negocio (CRUD) de la aplicación SmartTask.
 * Aplica el <em>Principio de Responsabilidad Única</em>: esta clase solo
 * gestiona tareas, no se ocupa de la consola ni del menú.</p>
 *
 * <p>Los métodos están divididos en dos grupos:</p>
 * <ul>
 *   <li><strong>Métodos de lógica</strong> (terminan en {@code Logica}): reciben
 *       parámetros y son fácilmente testables con JUnit.</li>
 *   <li><strong>Métodos de consola</strong>: leen Scanner y delegan en los de lógica.</li>
 * </ul>
 *
 * @author Javier Torres
 * @version 1.0
 */
public class GestorTareas {

    // ─────────────────────────────────────────────────────────────────────────
    //  ESTADO INTERNO
    //  ArrayList<Tarea> usa polimorfismo: puede guardar TareaNormal y TareaUrgente
    // ─────────────────────────────────────────────────────────────────────────

    /** Lista de tareas. Puede contener TareaNormal o TareaUrgente (polimorfismo). */
    private ArrayList<smarttask.Tarea> tareas;

    /** Contador autoincrementable para asignar IDs únicos. */
    private int contadorId;

    // ─────────────────────────────────────────────────────────────────────────
    //  CONSTRUCTORES
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Constructor principal: inicia con datos de ejemplo para demostración.
     */
    public GestorTareas() {
        this(true);
    }

    /**
     * Constructor flexible.
     * Los tests usan {@code new GestorTareas(false)} para empezar con lista vacía.
     *
     * @param cargarEjemplos {@code true} para cargar datos de ejemplo al inicio
     */
    public GestorTareas(boolean cargarEjemplos) {
        tareas     = new ArrayList<>();
        contadorId = 1;
        if (cargarEjemplos) cargarTareasEjemplo();
    }

    /** Agrega algunas tareas de ejemplo para probar la app al abrirla. */
    private void cargarTareasEjemplo() {
        agregarTareaNormalLogica("Estudiar Java fundamentos", "ALTA");
        agregarTareaNormalLogica("Leer capítulo 3 del manual", "MEDIA");
        agregarTareaUrgenteLogica("Entregar proyecto SmartTask", "ALTA", "2026-03-01");
        agregarTareaNormalLogica("Hacer ejercicios de algoritmos", "BAJA");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  LÓGICA DE NEGOCIO — métodos testables (sin Scanner)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Agrega una {@link TareaNormal} a la lista.
     *
     * @param nombre    descripción de la tarea
     * @param prioridad "ALTA", "MEDIA" o "BAJA"
     * @return la tarea creada con su ID asignado
     */
    public smarttask.TareaNormal agregarTareaNormalLogica(String nombre, String prioridad) {
        smarttask.TareaNormal nueva = new TareaNormal(contadorId++, nombre, prioridad);
        tareas.add(nueva);
        return nueva;
    }

    /**
     * Agrega una {@link TareaUrgente} a la lista.
     *
     * @param nombre      descripción de la tarea
     * @param prioridad   prioridad recomendada: "ALTA"
     * @param fechaLimite fecha límite en formato "AAAA-MM-DD"
     * @return la tarea urgente creada
     */
    public TareaUrgente agregarTareaUrgenteLogica(String nombre, String prioridad, String fechaLimite) {
        TareaUrgente nueva = new TareaUrgente(contadorId++, nombre, prioridad, fechaLimite);
        tareas.add(nueva);
        return nueva;
    }

    /**
     * Busca una tarea por su ID.
     *
     * @param id el identificador a buscar
     * @return la {@link Tarea} si existe, o {@code null} si no se encuentra
     */
    public Tarea buscarPorId(int id) {
        for (Tarea t : tareas) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    /**
     * Devuelve todas las tareas (activas y completadas).
     *
     * @return nueva lista con todas las tareas
     */
    public List<Tarea> obtenerTodas() {
        return new ArrayList<>(tareas);
    }

    /**
     * Devuelve solo las tareas cuyo estado sea pendiente.
     *
     * @return lista de tareas no completadas
     */
    public List<Tarea> obtenerPendientes() {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea t : tareas) {
            if (!t.isCompletado()) resultado.add(t);
        }
        return resultado;
    }

    /**
     * Devuelve solo las tareas completadas.
     *
     * @return lista de tareas completadas
     */
    public List<Tarea> obtenerCompletadas() {
        List<Tarea> resultado = new ArrayList<>();
        for (Tarea t : tareas) {
            if (t.isCompletado()) resultado.add(t);
        }
        return resultado;
    }

    /**
     * Marca una tarea como completada por su ID.
     *
     * @param id el identificador de la tarea
     * @return {@code true} si se marcó, {@code false} si el ID no existe o ya estaba completada
     */
    public boolean marcarComoCompletada(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null || tarea.isCompletado()) return false;
        tarea.setCompletado(true);
        return true;
    }

    /**
     * Elimina una tarea por su ID.
     *
     * @param id el identificador a eliminar
     * @return {@code true} si se eliminó, {@code false} si el ID no existe
     */
    public boolean eliminarTarea(int id) {
        Tarea tarea = buscarPorId(id);
        if (tarea == null) return false;
        tareas.remove(tarea);
        return true;
    }

    /**
     * Devuelve el total de tareas registradas.
     *
     * @return cantidad de tareas en la lista
     */
    public int getTotalTareas() {
        return tareas.size();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  MÉTODOS DE CONSOLA — para usar en MenuPrincipal
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Imprime en consola la lista de tareas recibida con cabecera formateada.
     * Si la lista está vacía, muestra un aviso.
     *
     * @param lista lista de tareas a mostrar
     * @param titulo titulo de la sección (ej: "TODAS LAS TAREAS")
     */
    public void listarTareas(List<Tarea> lista, String titulo) {
        System.out.println("\n  ══════════════════════ " + titulo + " ══════════════════════");

        if (lista.isEmpty()) {
            System.out.println("  ⚠ No hay tareas en esta categoría.");
            return;
        }

        // Cabecera de la tabla
        System.out.println("  +------+------------+------------------------------+------------+--------------+");
        System.out.println("  | ID   | TIPO       | NOMBRE                       | PRIORIDAD  | ESTADO       |");
        System.out.println("  +------+------------+------------------------------+------------+--------------+");

        // Polimorfismo en acción: cada Tarea llama a SU toString()
        // Si es TareaUrgente, mostrará la fecha; si es TareaNormal, no.
        for (Tarea t : lista) {
            System.out.println("  " + t);
        }

        System.out.println("  +------+------------+------------------------------+------------+--------------+");
        System.out.println("  Total: " + lista.size() + " tarea(s).");
    }

    /**
     * Lista todas las tareas (activas y completadas).
     */
    public void listarTodas() {
        listarTareas(obtenerTodas(), "TODAS LAS TAREAS");
    }

    /**
     * Lista solo las tareas pendientes.
     */
    public void listarPendientes() {
        listarTareas(obtenerPendientes(), "TAREAS PENDIENTES");
    }

    /**
     * Lista solo las tareas completadas.
     */
    public void listarCompletadas() {
        listarTareas(obtenerCompletadas(), "TAREAS COMPLETADAS");
    }
}
