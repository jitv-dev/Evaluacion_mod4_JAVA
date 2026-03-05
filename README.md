# 🤖 SmartTask — Gestión de Tareas en Java

| | |
|---|---|
| 📅 **Fecha** | 20 de febrero de 2026 |
| 👩‍🏫 **Docente** | Sabina Romero |
| 📚 **Módulo** | 4 — Fundamentos de Programación en Java |


Aplicación de consola en Java que permite gestionar tareas personales: agregar, listar, marcar como completadas y eliminar. Aplica los 7 pasos del ABP usando POO, herencia, interfaces, JavaDoc y JUnit 5.

---

## 📁 Estructura del proyecto en IntelliJ

```
SmartTask/
└── src/
    └── smarttask/
        ├── Accionable.java        ← Interfaz (Lección 6)
        ├── Tarea.java             ← Clase abstracta (Lecciones 3 y 5)
        ├── TareaNormal.java       ← Subclase (Lección 6)
        ├── TareaUrgente.java      ← Subclase con fecha límite (Lección 6)
        ├── GestorTareas.java      ← Lógica CRUD (Lección 5)
        ├── MenuPrincipal.java     ← main() + menú Scanner (Lección 4)
        └── GestorTareasTest.java  ← Tests JUnit 5 (Lección 7)
```

---

## 🚀 Cómo importar en IntelliJ IDEA

1. **File → New → Project from Existing Sources** → selecciona la carpeta.
2. Elige **"Create project from scratch"** → tipo: Java, JDK 8 o superior.
3. Copia los archivos `.java` dentro de `src/smarttask/`.
4. Para los tests: haz clic en `GestorTareasTest.java` → IntelliJ subrayará en rojo el `import org.junit.jupiter` → haz clic → **"Add JUnit 5 to classpath"**.
5. Ejecutar la app: clic derecho sobre `MenuPrincipal` → **Run 'MenuPrincipal.main()'**.
6. Ejecutar tests: clic derecho sobre `GestorTareasTest` → **Run 'GestorTareasTest'**.

---

## 📐 Lecciones implementadas

### Lección 1 — El lenguaje Java y su entorno
Estructura base del proyecto con paquete `smarttask` y clase principal con `main()`.

### Lección 2 — Algoritmos
Los algoritmos de agregar, listar y eliminar se implementan en `GestorTareas.java` con lógica separada del menú.

### Lección 3 — Sintaxis Java
```java
// Clase Tarea con tipos primitivos y objetos
private int     id;          // tipo primitivo
private String  nombre;      // objeto
private String  prioridad;   // "ALTA", "MEDIA" o "BAJA"
private boolean completado;  // true / false

// Estructuras de control en toString()
switch (prioridad) {
    case "ALTA":  iconoPrioridad = "🔴"; break;
    case "MEDIA": iconoPrioridad = "🟡"; break;
    default:      iconoPrioridad = "🟢"; break;
}
```

### Lección 4 — Aplicaciones de consola
```java
// Menú con Scanner + do-while + switch
Scanner teclado = new Scanner(System.in);
do {
    mostrarMenu();
    opcion = leerEntero(teclado);
    switch (opcion) {
        case 1: gestor.listarTodas();      break;
        case 4: opcionAgregarNormal(...);  break;
        case 6: opcionMarcarCompletada(...); break;
        // ...
    }
} while (opcion != 0);
```

### Lección 5 — Orientación a Objetos (POO)
```java
// Encapsulamiento: atributos private + getters/setters
public class Tarea {
    private int id;
    private String nombre;
    public int getId()          { return id; }
    public void setNombre(...)  { this.nombre = nombre; }
}

// GestorTareas: responsabilidad única — solo gestiona tareas
public class GestorTareas {
    private ArrayList<Tarea> tareas;
    public TareaNormal agregarTareaNormalLogica(String nombre, String prioridad) { ... }
    public boolean marcarComoCompletada(int id) { ... }
}
```

