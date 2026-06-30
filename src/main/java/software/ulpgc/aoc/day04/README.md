# Day 4: Printing Department

## Descripción

El desafío consiste en optimizar el acceso a rollos de papel (`@`) en una cuadrícula.
1.  **Parte 1**: Determinar rollos accesibles (menos de 4 vecinos) en estado inicial.
2.  **Parte 2**: Simulación iterativa de eliminación de rollos accesibles hasta convergencia.

El objetivo final es encontrar el número de rollos a los que se puede acceder tras la eliminación iterativa.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day04.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan abstracciones claras del dominio: `Coordinate` representa posiciones en una cuadrícula bidimensional, `Tile` enumera los posibles estados de una celda (`ROLL`, `EMPTY`, `CLEARED`), `RollsCount` representa el número de rollos accesibles y `Diagram` define la estructura física y límites del mapa. La complejidad geométrica se maneja a nivel lógico sin exponer detalles físicos innecesarios.
*   **Encapsulamiento**: La cuadrícula bidimensional y la validación de límites de las celdas se encapsulan en `Diagram` y `DiagramStatus`. A su vez, `DiagramAnalyzer` oculta por completo las matemáticas de vecindad y la estructura del enum privado `Direction`, exponiendo únicamente la interfaz para encontrar coordenadas accesibles o simular el ciclo completo de vaciado.
*   **Cohesión**: Cada clase tiene una única responsabilidad: `Tile` representa tipos de celda, `Coordinate` representa posiciones y desplazamientos, `Diagram` representa las dimensiones e inmutabilidad estructural, `DiagramStatus` mantiene el estado variable de la cuadrícula, y `DiagramAnalyzer` se dedica únicamente al algoritmo de accesibilidad y simulación recursiva.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.


## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: La simulación en `DiagramStatus` está compuesta del record `Diagram` en lugar de extender de él, manteniendo las responsabilidades aisladas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Diagram.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java): Se enfoca de manera única e inmutable en contener y proporcionar acceso a la estructura bidimensional del tablero de juego.
        *   [DiagramAnalyzer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java): Contiene exclusivamente las reglas de accesibilidad de vecinos y la simulación iterativa espacial.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [DiagramAnalyzer.java:L27-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L40): La lógica de la simulación se puede extender agregando nuevas restricciones espaciales o algoritmos de limpieza en el tablero sin necesidad de reescribir ni modificar la estructura del modelo de `Diagram`.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtDiagramDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/io/TxtDiagramDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Tile[]>`, siendo sustituible por cualquier otra clase de análisis de entrada sin romper la cohesión del cargador.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java:L18-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java#L18-L21): La ejecución en `Main` interactúa con la factoría genérica `LoaderFactory` y la interfaz `Deserializer<Tile[]>`, evitando acoplar el flujo central del programa a las dependencias de ficheros físicas.
*   **Law of Demeter (LoD - Ley de Deméter)**:  El analizador `DiagramAnalyzer` interactúa únicamente con `Diagram` y `Coordinate`, sin navegar de forma profunda por las estructuras del array bidimensional o la representación de caracteres primitiva.
*   **Principio de mínimo compromiso**: La interfaz de `Diagram` no permite la manipulación directa o destructiva de su matriz interna, exponiendo solo consultas y métodos inmutables de copia.

## Técnicas de diseño aplicadas
*   **Inmutabilidad del modelo**: Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. El record `Diagram` es la entidad estática y completamente inmutable. El estado que cambia a lo largo de la simulación se encapsula en el record `DiagramStatus`. Cuando se eliminan coordenadas accesibles mediante `withClearedCoordinates()`, no se recrea la entidad `Diagram` original; en su lugar, se devuelve un nuevo `DiagramStatus` que hace referencia al mismo `Diagram` inicial, garantizando un flujo inmutable y previniendo efectos secundarios.
*   **Programación funcional (con Java Streams)**:
    *   [DiagramAnalyzer.java:L27-L31](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L31) (`findAccessibleCoordinates()`): Filtra de forma funcional todas las coordenadas del tablero a través del método `diagram.coordinates()`, reteniendo sólo aquellas que resultan ser accesibles (`filter(c -> isAccessible(diagram, c))`) y recolectándolas en una lista (`toList()`).
    *   [DiagramAnalyzer.java:L54-L58](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L54-L58) (`countAdjacentTargets()`): Procesa las 8 posibles direcciones a partir de `Direction.values()`, filtrando aquellas en las que hay un objetivo (`ROLL`) en la celda adyacente y contando su cantidad con `count()`.
*   **Inyección de dependencias**: Inyección de los objetos deserializadores a través del constructor del cargador.
*   **Genéricos**:  Utilización de la interfaz genérica parametrizada `Deserializer<T>`.
*   **Clases internas**: Se define el enum privado `Direction` como una clase interna estática en `DiagramAnalyzer` para estructurar de forma altamente cohesiva los vectores de movimiento espacial de los vecinos de una celda.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `sumAllAccessibleRolls()`, `isInBounds()`, `withClearedCoordinates()`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Se define en [Diagram.java:L12-L14](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L12-L14) con el método estático `Diagram.create()`, centralizando la instanciación e inicialización del tablero a través del clonado seguro de celdas. Asimismo, se define en [DiagramStatus.java:L20-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramStatus.java#L20-L22) con el método estático `DiagramStatus.initial()`, centralizando la instanciación e inicialización del estado del tablero a través del clonado seguro de celdas.
*   **Patrones funcionales**:
    *   **Closure**: Se utiliza en las expresiones lambda en `DiagramAnalyzer` para encapsular estados inmutables en tiempo de ejecución.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del análisis espacial en la cuadrícula.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day04/IOTest/TxtDiagramDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/IOTest/TxtDiagramDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)

### Escenarios validados

#### Parte A (`ATest/DiagramAnalyzerTest`)
*   **Vecindad trivial**: Caso base con objetivos aislados para confirmar la detectabilidad básica.
*   **Reglas de límite**: Pruebas con bloques densos (ej. cuadrados de 4) para verificar que la regla de "menos de 4 vecinos" filtra correctamente los elementos interiores.
*   **Validación de límites y métodos de la cuadrícula**: Verificación del tamaño (`rows()` y `cols()`), los bordes (`isInBounds()`) y el lanzamiento de excepciones al intentar acceder a coordenadas fuera de límites.

#### Parte B (`BTest/DiagramAnalyzerTest`)
*   **Eliminación limpia de coordenadas**: Validación de que `withClearedCoordinates()` marca correctamente como `Tile.CLEARED` las posiciones dadas en el tablero sin modificar la instancia original.
*   **Simulación de convergencia**: Verificación de la convergencia y número acumulado de rollos removidos con el tablero de ejemplo, esperando un valor total de `43`.
