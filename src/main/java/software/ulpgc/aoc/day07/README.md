# Day 7: Manifold

## Descripción

El desafío consiste en simular la división de flujos (splits) y el recuento de caminos a través de una rejilla de divisores (`^`). El sistema se inicializa en una columna de partida marcada como `S` en la primera fila.
1.  **Parte 1**: Calcular cuántas divisiones de haz (splits) ocurren en total al desplazarse hacia abajo por las filas.
2.  **Parte 2**: Calcular el número total de caminos distintos posibles desde la salida `S` hasta el final de la rejilla.

## Diagramas UML

Modelo
| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day07a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day07b.png) |

I/O
<p align="center">
  <img src="../../../../../../../UML%20diagrams/uml_day07_io.png" width="400">
</p>

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Toda la lógica matemática y de propagación de caminos está encapsulada en las clases del modelo `Manifold` y `Paths`, ocultando al exterior la forma en la que se calculan las transiciones.
*   **Modularidad**: Organización limpia en paquetes específicos de lógica de negocio (`model`), permitiendo testear de forma aislada cada una de las partes.
*   **Alta cohesión**: Las clases y registros representan conceptos atómicos del problema: `Column` (coordenada horizontal), `Row` (fila con celdas), `Tile` (tipo de celda), `Grid` (matriz de celdas), `Paths` (caminos acumulados) y `Manifold` (orquestador del cálculo).
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**: Se resuelven las propagaciones por filas mediante acumulaciones declarativas usando el método `reduce` sobre los flujos de filas (`grid.rows().stream().reduce(...)`), lo que evita la necesidad de bucles de control anidados complejos y variables globales mutables.
*   **Inmutabilidad del modelo**: Las estructuras de datos principales (`Column`, `Row`, `Grid`, `Paths`, `Manifold`) se implementan como **Records** inmutables. Las transiciones de estado por cada fila devuelven nuevas instancias de `Paths` o de los estados intermedios, protegiendo al sistema contra efectos secundarios.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Column.java:L3-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Column.java#L3-L11): Modela de forma atómica el índice horizontal de la cuadrícula y sus movimientos laterales.
        *   [Row.java:L6-L35](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Row.java#L6-L35): Almacena las baldosas de una única fila y responde a consultas espaciales locales.
        *   [Paths.java:L7-L30](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Paths.java#L7-L30): Se encarga exclusivamente del cálculo matemático y transiciones de los caminos por fila.
        *   [Manifold.java:L9-L64](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Manifold.java#L9-L64): Orquesta y ejecuta de forma única la simulación general sobre la rejilla.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Grid.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/model/Grid.java#L5-L15): Su estructura bidimensional de cuadrícula permaneció completamente cerrada a la modificación, permitiendo añadir el cálculo dinámico de la Parte B mediante la creación de la clase `Paths` y agregando el método en `Manifold` sin modificar su lógica original.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtManifoldLoader.java:L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/io/TxtManifoldLoader.java#L10) y [TxtManifoldDeserializer.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/io/TxtManifoldDeserializer.java#L5): Implementan de forma limpia `ManifoldLoader` y `Deserializer<Manifold>`, respectivamente, pudiendo sustituir a sus tipos abstractos de manera transparente.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [ManifoldLoader.java:L4-L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/io/ManifoldLoader.java#L4-L6): Define únicamente el método cohesivo `load()`, impidiendo que cargadores tengan contratos hinchados de bajo nivel.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [TxtManifoldLoader.java:L10-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day07/io/TxtManifoldLoader.java#L10-L17): El cargador depende del contrato genérico `Deserializer<Manifold>` para parsear la rejilla de texto, desacoplándolo del motor de parseo específico.


## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*: Empleado en `Manifold` para realizar búsquedas, reducciones (`reduce`) y acumulaciones sobre los flujos de forma declarativa e inmutable.
*   **Good Naming**: Nombres de variables y métodos autodescriptivos como `countSplits()`, `countPaths()`, `isSplitterAt()`, `findStartColumn()`.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Uso de métodos de creación estáticos como `Grid.from()`, `Row.from()` y `Paths.initial()`.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Uso de Java Streams (`rows().stream()`, `IntStream.range()`) para recorrer y mapear la matriz espacial de forma secuencial y abstracta.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Expresiones lambda en la propagación de flujos que capturan variables locales y parámetros del método para realizar los cálculos dinámicos por fila.

## Elección de diseño: Primitivos con orElse vs Optional

En la clase `Row`, el método `findStartColumn()` utiliza el operador `orElse` para buscar la celda inicial:

*   **¿Por qué es mejor `orElse`?**
    Dado que las especificaciones del problema garantizan que siempre existirá un punto de partida `S` en el primer renglón, esta búsqueda siempre tiene éxito en condiciones de ejecución normales. Devolver un objeto directo `Column` simplifica al cliente (`Manifold.java`) que consume la columna inicial inmediatamente, evitando tener que lidiar con envoltorios `Optional<Column>` de manera innecesaria. El valor por defecto `new Column(-1)` proporciona un centinela limpio de fallo sin alterar las firmas ni requerir flujos de unboxing.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del motor de cálculo de flujos sobre la rejilla.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day07/IOTest/TxtManifoldDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/IOTest/TxtManifoldDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day07/ATest/ManifoldTest.java`](file:///c:/Users/laura/OneDrive/Desktop/AOC-2025/src/test/java/test/Day07/ATest/ManifoldTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day07/BTest/ManifoldTest.java`](file:///c:/Users/laura/OneDrive/Desktop/AOC-2025/src/test/java/test/Day07/BTest/ManifoldTest.java)

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