### Lección 6 — Herencia, Polimorfismo e Interfaces

```
         «interface»
         Accionable
        /           \
   TareaNormal   TareaUrgente
        \           /
         «abstract»
           Tarea
```

```java
// Interfaz
public interface Accionable {
    void ejecutar();
    String describir();
}

// Clase abstracta
public abstract class Tarea {
    public abstract String getEtiquetaTipo(); // obliga a las subclases
}

// Herencia + implementación de interfaz
public class TareaNormal extends Tarea implements Accionable {
    @Override public String getEtiquetaTipo() { return "[NORMAL]  "; }
    @Override public void ejecutar() { ... }   // polimorfismo
}

public class TareaUrgente extends Tarea implements Accionable {
    private String fechaLimite;               // atributo propio
    @Override public String getEtiquetaTipo() { return "[URGENTE] "; }
    @Override public String toString()        { return super.toString() + " Límite: ..."; }
}
```

### Lección 7 — Pruebas unitarias con JUnit 5

`GestorTareasTest.java` contiene **26 tests** organizados con `@Nested`:

| Grupo | Tests | Qué verifica |
|---|---|---|
| 🟢 Agregar | 7 | Crear tareas normales y urgentes, IDs únicos, estado inicial |
| 🔵 Listar | 6 | obtenerTodas, obtenerPendientes, obtenerCompletadas, copia de lista |
| 🟡 Marcar | 5 | marcarComoCompletada, doble marcado, no afecta a otras |
| 🔴 Eliminar | 4 | eliminarTarea, buscar tras eliminar, no afecta a otras |
| 🟣 Polimorfismo | 5 | instanceof, getEtiquetaTipo, describir, herencia |

---

## ✅ Checklist de validación

| Criterio | Estado |
|---|---|
| Clase `Tarea` abstracta con `id`, `nombre`, `prioridad`, `completado` | ✅ |
| Encapsulamiento (`private` + getters/setters) | ✅ |
| Interfaz `Accionable` con `ejecutar()` y `describir()` | ✅ |
| `TareaNormal` y `TareaUrgente` heredan de `Tarea` | ✅ |
| `TareaNormal` y `TareaUrgente` implementan `Accionable` | ✅ |
| Clase `GestorTareas` con lógica CRUD separada | ✅ |
| Métodos `agregarTareaNormalLogica`, `marcarComoCompletada`, `eliminarTarea` | ✅ |
| Menú interactivo con `Scanner`, `do-while` y `switch` | ✅ |
| JavaDoc en todas las clases y métodos | ✅ |
| `GestorTareasTest.java` con JUnit 5 | ✅ |
| Cobertura estimada ≥ 80% del código de negocio | ✅ |
| `@Nested` para organizar tests por funcionalidad | ✅ |
| `@BeforeEach` para datos limpios en cada test | ✅ |

---

## 🧠 Conceptos Java aplicados

| Concepto | Dónde |
|---|---|
| Clase abstracta `abstract class` | `Tarea.java` |
| Interfaz `interface` | `Accionable.java` |
| Herencia `extends` | `TareaNormal`, `TareaUrgente` |
| `implements` (interfaz) | `TareaNormal`, `TareaUrgente` |
| Encapsulamiento `private` + getters/setters | `Tarea.java` |
| `super()` en constructor | `TareaNormal`, `TareaUrgente` |
| `@Override` | `getEtiquetaTipo()`, `toString()`, `ejecutar()` |
| `instanceof` | `MenuPrincipal.java`, `GestorTareasTest.java` |
| Polimorfismo | `ArrayList<Tarea>` con objetos de distintos tipos |
| `do-while` + `switch` | `MenuPrincipal.java` |
| `Scanner` con validación | `MenuPrincipal.java` |
| JavaDoc `/** */` | Todas las clases |
| JUnit 5 `@Test`, `@BeforeEach`, `@Nested` | `GestorTareasTest.java` |
