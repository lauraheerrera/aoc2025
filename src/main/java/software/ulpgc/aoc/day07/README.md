# Day 7: Manifold

## Descripción

El desafío consiste en simular la división de flujos (splits) y el recuento de caminos a través de una rejilla de divisores (`^`). El sistema se inicializa en una columna de partida marcada como `S` en la primera fila.
1.  **Parte 1**: Calcular cuántas divisiones de haz (splits) ocurren en total al desplazarse hacia abajo por las filas.
2.  **Parte 2**: Calcular el número total de caminos distintos posibles desde la salida `S` hasta el final de la rejilla.

## Diagramas UML
| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day07a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day07b.png) |


## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Toda la lógica matemática y de propagación de caminos está encapsulada en las clases del modelo `Manifold` y `Paths`, ocultando al exterior la forma en la que se calculan las transiciones.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La navegación interna de coordenadas y el estado mutable intermedio del flujo se encapsulan dentro de los records de dominio.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: Las clases y registros representan conceptos atómicos del problema: `Column` (coordenada horizontal), `Row` (fila con celdas), `Tile` (tipo de celda), `Grid` (matriz de celdas), `Paths` (caminos acumulados) y `Manifold` (orquestador del cálculo).
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io` para leer las líneas del fichero, y construye directamente el `Manifold` con esas líneas, lo que permite cambiar el formato de datos sin afectar a las clases de modelo.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: Se resuelven las propagaciones por filas mediante acumulaciones declarativas usando el método `reduce` sobre los flujos de filas (`grid.rows().stream().reduce(...)`), lo que evita la necesidad de bucles de control anidados complejos y variables globales mutables.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las estructuras de datos principales (`Column`, `Row`, `Grid`, `Paths`, `Manifold`) se implementan como **Records** inmutables. Las transiciones de estado por cada fila devuelven nuevas instancias de `Paths` o de los estados intermedios, protegiendo al sistema contra efectos secundarios.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `Manifold` se compone de una instancia de `Grid`, y a su vez `Grid` se compone de una lista de `Row`, evitando el uso de jerarquías de herencia complejas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Column.java:L3-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Column.java#L3-L11): Modela de forma atómica el índice horizontal de la cuadrícula y sus movimientos laterales.
            *   [Row.java:L6-L35](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Row.java#L6-L35): Almacena las baldosas de una única fila y responde a consultas espaciales locales.
            *   [Paths.java:L7-L30](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Paths.java#L7-L30): Se encarga exclusivamente del cálculo matemático y transiciones de los caminos por fila.
            *   [Manifold.java:L9-L64](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Manifold.java#L9-L64): Orquesta y ejecuta de forma única la simulación general sobre la rejilla.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Grid.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Grid.java#L5-L15): Su estructura bidimensional de cuadrícula permaneció completamente cerrada a la modificación, permitiendo añadir el cálculo dinámico de la Parte B mediante la creación de la clase `Paths` y agregando el método en `Manifold` sin modificar su lógica original.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes.
        *   *Implementación*:
            *   La carga del fichero se delega en la factoría genérica `LoaderFactory`, que devuelve un `TxtLoader<String>` y lee las líneas del fichero. El `Manifold` se construye directamente con la lista de líneas leídas.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   La factoría `LoaderFactory` utiliza la interfaz funcional `Function<String, T>` de Java, que expone un único método (`apply()`). No se imponen interfaces específicas de carga a cada día.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java (day07/a)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/a/Main.java): El flujo principal depende de la factoría genérica `LoaderFactory` del paquete `common.io` en lugar de un cargador concreto.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento.
    *   *Implementación*: El manifold interactúa directamente con `Grid` y `Paths`, sin requerir inspecciones profundas de las baldosas individuales de `Row`.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma basado en la composición y aplicación de funciones declarativas.
    *   *Implementación*: Empleado en `Manifold` para realizar búsquedas, reducciones (`reduce`) y acumulaciones sobre los flujos de forma declarativa e inmutable.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso, proporcionándolos desde fuera para reducir el acoplamiento.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros y significativos a clases, variables y métodos.
    *   *Implementación*: Nombres de variables y métodos autodescriptivos como `countSplits()`, `countPaths()`, `isSplitterAt()`, `findStartColumn()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Encapsula la creación de objetos mediante un método estático.
        *   *Implementación*: Uso de métodos de creación estáticos como `Grid.from()`, `Row.from()` y `Paths.initial()`.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Proporciona acceso secuencial a elementos sin exponer la estructura interna.
        *   *Implementación*: Uso de Java Streams (`rows().stream()`, `IntStream.range()`) para recorrer la matriz espacial.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Función anónima que captura variables de su contexto de creación.
        *   *Implementación*: Expresiones lambda en la propagación de flujos.

## Elección de diseño: Primitivos con orElse vs Optional

En la clase `Row`, el método `findStartColumn()` utiliza el operador `orElse` para buscar la celda inicial:

*   **¿Por qué es mejor `orElse`?**
    Dado que las especificaciones del problema garantizan que siempre existirá un punto de partida `S` en el primer renglón, esta búsqueda siempre tiene éxito en condiciones de ejecución normales. Devolver un objeto directo `Column` simplifica al cliente (`Manifold.java`) que consume la columna inicial inmediatamente, evitando tener que lidiar con envoltorios `Optional<Column>` de manera innecesaria. El valor por defecto `new Column(-1)` proporciona un centinela limpio de fallo sin alterar las firmas ni requerir flujos de unboxing.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del motor de cálculo de flujos sobre la rejilla.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day07/IOTest/TxtManifoldDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/IOTest/TxtManifoldDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day07/ATest/ManifoldTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/ATest/ManifoldTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day07/BTest/ManifoldTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/BTest/ManifoldTest.java)

### Escenarios validados

#### Deserialización (`TxtManifoldDeserializerTest`)
*   **Parseo correcto**: Comprobación del correcto parseo de la rejilla de caracteres y dimensiones.
*   **Robustez**: Excepciones correspondientes ante bloques de texto nulos o vacíos.

#### Parte A (`ATest/ManifoldTest`)
*   **Comportamiento de Columnas y Filas**: Validación de la obtención del índice, el movimiento lateral y la correcta identificación de celdas vacías, de divisor o de inicio.
*   **Comportamiento de Celdas (Tile)**: Verificación de que mapea los caracteres de forma segura y lanza `IllegalArgumentException` si detecta un caracter desconocido.
*   **Cálculo de Splits**: Verificación del número de splits de los ejemplos base y del ejemplo complejo de 15x15, esperando un resultado de `21L`.

#### Parte B (`BTest/ManifoldTest`)
*   **Propagación de Caminos (Paths)**: Validación del cálculo matemático de caminos a través de los divisores y su acumulación dinámica por fila.
*   **Conteo de Caminos del Manifold**: Confirmación de la suma total de caminos del ejemplo, esperando un valor de `40` (representado como `BigInteger`).
