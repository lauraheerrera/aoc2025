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

*   **Abstracción**: Se modela el problema espacial mediante entidades abstractas claras: `Column` (coordenada horizontal del rayo con operaciones de movimiento relativo `left()` y `right()`), `Tile` (tipos de obstáculos `SPLITTER`, `SPACE`), `Row` (la fila física y sus interacciones) y `Grid` (el colector bidimensional). Esto oculta las complejidades de índices a nivel de array.
*   **Encapsulamiento**: La lógica matemática de propagación de caminos (`Paths.next`), las transiciones de estados intermedios y la inversión de filas (`reversedRows()`) están totalmente encapsuladas en `Manifold` y `Paths`. La clase cliente solo invoca métodos limpios como `countSplits()` o `countPaths()`.
*   **Cohesión**: Cada clase tiene un único propósito: `Column` gestiona coordenadas y su lógica de traslación, `Row` administra una línea horizontal de la cuadrícula, `Grid` expone la estructura de la cuadrícula, `Paths` calcula la cantidad de caminos posibles mediante programación dinámica, y `Manifold` orquesta el flujo general de simulación (splits y caminos).
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: 
    - `Manifold` se compone de `Row`
    - `Row` se compone de `Tile`

*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *  [`Column.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Column.java): Modela el índice horizontal y movimientos.
        *  [`Row.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Row.java): Almacena las baldosas de una fila.
        *  [`Tile.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Tile.java): Define el comportamiento de cada celda.
        *  [`Manifold.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Manifold.java): Orquesta la estructura global.
        *  [`State.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/a/model/State.java): Simulación de splits.
        *  [`Paths.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/b/model/Paths.java): Cálculo de caminos.
        *  [`SplitterCounter.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/a/model/SplitterCounter.java): Orquesta Parte A.
        *  [`PathCounter.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/b/model/PathCounter.java): Orquesta Parte B.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   Gracias a la separación de responsabilidades, se pudo agregar la lógica de la Parte B sin modificar el código de la Parte A, simplemente creando nuevas clases que implementan la lógica existente.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [Manifold.java:L39-L56](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Manifold.java#L39-L56): La carga del fichero se delega en la factoría genérica `LoaderFactory`, que devuelve un `TxtLoader<String>` y lee las líneas del fichero. El `Manifold` se construye directamente con la lista de líneas leídas.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [LoaderFactory.java:L6-L7](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/io/LoaderFactory.java#L6-L7): La factoría `LoaderFactory` utiliza la interfaz funcional `Function<String, T>` de Java, que expone un único método (`apply()`). No se imponen interfaces específicas de carga a cada día.
        *   La factoría `LoaderFactory` utiliza la interfaz funcional `Function<String, T>` de Java, que expone un único método (`apply()`). No se imponen interfaces específicas de carga a cada día.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java (day07/a)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/a/Main.java): El flujo principal depende de la factoría genérica `LoaderFactory` del paquete `common.io` en lugar de un cargador concreto.
*   **Law of Demeter (LoD - Ley de Deméter)**: El manifold interactúa directamente con `Grid` y `Paths`, sin requerir inspecciones profundas de las baldosas individuales de `Row`.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**:
    *   Las clases principales (`Column`, `Row`, `Tile`, `Paths`, `Manifold`) se implementan como **Records** inmutables. El estado acumulado en la propagación de rayos se modela mediante el record interno `Splits` e instancias inmutables de `Paths` devueltas en cada paso recursivo, previniendo efectos secundarios no deseados.
*   **Programación funcional (con Java Streams)**:
    *   Emplea en `Manifold` para realizar búsquedas, reducciones (`reduce`) y acumulaciones sobre los flujos de forma declarativa e inmutable.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe una `Function<String, T>` como parámetro. A su vez, `Worksheet.parse()` recibe el `Deserializer<Problem>` como argumento.
*   **Genéricos**: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar el deserializador.
*   **Good Naming**: Nombres de variables y métodos autodescriptivos como `countSplits()`, `countPaths()`, `isSplitterAt()`, `findStartColumn()`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Uso de métodos de creación estáticos como `Manifold.from()`, `Row.from()`, `Column.from()`, `Tile.from()` y `Paths.initial()`.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como `i -> nextValue(current, row, new Column(i))`.
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
