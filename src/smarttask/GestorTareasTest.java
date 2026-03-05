package smarttask;

/**
 * Suite de pruebas unitarias para {@link GestorTareas} — Lección 7.
 *
 * <p>Cubre las funcionalidades:
 * agregarTarea, listarTareas, marcarComoCompletada y eliminarTarea.
 * Apunta a una cobertura mayor al 80% del código de negocio.</p>
 *
 * <p>Cada test usa {@code new GestorTareas(false)} para empezar
 * con una lista vacía y tener control total sobre los datos.</p>
 *
 * @author Javier Torres
 * @version 1.0
 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests unitarios — GestorTareas (SmartTask)")
class GestorTareasTest {

    // ─────────────────────────────────────────────────────────────────────────
    //  @BeforeEach: se ejecuta ANTES de cada test.
    //  Usamos GestorTareas(false) → lista vacía → control total.
    // ─────────────────────────────────────────────────────────────────────────
    private GestorTareas gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorTareas(false);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  🟢 AGREGAR TAREAS
    // ════════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("🟢 Agregar tareas")
    class AgregarTareasTest {

        @Test
        @DisplayName("agregarTareaNormalLogica() aumenta el total en 1")
        void testAgregarNormalAumentaTotal() {
            assertEquals(0, gestor.getTotalTareas(), "Debe empezar vacío");

            gestor.agregarTareaNormalLogica("Estudiar Java", "ALTA");

            assertEquals(1, gestor.getTotalTareas());
        }

        @Test
        @DisplayName("La tarea normal agregada se puede recuperar por su ID")
        void testAgregarNormalYRecuperar() {
            TareaNormal nueva = gestor.agregarTareaNormalLogica("Estudiar Java", "ALTA");

            Tarea encontrada = gestor.buscarPorId(nueva.getId());

            assertNotNull(encontrada, "La tarea debe encontrarse");
            assertEquals("Estudiar Java", encontrada.getNombre());
        }

        @Test
        @DisplayName("La tarea normal comienza con estado PENDIENTE (completado = false)")
        void testTareaNuevaPendiente() {
            TareaNormal nueva = gestor.agregarTareaNormalLogica("Tarea pendiente", "MEDIA");

            assertFalse(nueva.isCompletado(), "Toda tarea nueva debe empezar pendiente");
        }

        @Test
        @DisplayName("La prioridad se normaliza a mayúsculas")
        void testPrioridadNormalizada() {
            TareaNormal t = gestor.agregarTareaNormalLogica("Tarea", "alta");

            assertEquals("ALTA", t.getPrioridad(), "La prioridad debe guardarse en mayúsculas");
        }

        @Test
        @DisplayName("agregarTareaUrgenteLogica() crea una TareaUrgente con fecha límite")
        void testAgregarUrgente() {
            TareaUrgente urgente = gestor.agregarTareaUrgenteLogica("Entregar proyecto", "ALTA", "2026-03-01");

            assertNotNull(urgente);
            assertEquals("2026-03-01", urgente.getFechaLimite());
            assertEquals("[URGENTE] ",  urgente.getEtiquetaTipo());
        }

        @Test
        @DisplayName("Agregar varias tareas asigna IDs únicos a cada una")
        void testIdsUnicos() {
            TareaNormal t1 = gestor.agregarTareaNormalLogica("Tarea 1", "ALTA");
            TareaNormal t2 = gestor.agregarTareaNormalLogica("Tarea 2", "MEDIA");
            TareaNormal t3 = gestor.agregarTareaNormalLogica("Tarea 3", "BAJA");

            assertNotEquals(t1.getId(), t2.getId());
            assertNotEquals(t2.getId(), t3.getId());
            assertNotEquals(t1.getId(), t3.getId());
        }

        @Test
        @DisplayName("Agregar 3 tareas da un total de 3")
        void testAgregarVarias() {
            gestor.agregarTareaNormalLogica("T1", "ALTA");
            gestor.agregarTareaNormalLogica("T2", "MEDIA");
            gestor.agregarTareaNormalLogica("T3", "BAJA");

            assertEquals(3, gestor.getTotalTareas());
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  🔵 LISTAR TAREAS
    // ════════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("🔵 Listar tareas")
    class ListarTareasTest {

        @Test
        @DisplayName("obtenerTodas() devuelve todas las tareas agregadas")
        void testObtenerTodas() {
            gestor.agregarTareaNormalLogica("T1", "ALTA");
            gestor.agregarTareaNormalLogica("T2", "MEDIA");
            gestor.agregarTareaUrgenteLogica("TU", "ALTA", "2026-03-01");

            List<Tarea> todas = gestor.obtenerTodas();

            assertEquals(3, todas.size(), "Deben devolver las 3 tareas");
        }

        @Test
        @DisplayName("obtenerTodas() devuelve lista vacía si no hay tareas")
        void testObtenerTodasVacía() {
            List<Tarea> todas = gestor.obtenerTodas();

            assertNotNull(todas, "No debe devolver null");
            assertTrue(todas.isEmpty(), "Debe estar vacía");
        }

        @Test
        @DisplayName("obtenerPendientes() devuelve solo las tareas no completadas")
        void testObtenerPendientes() {
            TareaNormal t1 = gestor.agregarTareaNormalLogica("Pendiente", "ALTA");
            TareaNormal t2 = gestor.agregarTareaNormalLogica("Completada", "MEDIA");
            gestor.marcarComoCompletada(t2.getId());

            List<Tarea> pendientes = gestor.obtenerPendientes();

            assertEquals(1, pendientes.size());
            assertEquals(t1.getId(), pendientes.get(0).getId());
        }

        @Test
        @DisplayName("obtenerCompletadas() devuelve solo las tareas completadas")
        void testObtenerCompletadas() {
            TareaNormal t1 = gestor.agregarTareaNormalLogica("T1", "ALTA");
            gestor.agregarTareaNormalLogica("T2", "MEDIA"); // sigue pendiente
            gestor.marcarComoCompletada(t1.getId());

            List<Tarea> completadas = gestor.obtenerCompletadas();

            assertEquals(1, completadas.size());
            assertTrue(completadas.get(0).isCompletado());
        }

        @Test
        @DisplayName("buscarPorId() devuelve null para un ID inexistente")
        void testBuscarPorIdInexistente() {
            Tarea resultado = gestor.buscarPorId(999);

            assertNull(resultado, "Debe devolver null para ID que no existe");
        }

        @Test
        @DisplayName("La lista devuelta por obtenerTodas() es una copia (no afecta al gestor)")
        void testObtenerTodasEsCopia() {
            gestor.agregarTareaNormalLogica("T1", "ALTA");

            List<Tarea> copia = gestor.obtenerTodas();
            copia.clear(); // limpiamos la copia

            assertEquals(1, gestor.getTotalTareas(), "Modificar la copia no debe afectar al gestor");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  🟡 MARCAR COMO COMPLETADA
    // ════════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("🟡 Marcar como completada")
    class MarcarCompletadaTest {

        @Test
        @DisplayName("marcarComoCompletada() devuelve true y cambia el estado")
        void testMarcarExistente() {
            TareaNormal t = gestor.agregarTareaNormalLogica("Estudiar", "ALTA");
            assertFalse(t.isCompletado(), "Debe empezar pendiente");

            boolean resultado = gestor.marcarComoCompletada(t.getId());

            assertTrue(resultado, "Debe devolver true al completar");
            assertTrue(gestor.buscarPorId(t.getId()).isCompletado(), "El estado debe ser completado");
        }

        @Test
        @DisplayName("marcarComoCompletada() devuelve false para ID inexistente")
        void testMarcarInexistente() {
            boolean resultado = gestor.marcarComoCompletada(999);

            assertFalse(resultado, "Debe devolver false para ID inexistente");
        }

        @Test
        @DisplayName("marcarComoCompletada() devuelve false si ya estaba completada")
        void testMarcarYaCompletada() {
            TareaNormal t = gestor.agregarTareaNormalLogica("Tarea", "MEDIA");
            gestor.marcarComoCompletada(t.getId()); // primera vez → OK

            boolean resultado = gestor.marcarComoCompletada(t.getId()); // segunda vez

            assertFalse(resultado, "No se puede completar dos veces");
        }

        @Test
        @DisplayName("Marcar la tarea 1 no afecta el estado de la tarea 2")
        void testMarcarNoAfectaOtras() {
            TareaNormal t1 = gestor.agregarTareaNormalLogica("T1", "ALTA");
            TareaNormal t2 = gestor.agregarTareaNormalLogica("T2", "MEDIA");

            gestor.marcarComoCompletada(t1.getId());

            assertFalse(gestor.buscarPorId(t2.getId()).isCompletado(),
                    "Marcar T1 no debe afectar a T2");
        }

        @Test
        @DisplayName("Funciona con TareaUrgente también")
        void testMarcarUrgente() {
            TareaUrgente tu = gestor.agregarTareaUrgenteLogica("Entregar", "ALTA", "2026-03-01");

            boolean resultado = gestor.marcarComoCompletada(tu.getId());

            assertTrue(resultado);
            assertTrue(tu.isCompletado());
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  🔴 ELIMINAR TAREAS
    // ════════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("🔴 Eliminar tareas")
    class EliminarTareasTest {

        @Test
        @DisplayName("eliminarTarea() devuelve true y reduce el total")
        void testEliminarExistente() {
            TareaNormal t = gestor.agregarTareaNormalLogica("Para eliminar", "BAJA");
            assertEquals(1, gestor.getTotalTareas());

            boolean resultado = gestor.eliminarTarea(t.getId());

            assertTrue(resultado, "Debe devolver true al eliminar");
            assertEquals(0, gestor.getTotalTareas(), "El total debe reducirse a 0");
        }

        @Test
        @DisplayName("Tras eliminar, buscarPorId() devuelve null")
        void testEliminarYBuscar() {
            TareaNormal t = gestor.agregarTareaNormalLogica("Para eliminar", "MEDIA");
            int idEliminado = t.getId();

            gestor.eliminarTarea(idEliminado);

            assertNull(gestor.buscarPorId(idEliminado), "Debe devolver null tras eliminar");
        }

        @Test
        @DisplayName("eliminarTarea() devuelve false para ID inexistente")
        void testEliminarInexistente() {
            boolean resultado = gestor.eliminarTarea(999);

            assertFalse(resultado, "Debe devolver false para ID inexistente");
        }

        @Test
        @DisplayName("Eliminar tarea 1 no afecta a la tarea 2")
        void testEliminarNoAfectaOtras() {
            TareaNormal t1 = gestor.agregarTareaNormalLogica("T1", "ALTA");
            TareaNormal t2 = gestor.agregarTareaNormalLogica("T2", "BAJA");

            gestor.eliminarTarea(t1.getId());

            assertNotNull(gestor.buscarPorId(t2.getId()), "T2 debe seguir existiendo");
            assertEquals("T2", gestor.buscarPorId(t2.getId()).getNombre());
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  🟣 POLIMORFISMO — TareaUrgente
    // ════════════════════════════════════════════════════════════════════════
    @Nested
    @DisplayName("🟣 Polimorfismo — TareaUrgente")
    class PolimorfismoTest {

        @Test
        @DisplayName("TareaUrgente implementa la interfaz Accionable")
        void testUrgentEsAccionable() {
            TareaUrgente tu = gestor.agregarTareaUrgenteLogica("Entregar", "ALTA", "2026-03-01");

            // instanceof verifica si el objeto implementa la interfaz
            assertTrue(tu instanceof Accionable, "TareaUrgente debe implementar Accionable");
        }

        @Test
        @DisplayName("TareaNormal implementa la interfaz Accionable")
        void testNormalEsAccionable() {
            TareaNormal tn = gestor.agregarTareaNormalLogica("Estudiar", "MEDIA");

            assertTrue(tn instanceof Accionable, "TareaNormal debe implementar Accionable");
        }

        @Test
        @DisplayName("TareaUrgente y TareaNormal son subclases de Tarea")
        void testHerencia() {
            TareaUrgente tu = new TareaUrgente(1, "Urgente", "ALTA", "2026-03-01");
            TareaNormal  tn = new TareaNormal(2, "Normal", "BAJA");

            assertTrue(tu instanceof Tarea, "TareaUrgente debe ser subclase de Tarea");
            assertTrue(tn instanceof Tarea, "TareaNormal debe ser subclase de Tarea");
        }

        @Test
        @DisplayName("getEtiquetaTipo() es distinto en cada subclase (polimorfismo)")
        void testEtiquetasTipo() {
            TareaNormal  tn = gestor.agregarTareaNormalLogica("N", "MEDIA");
            TareaUrgente tu = gestor.agregarTareaUrgenteLogica("U", "ALTA", "2026-03-01");

            // Polimorfismo: mismo método, distinto resultado según el tipo real
            assertNotEquals(tn.getEtiquetaTipo(), tu.getEtiquetaTipo(),
                    "Cada subclase debe tener su propia etiqueta");
        }

        @Test
        @DisplayName("describir() de Accionable funciona para ambos tipos")
        void testDescribir() {
            TareaNormal tn  = gestor.agregarTareaNormalLogica("Estudiar", "ALTA");
            TareaUrgente tu = gestor.agregarTareaUrgenteLogica("Entregar", "ALTA", "2026-03-01");

            // Usamos la interfaz como tipo → polimorfismo
            Accionable a1 = (Accionable) tn;
            Accionable a2 = (Accionable) tu;

            assertNotNull(a1.describir());
            assertNotNull(a2.describir());
            assertTrue(a2.describir().contains("2026-03-01"),
                    "La descripción de urgente debe incluir la fecha límite");
        }
    }
}
