# Day 9: Movie Theater

## Descripción

El desafío consiste en optimizar la disposición espacial en un cine (`MovieTheater`), modelado mediante baldosas o asientos rojos posicionados en un plano bidimensional discreto.
1.  **Parte 1**: Encontrar el área máxima de cualquier rectángulo (definido por dos puntos cualesquiera de entrada) sin restricciones de obstáculos, calculando el producto de la cantidad de filas y columnas que cubre.
2.  **Parte 2**: Encontrar el área máxima de un rectángulo que no contenga ningún segmento del contorno o límite del cine en su interior, y que a su vez esté completamente contenido dentro del polígono delimitado por los segmentos de las baldosas rojas.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day09a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day09b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: El modelo del plano geométrico está encapsulado en `Point`, `Segment` y `MovieTheater`, ocultando los algoritmos de detección de colisiones y cálculo de áreas.
*   **Modularidad**: Separación limpia entre la lógica de E/S (`io`) y las estructuras espaciales del dominio (`model`).
*   **Alta cohesión**: Las clases y registros representan conceptos atómicos bien acotados: `Point` maneja coordenadas individuales y áreas, `Segment` representa los límites físicos entre asientos, y `MovieTheater` implementa la lógica de optimización del espacio.
*   **Bajo acoplamiento**: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io`, que recibe una `Function<String, T>` de deserialización, abstrayendo la fuente y formato de los datos de entrada.
*   **Inmutabilidad del modelo**: Todas las estructuras de datos (`Point`, `Segment` y `MovieTheater`) se implementan como **Records** inmutables en Java.
*   **Diseño por contrato**: Definición de la interfaz genérica `Deserializer<T>` para la deserialización, y uso de la factoría `LoaderFactory` que respeta el contrato genérico `TxtLoader<T>`.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Point.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/Point.java#L5-L16): Modela exclusivamente una coordenada bidimensional discreta y calcula áreas rectangulares relativas.
        *   [Segment.java:L5-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/Segment.java#L5-L25): Representa únicamente aristas del polígono del cine y calcula colisiones con el contorno.
        *   [MovieTheater.java:L9-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheater.java#L9-L44): Responsable exclusivo de buscar el área rectangular máxima libre de obstáculos.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las clases deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [MovieTheater.java:L9-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheater.java#L9-L44): Se introdujo la estructura `Segment` en la Parte B y se modificó la lógica de búsqueda en el teatro sin necesidad de alterar el código del modelo básico `Point`.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los subtipos deben poder reemplazar a sus tipos base sin alterar el comportamiento.
    *   *Implementación*:
        *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtPointDeserializer` implementa `Deserializer<Point>` de forma limpia, lo que permite reemplazarlo por cargadores mock u otros sin romper el flujo principal.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`). La factoría `LoaderFactory` utiliza la interfaz funcional `Function<String, T>`, igualmente mínima.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Point>` en lugar de una implementación deserializadora concreta.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [MovieTheater.java:L9-L14 (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/model/MovieTheater.java#L9-L14) (`maxRectangleArea()`): Utiliza combinaciones funcionales con `IntStream.range` y `flatMapToLong` para evaluar eficientemente las áreas de todos los pares de baldosas del cine.
        *   [MovieTheater.java:L16-L22 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/b/model/MovieTheater.java#L16-L22) (`maxRectangleAreaWithSegments()`): Aplica streams para filtrar combinaciones válidas que no toquen el contorno (`filter(isValidRectangle)`) y obtener el área máxima.
        *   [MovieTheater.java:L38-L40 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/b/model/MovieTheater.java#L38-L40) (`noSegmentIntersectsInterior()`): Usa `segments.stream().noneMatch()` para comprobar de forma declarativa que ninguna arista del polígono colisiona con el área rectangular.
        *   [MovieTheater.java:L63-L69 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/b/model/MovieTheater.java#L63-L69) (`isPointInside()`): Cuenta de forma declarativa el número de intersecciones de un rayo vertical mediante filtros y `count()`.
*   **Inyección de dependencias**: El cargador recibe la instancia del deserializador por constructor.
*   **Genéricos**: Uso de `Deserializer<T>` parametrizado para la entidad `Point`.
*   **Good Naming**: Nombres autodescriptivos como `maxRectangleArea()`, `rectangleAreaWith()`, `noSegmentIntersectsInterior()`, `isOnBoundary()`.

## Patrones de diseño

*   **Patrón Iterator / Streams**:
    *   *Implementación*: Uso de Java Streams (`IntStream.range().flatMapToLong()`) para evaluar de forma limpia y declarativa todas las combinaciones de pares de puntos en el cine.

## Elección de diseño: Primitivos con orElse vs Optional

En la clase `MovieTheater`, los métodos `maxRectangleArea()` (Parte A y Parte B) utilizan el operador `orElse` sobre el stream de áreas reducidas:

*   **¿Por qué es mejor `orElse`?**
    El método de cálculo de área debe devolver un resultado numérico final directo de tipo primitivo `long` para que sea consumido y acumulado en el punto de entrada principal `Main`. Si el stream de puntos estuviera vacío o no se encontrara ningún rectángulo válido (por ejemplo, si no se cumple el contorno de exclusión de obstáculos), el valor por defecto natural de área es `0L`. Retornar un `OptionalLong` añadiría una complejidad de envoltura innecesaria para el cliente, por lo que resolverlo de inmediato con `.orElse(0L)` es la mejor opción.

---

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
*   **Propiedades de Segmento**: Validación de los métodos `isHorizontal()`, `isVertical()`, y los límites `minX/maxX/minY/maxY` para aristas horizontales y verticales.
*   **Cálculo de Áreas Rectangulares**: Comprobación de que `rectangleAreaWith` calcula áreas correctas incluyendo los bordes del rectángulo.
*   **Parte A**: Verificación de que el área máxima sin obstáculos en el ejemplo es de `50L`.
*   **Parte B**: Verificación de que el área máxima respetando los límites del polígono interior del cine es de `24L`.
