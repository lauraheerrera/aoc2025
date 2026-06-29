# Day 9: Movie Theater

## Descripción

El desafío consiste en optimizar la disposición espacial en un cine (`MovieTheater`), modelado mediante baldosas o asientos rojos posicionados en un plano bidimensional discreto.
1.  **Parte 1**: Encontrar el área máxima de cualquier rectángulo (definido por dos puntos cualesquiera de entrada) sin restricciones de obstáculos, calculando el producto de la cantidad de filas y columnas que cubre.
2.  **Parte 2**: Encontrar el área máxima de un rectángulo que no contenga ningún segmento del contorno o límite del cine en su interior, y que a su vez esté completamente contenido dentro del polígono delimitado por los segmentos de las baldosas rojas.

## Diagramas UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day09.png) 

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se abstrae el espacio bidimensional del teatro utilizando `Tile` (que representa una coordenada y sus cálculos de área asociados) y `MovieTheater` (que representa el teatro y sus cálculos de área). La complejidad geométrica se maneja a través de estos tipos sin exponer detalles de matrices continuas.
*   **Encapsulamiento**: Las operaciones geométricas se encapsulan dentro de los propios objetos, evitando exponer cálculos internos.
*   **Cohesión**: Cada componente se centra en un único concepto: `Tile` representa una coordenada discreta y calcula áreas de forma autónoma y `MovieTheater` implementa exclusivamente los algoritmos globales de optimización (como calcular áreas máximas libres o contar baldosas rojas no cruzadas). 
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.


## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: `MovieTheater` se compone de una colección de `Tile`, omitiendo el uso de herencia espacial.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Tile.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/Tile.java): Responsable exclusivo de representar una baldosa roja y calcular áreas de forma autónoma.
        *   [MovieTheater.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheater.java): Responsable de buscar el área rectangular máxima libre de obstáculos.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        * El sistema está abierto a nuevas estrategias mediante la interfaz [MovieTheaterInterface.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheaterInterface.java). Esto permite añadir nuevas versiones del problema sin modificar el código existente.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtPointDeserializer` implementa `Deserializer<Tile>` de forma limpia, lo que permite reemplazarlo por cargadores mock u otros sin romper el flujo principal.
        *   Cualquier implementación de [MovieTheaterInterface.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheaterInterface.java) puede ser sustituida por cualquier otra implementación.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Tile>` en lugar de una implementación deserializadora concreta.
    *   **Law of Demeter (LoD - Ley de Deméter)**: `MovieTheater` interactúa con `Tile` mediante sus abstracciones sin navegar por sus variables coordinadas `x` e `y` individuales.

## Técnicas de diseño aplicadas
*   **Inmutabilidad del modelo**: Todas las estructuras de datos del dominio (`Tile` y `MovieTheater`) están implementadas como **Records** inmutables en Java, garantizando que el estado espacial del teatro no pueda ser modificado de manera accidental durante los cálculos geométricos.
*   **Programación funcional (con Java Streams)**:
    *   [MovieTheater.java:L9-L14 (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/model/MovieTheater.java#L9-L14) (`maxRectangleArea()`): Utiliza combinaciones funcionales con `IntStream.range` y `flatMapToLong` para evaluar eficientemente las áreas de todos los pares de baldosas del cine.
    *   [MovieTheater.java:L16-L22 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/b/model/MovieTheater.java#L16-L22) (`maxRectangleArea()`): uso de streams con filtros geométricos para validar restricciones espaciales.
*   **Inyección de dependencias**:
    *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Tile>` en lugar de una implementación deserializadora concreta.
*   **Genéricos**:
    *   Uso de `Deserializer<T>` parametrizado para la entidad `Tile`.
*   **Good Naming**: Nombres autodescriptivos como `maxRectangleArea()`, `hasInternalCrossings()`, `crossesVertical()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como en `maxRectangleArea()`.

## Pruebas realizadas

Se han diseñado pruebas unitarias robustas utilizando **JUnit** y **AssertJ** para certificar el cálculo de áreas y la detección de intersecciones con los límites del polígono.

### Rutas de las pruebas
*   **Tests de Deserialización**: [TxtPointDeserializerTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day09/ATest/TxtPointDeserializerTest.java)
*   **Tests de la Parte A**: [MovieTheaterTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day09/ATest/MovieTheaterTest.java)
*   **Tests de la Parte B**: [MovieTheaterTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day09/BTest/MovieTheaterTest.java)

### Escenarios validados

#### Deserialización (`TxtPointDeserializerTest`)
*   **Parseo de coordenadas**: Verificación del parseo de líneas con números positivos, negativos y con espacios.
*   **Gestión de errores**: Comprobación de que lanza `IllegalArgumentException` ante entradas vacías o mal formateadas, y `NumberFormatException` si contiene valores no numéricos.

#### Modelo y Algoritmos (`MovieTheaterTest`)
*   **Cálculo de Áreas Rectangulares**: Comprobación de que `rectangleAreaWith` calcula áreas correctas incluyendo los bordes del rectángulo.
*   **Parte A**: Verificación de que el área máxima sin obstáculos en el ejemplo es de `50L`.
*   **Parte B**: Verificación de que el área máxima respetando los límites del polígono interior del cine es de `24L`.
