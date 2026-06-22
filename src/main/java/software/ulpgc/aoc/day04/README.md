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
    *   *Implementación*: `Diagram` es un contenedor de datos inmutable sobre la cuadrícula, y `DiagramAnalyzer` contiene exclusivamente la lógica de análisis espacial y reglas de negocio.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La simulación se puede extender agregando nuevos comportamientos de limpieza de coordenadas u otros algoritmos de vecindad sin modificar la cuadrícula `Diagram`.
    

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación que trata la computación como la evaluación de funciones matemáticas y evita cambiar datos mutables. En Java, se ve implementado a través de expresiones lambda, referencias a métodos y la API de Streams, la cual permite encadenar operaciones (como filter, map, reduce) de forma declarativa e inmutable.
*   **Clases internas**: Se define el enum privado `Direction` como una clase interna en `DiagramAnalyzer` para estructurar de forma altamente cohesiva los vectores de movimiento espacial de los vecinos de una celda.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `sumAllAccessibleRolls()`, `isInBounds()`, `withClearedCoordinates()`.

## Patrones de diseño
*   **Constructores (Constructor privado)**:
    *   *Definición*: Son métodos especiales que permiten crear nuevas instancias de una clase. Su propósito es inicializar el objeto con un estado válido. Pueden ser privados para restringir y controlar la creación de objetos desde el exterior.
    *   *Implementación*: La clase `Diagram` define un constructor privado `private Diagram(Tile[][] grid)` para garantizar que el tablero solo sea instanciado y clonado a través de su método estático de factoría `create()`.
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se define el método estático de creación `Diagram.create()` que centraliza la instanciación e inicialización del tablero de la cuadrícula a partir de strings.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Uso de streams (`Arrays.stream()`, `lines.stream()`) para procesar iteraciones sobre direcciones de manera declarativa.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Expresiones lambda en el análisis espacial y filtrado de coordenadas que capturan de forma inmutable el estado actual del tablero y las celdas analizadas.

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
