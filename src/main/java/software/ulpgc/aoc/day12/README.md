# Day 12: Christmas Tree Farm

## Descripción

El desafío consiste en validar si un conjunto de regalos navideños con formas irregulares bidimensionales (polyominoes) caben en diferentes regiones rectangulares debajo de árboles de Navidad, sin solaparse.
1.  **Parte 1**: Calcular cuántas de las regiones especificadas pueden acomodar con éxito las cantidades de regalos solicitadas de cada forma. Dado que la entrada física del problema permite un atajo heurístico garantizado por sus límites, basta con verificar si el área acumulada de los regalos requeridos es menor o igual al área rectangular de la región del árbol.

## Diagramas UML

Modelo
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day12.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

* **Abstracción**: El problema se modela mediante los conceptos de `Shape` (figuras geométricas), `Region` (espacio rectangular con restricciones de capacidad) y `Farm` (agregador de regiones a evaluar).
* **Encapsulamiento**: La lógica de decisión sobre si un conjunto de formas cabe en una región no reside en `Region`, sino en una abstracción independiente `RegionFitter`, que encapsula los distintos algoritmos de resolución (greedy o backtracking).
* **Cohesión**: Cada componente tiene una responsabilidad clara:
    - `Shape`: representación geométrica e invariantes de la figura.
    - `Region`: modelo de espacio disponible y requerimientos.
    - `Farm`: agregación y conteo de regiones.
    - `RegionFitter`: estrategia de resolución del problema de encaje.
* **Bajo acoplamiento**: El modelo de dominio (`Shape`, `Region`, `Farm`) no depende de la implementación concreta del algoritmo de ajuste, sino de la abstracción `RegionFitter`.
* **Código expresivo**: Las operaciones de expansión de figuras y cálculo de áreas se realizan mediante Streams de Java, favoreciendo expresividad y reducción de estado mutable.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: `Farm` se compone de una colección de `Region`, evitando herencias o acoplamientos rígidos entre granjas y sus sectores.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   **Shape**: representa una figura geométrica inmutable.
        *   **Region**: modela el espacio disponible y las restricciones de demanda.
        *   **Farm**: orquesta el conteo de regiones válidas.
        *   **RegionFitter**: encapsula el algoritmo de resolución del problema de encaje.
        *   **GreedyRegionFitter**: implementaciones concretas del algoritmo.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   El modelo de dominio formado por `Shape`, `Region` y `Farm` define una estructura basada en la representación de áreas y regiones. La lógica de decisión (`canFit`) está encapsulada dentro de `RegionFitter`, mientras que `Farm` únicamente orquesta la agregación de resultados sin conocer los detalles de cálculo. Esto permite que nuevas formas de calcular el ajuste puedan incorporarse con nuevas implementaciones, sin modificar las clases existentes del dominio.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtShapeDeserializer.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L5) y [TxtRegionDeserializer.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtRegionDeserializer.java#L8): Son perfectamente sustituibles bajo la firma abstracta de `Deserializer<T>`.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`). Los deserializadores del Día 12 solo implementan este método específico.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   La carga de datos en el Main no depende de deserializadores acoplados físicamente, sino de la interfaz abstracta `Deserializer<T>` inyectada en el método `load()`.
        Asimismo, `Farm` depende de la abstracción `RegionFitter`, no de implementaciones concretas. Esto permite sustituir algoritmos sin modificar la lógica de agregación.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   `Farm` interactúa directamente con `Region` para consultar si encaja la forma, sin examinar las dimensiones o límites específicos de la misma.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   [Region.java:L12-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L12-L15) (`fits()`): Utiliza `IntStream.range()` para iterar y multiplicar cantidades y áreas en paralelo, acumulando el área mediante `.sum()`.
    *   [TxtShapeDeserializer.java:L13-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L13-L16) (`deserialize()`): Cuenta los caracteres `#` de las líneas del bloque de la figura usando `.flatMapToInt(String::chars).filter(c -> c == '#').count()`.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe la función de deserialización como parámetro en tiempo de ejecución, inyectándola en el `TxtLoader` genérico.
*   **Genéricos**: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Device`.
*   **Good Naming**: Nombres de métodos limpios y descriptivos como `fits()`, `countRegionsThatFit()`, y `toBlocks()`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Uso de métodos de creación estáticos como `Farm.of()` para construir granjas con un número de regiones.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como en `countRegionsThatFit()`.
*   **Patrones estratégicos**:
    *   **Strategy**: `Farm` utiliza una `RegionFitter` inyectada (Strategy Pattern) para decidir cómo evaluar las regiones. Esto desacopla la lógica de agregación (`Farm`) del algoritmo de encaje (`RegionFitter`), permitiendo intercambiar estrategias (greedy, backtracking, etc.) sin modificar el cliente.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la lógica de negocio del modelo en diversos escenarios.

### Rutas de las pruebas
* **Tests de Deserializacion**: [`IOTest`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day12/IOTest/)
*   **Tests de la Parte A**: [`FarmTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day12/ATest/FarmTest.java)

### Escenarios validados

#### Deserialización (`IOTest`)
*   **Deserialización de figuras**: Validación de que `TxtShapeDeserializer` parsea correctamente los bloques de texto de las figuras de regalos y calcula el área total de sus celdas `#`.
*   **Deserialización de regiones**: Validación de que `TxtRegionDeserializer` decodifica las dimensiones y los requerimientos numéricos de regalos, gestionando correctamente la presencia de tabulaciones y espacios en blanco irregulares en la entrada.

#### Parte A (`ATest/FarmTest`)
*   **Validación de ajuste de región**: Verificación de que `Region.fits()` calcula de manera precisa el área consumida por las cantidades de regalos solicitadas y confirma si cabe en el espacio del árbol.
*   **Conteo de regiones válidas**: Validación de que `Farm.countRegionsThatFit()` filtra y calcula de forma acumulada el número total de regiones que admiten su combinación correspondiente de regalos.

