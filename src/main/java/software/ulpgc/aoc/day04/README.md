# Day 4: Printing Department

## Descripción

El desafío consiste en optimizar el acceso a rollos de papel (`@`) en una cuadrícula.
1.  **Parte 1**: Determinar rollos accesibles (menos de 4 vecinos) en estado inicial.
2.  **Parte 2**: Simulación iterativa de eliminación de rollos accesibles hasta convergencia.

El objetivo final es encontrar el número de rollos a los que se puede acceder tras la eliminación iterativa.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day04a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day04b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta la complejidad de la navegación espacial y la verificación de límites tras la clase `Diagram` y el enum `Direction`.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `a`, `b`). Esto permite aislar y desarrollar componentes por separado, facilitando la mantenibilidad.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad. `Diagram` representa el estado de la cuadrícula de forma inmutable, y `DiagramAnalyzer` calcula las reglas de accesibilidad sobre el estado actual.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. El flujo principal (Main) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**: Se utiliza el enum `Direction` (`NORTH`, `SOUTH_EAST`, etc.) para representar los vectores de movimiento de vecindad de forma clara, eliminando números mágicos y haciendo el código autoexplicativo.
*   **Inmutabilidad del modelo**: La clase `Diagram` es inmutable. El método `withClearedCoordinates` no altera el estado de la instancia existente, sino que devuelve un nuevo objeto `Diagram` con las modificaciones. Esto garantiza que cada paso de la simulación sea discreto y libre de efectos secundarios.
*   **Eliminación de la obsesión por los primitivos (Primitive Obsession)**: Se han eliminado los tipos primitivos en las firmas del modelo. En su lugar:
    *   Se utiliza el enum `Tile` (`ROLL`, `EMPTY`, `CLEARED`) en lugar de caracteres primitivos (`char`) para representar el estado de las celdas.
    *   Se utiliza el record `RollsCount` para representar el conteo acumulado de rollos en lugar de enteros planos (`int`).
    *   Tanto el acceso al mapa como los cálculos de bordes y vecindad se realizan directamente con objetos `Coordinate` en lugar de pasar variables coordinadas `row` y `col` primitivas por separado.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Diagram.java:L12-L30](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L12-L30): Se enfoca de manera única e inmutable en contener y proporcionar acceso a la estructura bidimensional del tablero de juego.
        *   [DiagramAnalyzer.java:L12-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L12-L40): Contiene exclusivamente las reglas de accesibilidad de vecinos y la simulación iterativa espacial.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [DiagramAnalyzer.java:L27-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L40): La lógica de la simulación se puede extender agregando nuevas restricciones espaciales o algoritmos de limpieza en el tablero sin necesidad de reescribir ni modificar la estructura interna de `Diagram`.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtDiagramDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/io/TxtDiagramDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Tile[]>`, siendo sustituible por cualquier otra clase de análisis de entrada sin romper la cohesión del cargador.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [DiagramLoader.java:L8-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/io/DiagramLoader.java#L8-L10): Expone un contrato muy restrictivo de un único método (`load()`), simplificando el desarrollo de cargadores específicos.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java:L18-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java#L18-L21): La ejecución en `Main` interactúa únicamente con las abstracciones `DiagramLoader` y `Deserializer<Tile[]>`, evitando acoplar el flujo central del programa a las dependencias de ficheros físicas.

    

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación que trata la computación como la evaluación de funciones matemáticas y evita cambiar datos mutables. En Java, se ve implementado a través de expresiones lambda, referencias a métodos y la API de Streams, la cual permite encadenar operaciones (como filter, map, reduce) de forma declarativa e inmutable.
    *   *Implementación*:
        *   [DiagramAnalyzer.java:L27-L31](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L31) (`findAccessibleCoordinates()`): Filtra de forma funcional todas las coordenadas del tablero a través del método `diagram.coordinates()`, reteniendo sólo aquellas que resultan ser accesibles (`filter(c -> isAccessible(diagram, c))`) y recolectándolas en una lista (`toList()`).
        *   [DiagramAnalyzer.java:L54-L58](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L54-L58) (`countAdjacentTargets()`): Procesa las 8 posibles direcciones a partir de `Direction.values()`, filtrando aquellas en las que hay un objetivo (`ROLL`) en la celda adyacente y contando su cantidad con `count()`.
        *   [Diagram.java:L18-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L18-L22) (`create()`): Usa `Stream.of(tiles)` para mapear cada fila clonándola (`map(Tile[]::clone)`) y recolectándola en un nuevo array bidimensional (`toArray(Tile[][]::new)`), preservando la inmutabilidad de la cuadrícula.
        *   [Diagram.java:L37-L41](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L37-L41) (`coordinates()`): Genera una cuadrícula declarativa de coordenadas con `IntStream.range` sobre las filas, convirtiéndola en objetos de coordenadas emparejadas con las columnas mediante `flatMap` y `mapToObj(c -> new Coordinate(r, c))`.
        *   [Diagram.java:L51-L57](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L51-L57) (`withClearedCoordinates()`): Clona la cuadrícula usando `Stream.of` y posteriormente recorre la lista de coordenadas a limpiar (`coordinates.stream()`), filtrando por límites y actualizando los valores del clon de forma secuencial y sin efectos colaterales en la instancia original.
        *   [Main.java (Parte A y B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java#L25-L26): Utiliza `diagramLines.stream().toArray(Tile[][]::new)` para convertir la colección dinámica en el array bidimensional final que representa la cuadrícula.
*   **Clases internas**: Se define el enum privado `Direction` como una clase interna en `DiagramAnalyzer` para estructurar de forma altamente cohesiva los vectores de movimiento espacial de los vecinos de una celda.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `sumAllAccessibleRolls()`, `isInBounds()`, `withClearedCoordinates()`.

## Patrones de diseño
*   **Constructores (Constructor privado)**:
    *   *Definición*: Son métodos especiales que permiten crear nuevas instancias de una clase. Su propósito es inicializar el objeto con un estado válido. Pueden ser privados para restringir y controlar la creación de objetos desde el exterior.
    *   *Implementación*: La clase `Diagram` define su constructor en [Diagram.java:L12-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L12-L16) para restringir la instanciación directa externa, garantizando el encapsulamiento de la cuadrícula.
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se define en [Diagram.java:L18-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L18-L22) con el método estático `Diagram.create()`, centralizando la instanciación e inicialización del tablero a través del clonado seguro de celdas.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Empleado a través de Java Streams en [DiagramAnalyzer.java:L55-L57](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L55-L57) (`Arrays.stream(Direction.values())`), [Diagram.java:L38-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L38-L40) (`IntStream.range`) y [Diagram.java:L53-L55](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L53-L55) (`coordinates.stream()`) para iterar colecciones y matrices sin bucles explícitos.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Se utiliza en las expresiones lambda en [DiagramAnalyzer.java:L29](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L29) (capturando `diagram`) y [DiagramAnalyzer.java:L56](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L56) (capturando `diagram` y `coordinate`) para encapsular estados inmutables en tiempo de ejecución.

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
