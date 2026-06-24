# Day 8: Junction Box

## Descripción

El desafío consiste en modelar y conectar una red tridimensional de cajas de conexiones (`JunctionBox`) en un espacio tridimensional. Cada caja tiene coordenadas enteras `(x, y, z)`. Las conexiones posibles entre dos cajas se definen por su distancia euclídea al cuadrado.
1.  **Parte 1**: Conectar las cajas utilizando el algoritmo de Kruskal (Kruskal's MST algorithm) / estructura de conjuntos disjuntos (Disjoint Set) y, tras aplicar las primeras 1000 conexiones más cortas, calcular el producto del tamaño de los 3 componentes conexos (circuitos) más grandes.
2.  **Parte 2**: Determinar cuál es la última conexión que une todos los nodos en una única red conectada (árbol de expansión mínimo completo) y calcular el producto de las coordenadas `x` de los dos extremos de dicha conexión.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day08a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day08b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: El modelo matemático de distancias tridimensionales y de optimización de redes está encapsulado en `JunctionBox`, `Connection` y `Playground`, ocultando los detalles algorítmicos.
*   **Modularidad**: Separación limpia entre la lógica de E/S (`io`) y las estructuras algebraicas (`model`).
*   **Alta cohesión**: Cada componente tiene un propósito claro. `DisjointSet` se encarga exclusivamente de la estructura Union-Find (con compresión de caminos y unión por tamaño), `Connection` representa un enlace ordenable por distancia, y `JunctionBox` es un nodo en el espacio 3D.
*   **Bajo acoplamiento**: `TxtJunctionBoxLoader` depende de la interfaz `Deserializer<JunctionBox>`, evitando el acoplamiento a formatos de texto concretos.
*   **Inmutabilidad del modelo**: Las entidades principales del modelo (`JunctionBox` y `Connection`) se implementan como **Records** inmutables en Java.
*   **Diseño por contrato**: Definición limpia de interfaces para la lectura (`JunctionBoxLoader`) y la deserialización (`Deserializer`).

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [DisjointSet.java:L6-L48](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/DisjointSet.java#L6-L48): Se encarga de forma exclusiva de la lógica de la estructura de conjuntos disjuntos (Union-Find con compresión de caminos).
        *   [Connection.java:L3-L12](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Connection.java#L3-L12): Modela una única conexión física 3D entre dos nodos y calcula su distancia euclídea al cuadrado.
        *   [JunctionBox.java:L3-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/JunctionBox.java#L3-L11): Record inmutable que almacena únicamente las coordenadas espaciales `(x, y, z)` de un nodo.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las clases deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Playground.java:L9-L63](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L9-L63): Permite implementar y ejecutar simulaciones combinatorias de red personalizadas sin necesidad de alterar la lógica nuclear de los nodos (`JunctionBox`) o de conectividad (`DisjointSet`).
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar la corrección del programa.
    *   *Implementación*:
        *   [JunctionBoxLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/io/JunctionBoxLoader.java#L7-L9): Define la interfaz que se implementa de manera transparente mediante una expresión lambda en el cliente (`Main`), lo que permite que sea reemplazado por cargadores mock u otros sin romper el flujo principal.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [JunctionBoxLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/io/JunctionBoxLoader.java#L7-L9): Define un único método cohesivo (`load()`), asegurando una interfaz minimalista.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/a/Main.java#L17-L19): El cliente (`Main`) y la lógica dependen de la abstracción `JunctionBoxLoader` en lugar de una clase cargadora concreta, inyectando la implementación dinámicamente mediante una lambda.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Playground.java:L18-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L18-L25) (`allConnections()`): Genera de forma declarativa todas las posibles conexiones combinatorias entre las cajas usando `IntStream.range` y `flatMap()`, ordenándolas por distancia euclídea de menor a mayor.
        *   [Playground.java:L32-L41](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L32-L41) (`multiplyThreeLargestSizes()`): Procesa de forma funcional la lista de cajas de conexiones para mapear sus representantes (`map(ds::find)`), filtrar duplicados (`distinct()`), mapear a su tamaño (`map(ds::size)`), ordenar de forma descendente, y multiplicar los 3 primeros con `reduce()`.
*   **Inyección de dependencias**: Inyección del deserializador en el cargador por constructor.
*   **Genéricos**: `DisjointSet<T>` y `Deserializer<T>` para desacoplar el comportamiento de los tipos concretos de datos.
*   **Good Naming**: Nombres explícitos como `multiplyThreeLargestCircuitSizesAfterConnecting()`, `lastConnectionCoordinatesProduct()`, `squaredDistanceTo()`.

## Patrones de diseño

*   **Patrón Iterator / Streams**:
    *   *Implementación*: Uso exhaustivo de flujos y generadores en `Playground` (`IntStream.range().flatMap()`, `sorted()`, `collect()`) para emparejar y ordenar todas las conexiones combinatorias de manera declarativa.

---

## Pruebas realizadas

Se han diseñado pruebas unitarias exhaustivas con **JUnit** y **AssertJ** para validar el comportamiento del motor de conjuntos disjuntos y del cálculo de distancias espaciales.

### Rutas de las pruebas
*   **Tests de Deserialización**: [TxtJunctionBoxDeserializerTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/IOTest/TxtJunctionBoxDeserializerTest.java)
*   **Tests de la Parte A**: [PlaygroundTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/ATest/PlaygroundTest.java)
*   **Tests de la Parte B**: [PlaygroundTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/BTest/PlaygroundTest.java)

### Escenarios validados

#### Deserialización (`IOTest/TxtJunctionBoxDeserializerTest`)
*   **Parseo de coordenadas**: Conversión exitosa de líneas de texto a objetos `JunctionBox` con eliminación de espacios en blanco.
*   **Gestión de errores**: Excepciones correspondientes (`IllegalArgumentException`) si la línea es nula o vacía, si el formato es inválido, y `NumberFormatException` si contiene valores no numéricos.

#### Parte A (`ATest/PlaygroundTest`)
*   **Distancia euclídea**: Verificación del método `squaredDistanceTo` en `JunctionBox` para determinar distancias al cuadrado en 3D de manera precisa.
*   **Conectividad de Conjuntos (`DisjointSet`)**: Testeo de la compresión de caminos, inicialización implícita, tamaño de componentes conexos, y unión exitosa/redundante.
*   **Cálculo de Circuitos**: Verificación del producto de los 3 circuitos más grandes con el ejemplo base, dando `40L`.

#### Parte B (`BTest/PlaygroundTest`)
*   **Conexión Final**: Verificación del producto de coordenadas `x` de la última conexión para unir la red completa, dando `25272L`.
