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

*   **Abstracción**: Oculta la complejidad de la navegación espacial y la verificación de límites tras la clase `Diagram` y el enum `Direction`. Asimismo, los detalles de la entrada de datos se abstraen tras la interfaz `DiagramLoader`.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite aislar y desarrollar componentes por separado, facilitando la mantenibilidad.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad. `Diagram` representa el estado de la cuadrícula de forma inmutable, `DiagramAnalyzer` calcula las reglas de accesibilidad sobre el estado actual, y `TxtDiagramDeserializer` traduce las líneas de texto.
*   **Bajo acoplamiento**: La interacción entre componentes se realiza a través de contratos y abstracciones (como la interfaz `Deserializer<Tile[]>`), lo que reduce el acoplamiento directo entre la carga física de ficheros y las reglas de negocio.
*   **Código expresivo**: Se utiliza el enum `Direction` (`NORTH`, `SOUTH_EAST`, etc.) para representar los vectores de movimiento de vecindad de forma clara, eliminando números mágicos y haciendo el código autoexplicativo.
*   **Diseño por contrato**: Se formalizan los acuerdos de comportamiento y transferencia de datos a través de interfaces de entrada/salida genéricas (`Deserializer`).
*   **Inmutabilidad del modelo**: La clase `Diagram` es inmutable. El método `withClearedCoordinates` no altera el estado de la instancia existente, sino que devuelve un nuevo objeto `Diagram` con las modificaciones. Esto garantiza que cada paso de la simulación sea discreto y libre de efectos secundarios.
*   **Eliminación de la obsesión por los primitivos (Primitive Obsession)**: Se han eliminado los tipos primitivos en las firmas del dominio y lógica de negocio. En su lugar:
    *   Se utiliza el enum `Tile` (`ROLL`, `EMPTY`, `CLEARED`) en lugar de caracteres primitivos (`char`) para representar el estado de las celdas.
    *   Se utiliza el record `RollsCount` para representar el conteo acumulado de rollos en lugar de enteros planos (`int`).
    *   Tanto el acceso al mapa como los cálculos de bordes y vecindad se realizan directamente con objetos `Coordinate` en lugar de pasar variables coordinadas `row` y `col` primitivas por separado.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: `Diagram` es un contenedor de datos inmutable sobre la cuadrícula, `DiagramAnalyzer` contiene exclusivamente la lógica de análisis espacial y reglas de negocio, y `TxtDiagramDeserializer` procesa la entrada de texto.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La simulación se puede extender agregando nuevos comportamientos de limpieza de coordenadas u otros algoritmos de vecindad sin modificar la cuadrícula `Diagram`.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los subtipos deben poder reemplazar a sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*: Cualquier cargador de diagramas alternativo que implemente `DiagramLoader` puede sustituir al actual sin comprometer el flujo principal.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*: Las interfaces definidas (`DiagramLoader`, `Deserializer<T>`) son cohesivas y minimalistas, conteniendo solo los métodos indispensables para realizar sus tareas.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: La clase de entrada `Main` y la infraestructura de carga no dependen de implementaciones concretas, sino de las interfaces abstractas de IO.

## Técnicas de diseño aplicadas

*   **Inyección de dependencias**: Delegación externa del proceso de creación del cargador inyectando el deserializador como una dependencia.
*   **Genéricos**: La interfaz del núcleo `Deserializer<T>` es genérica, permitiendo que la lógica del deserializador de diagramas comparta la misma base tipada.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `sumAllAccessibleRolls()`, `isInBounds()`, `withClearedCoordinates()`.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se define el método estático de creación `Diagram.create()` que centraliza la instanciación e inicialización del tablero de la cuadrícula a partir de strings.
*   **Patrón Iterator**:
    *   *Implementación*: Uso de streams (`Arrays.stream()`, `lines.stream()`) para procesar iteraciones sobre direcciones de manera declarativa.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del análisis espacial en la cuadrícula.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day04/ATest/TxtDiagramDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/TxtDiagramDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)

### Escenarios validados

#### Deserialización (`TxtDiagramDeserializerTest`)
*   **Parseo correcto**: Confirmación de la correcta conversión de una cadena de texto en un array de `Tile[]`.
*   **Manejo de errores**: Lanzamiento de `IllegalArgumentException` ante cadenas nulas.

#### Parte A (`ATest/DiagramAnalyzerTest`)
*   **Vecindad trivial**: Caso base con objetivos aislados para confirmar la detectabilidad básica.
*   **Reglas de límite**: Pruebas con bloques densos (ej. cuadrados de 4) para verificar que la regla de "menos de 4 vecinos" filtra correctamente los elementos interiores.
*   **Validación de límites y métodos de la cuadrícula**: Verificación del tamaño (`rows()` y `cols()`), los bordes (`isInBounds()`) y el lanzamiento de excepciones al intentar acceder a coordenadas fuera de límites.

#### Parte B (`BTest/DiagramAnalyzerTest`)
*   **Eliminación limpia de coordenadas**: Validación de que `withClearedCoordinates()` marca correctamente como `Tile.CLEARED` las posiciones dadas en el tablero sin modificar la instancia original.
*   **Simulación de convergencia**: Verificación de la convergencia y número acumulado de rollos removidos con el tablero de ejemplo, esperando un valor total de `43`.
