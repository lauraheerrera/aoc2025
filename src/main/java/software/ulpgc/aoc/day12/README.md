# Day 12: Christmas Tree Farm

## Descripción

El desafío consiste en validar si un conjunto de regalos navideños con formas irregulares bidimensionales (polyominoes) caben en diferentes regiones rectangulares debajo de árboles de Navidad, sin solaparse.
1.  **Parte 1 / Parte 2**: Calcular cuántas de las regiones especificadas pueden acomodar con éxito las cantidades de regalos solicitadas de cada forma. Dado que la entrada física del problema permite un atajo heurístico garantizado por sus límites, basta con verificar si el área acumulada de los regalos requeridos es menor o igual al área rectangular de la región del árbol.

## Diagramas UML

Modelo
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day12.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modela el problema mediante conceptos de alto nivel: `Shape` (abstrae una figura física y su área), `Region` (abstrae un sector bidimensional del terreno y las cantidades de elementos presentes) y `Farm` (que abstrae todo el conjunto de terrenos a evaluar).
*   **Encapsulamiento**: La lógica matemática que determina si una lista de figuras cabe o no dentro de una zona específica (`fits(shapes)`) está encapsulada dentro del record `Region`. Ninguna clase externa conoce cómo se calcula el área requerida o el cruce con las cantidades de elementos.
*   **Cohesión**: Cada componente tiene un único rol definido: `Shape` únicamente expone el área de las figuras, `Region` representa los límites y el contenido local del terreno, y `Farm` actúa exclusivamente como el agregador encargado de filtrar y contabilizar las regiones válidas.
*   **Bajo acoplamiento**: Las clases del modelo geométrico (`Shape`, `Region`, `Farm`) están totalmente desacopladas de la infraestructura de carga de archivos e interpretación de texto de entrada.
*   **Código expresivo**: El cálculo acumulado del área necesaria en `Region` se realiza declarativamente usando la API funcional de Java (`IntStream.range().map().sum()`), lo que permite que el algoritmo sea directo, legible y libre de variables mutables temporales.
*   **Inmutabilidad del modelo**: `Shape`, `Region` y `Farm` son **Records** inmutables. La consulta de disponibilidad y las operaciones matemáticas devuelven valores inmutables sin realizar modificaciones de estado colaterales.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: `Farm` se compone de una colección de `Region`, evitando herencias o acoplamientos rígidos entre granjas y sus sectores.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Shape.java:L3](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Shape.java#L3): Define exclusivamente las propiedades de una figura de regalo individual.
        *   [Region.java:L6-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L6-L18): Su responsabilidad es modelar una región de árbol y determinar individualmente si caben los regalos.
        *   [Farm.java:L5-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Farm.java#L5-L11): Encapsula de forma única el recuento global de regiones válidas en la granja.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   El modelo de dominio formado por `Shape`, `Region` y `Farm` define una estructura basada en la representación de áreas y regiones. La lógica de decisión (`fits`) está encapsulada dentro de `Region`, mientras que `Farm` únicamente orquesta la agregación de resultados sin conocer los detalles de cálculo. Esto permite que nuevas formas de calcular el ajuste o nuevas restricciones geométricas puedan incorporarse extendiendo el modelo mediante variaciones del cálculo, sin modificar las clases existentes del dominio.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtShapeDeserializer.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L5) y [TxtRegionDeserializer.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtRegionDeserializer.java#L8): Son perfectamente sustituibles bajo la firma abstracta de `Deserializer<T>`.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`). Los deserializadores del Día 12 solo implementan este método específico.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   La carga de datos en el Main no depende de deserializadores acoplados físicamente, sino de la interfaz abstracta `Deserializer<T>` inyectada en el método `load()`.
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

