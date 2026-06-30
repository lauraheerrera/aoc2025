# Day 8: Junction Box

## Descripción

El desafío consiste en modelar y conectar una red tridimensional de cajas de conexiones (`JunctionBox`) en un espacio tridimensional. Cada caja tiene coordenadas enteras `(x, y, z)`. Las conexiones posibles entre dos cajas se definen por su distancia euclídea al cuadrado.
1.  **Parte 1**: Conectar las cajas utilizando el algoritmo de Kruskal (Kruskal's MST algorithm) / estructura de conjuntos disjuntos (Disjoint Set) y, tras aplicar las primeras 1000 conexiones más cortas, calcular el producto del tamaño de los 3 componentes conexos (circuitos) más grandes.
2.  **Parte 2**: Determinar cuál es la última conexión que une todos los nodos en una única red conectada (árbol de expansión mínimo completo) y calcular el producto de las coordenadas `x` de los dos extremos de dicha conexión.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day08.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan de forma abstracta las entidades físicas y matemáticas del problema: `JunctionBox` representa una caja de conexiones en el espacio tridimensional (x, y, z), `Connection` representa el enlace y la distancia euclídea calculada entre dos cajas, y `Playground` abstrae el conjunto total de cajas y sus relaciones.
*   **Encapsulamiento**: La estructura de datos Union-Find (`DisjointSet`) encapsula completamente la lógica de compresión de caminos, la búsqueda del representante del conjunto y la unión por tamaño mediante estructuras internas privadas (`parent`, `size`), ofreciendo una API limpia y simple (`union`, `find`, `size`).
*   **Cohesión**: Cada clase tiene un único propósito: `JunctionBox` almacena las coordenadas espaciales, `Connection` calcula distancias euclídeas y se compara de forma única por esta propiedad, `DisjointSet` administra conjuntos disjuntos y `Playground` se encarga de aplicar los algoritmos de interconectividad (Kruskal) sobre las cajas de conexiones.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: La clase `Connection` compone las dos instancias de `JunctionBox` correspondientes a sus extremos, en lugar de recurrir a la herencia.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [DisjointSet.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/DisjointSet.java): Se encarga de forma exclusiva de la lógica de la estructura de conjuntos disjuntos (Union-Find con compresión de caminos).
        *   [Connection.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Connection.java): Modela una única conexión física 3D entre dos nodos y calcula su distancia euclídea al cuadrado.
        *   [JunctionBox.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/JunctionBox.java): Record inmutable que almacena únicamente las coordenadas espaciales `(x, y, z)` de un nodo.
        *   [ConnectionGenerator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/ConnectionGenerator.java): Genera de forma declarativa todas las posibles conexiones combinatorias entre las cajas usando `IntStream.range` y `flatMap()`, ordenándolas por distancia euclídea de menor a mayor.
        *   [Playground.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java): Aplica el algoritmo de Kruskal (Kruskal's MST algorithm) y la estructura de conjuntos disjuntos (Disjoint Set) para construir un árbol de expansión mínima sobre el grafo de conexiones.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   El modelo de red de conexiones basado en `JunctionBox` y `Connection` forma un núcleo estable que define la estructura del grafo y las relaciones entre nodos. La lógica de construcción del árbol de expansión mínima está implementada utilizando `DisjointSet` dentro de `Playground`, pero no forma parte de la representación del dominio. Esto permite que nuevas políticas de conexión o estrategias alternativas de construcción del MST puedan incorporarse mediante variaciones del algoritmo, sin modificar las clases que representan el dominio ni la estructura de conjuntos disjuntos.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtJunctionBoxDeserializer` implementa `Deserializer<JunctionBox>` de forma limpia, permitiéndose reemplazar por mock u otros sin romper el flujo principal.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/a/Main.java#L17-L19): El cliente (`Main`) depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<JunctionBox>` en lugar de una clase cargadora concreta.
*   **Law of Demeter (LoD - Ley de Deméter)**: `Playground` interactúa con `Connection` y `JunctionBox` a través de sus métodos expuestos, sin navegar hacia los componentes internos de sus coordenadas.

## Técnicas de diseño aplicadas
*   **Inmutabilidad del modelo**: `JunctionBox`, `Connection` y `Playground` están implementados como **Records** inmutables. A pesar de que `DisjointSet` mantiene un estado mutable temporal por rendimiento algorítmico, este se crea y encapsula de forma aislada para cada cálculo, asegurando que el modelo de datos de entrada permanezca intacto.
*   **Programación funcional (con Java Streams)**:
    *   [Playground.java:L18-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L18-L25) (`allConnections()`): Genera de forma declarativa todas las posibles conexiones combinatorias entre las cajas usando `IntStream.range` y `flatMap()`, ordenándolas por distancia euclídea de menor a mayor.
    *   [Playground.java:L32-L41](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L32-L41) (`multiplyThreeLargestSizes()`): Procesa de forma funcional la lista de cajas de conexiones para mapear sus representantes (`map(ds::find)`), filtrar duplicados (`distinct()`), mapear a su tamaño (`map(ds::size)`), ordenar de forma descendente, y multiplicar los 3 primeros con `reduce()`.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe una `Function<String, T>` como parámetro. A su vez, `Worksheet.parse()` recibe el `Deserializer<Problem>` como argumento.
*   **Genéricos**: Se usan `DisjointSet<T>` y `Deserializer<T>` para desacoplar el comportamiento de los tipos concretos de datos.
*   **Good Naming**: Nombres explícitos como `multiplyThreeLargestCircuitSizesAfterConnecting()`, `lastConnectionCoordinatesProduct()`, `squaredDistanceTo()`.

## Patrones de diseño
*   **Patrones funcionales**:
    *   **Closure**: `findLasConnectionWithState()` hace uso de una closure `filter(c -> ds.size(c.first()) == totalBoxes)`. `totalBoxes` es una variable capturada por la closure.

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
