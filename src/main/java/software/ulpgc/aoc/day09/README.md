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

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: El modelo del plano geométrico está encapsulado en `Point`, `Segment` y `MovieTheater`, ocultando los algoritmos de detección de colisiones y cálculo de áreas.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: Toda la lógica interna de la colisión de rayos e intersecciones de segmentos se oculta tras los records del dominio.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: Las clases y registros representan conceptos atómicos bien acotados: `Point` maneja coordenadas individuales y áreas, `Segment` representa los límites físicos entre asientos, y `MovieTheater` implementa la lógica de optimización del espacio.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io`, que recibe una `Function<String, T>` de deserialización, abstrayendo la fuente y formato de los datos de entrada.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: La simulación combinatoria de áreas e intersecciones de aristas se modela declarativamente con streams, aumentando la expresividad del algoritmo geométrico.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Todas las estructuras de datos (`Point`, `Segment` y `MovieTheater`) se implementan como **Records** inmutables en Java.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `Segment` se compone de dos instancias de `Point`, y `MovieTheater` se compone de una colección de `Point` y otra de `Segment`, eludiendo el uso de herencia espacial.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Point.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/Point.java#L5-L16): Modela exclusivamente una coordenada bidimensional discreta y calcula áreas rectangulares relativas.
            *   [Segment.java:L5-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/Segment.java#L5-L25): Representa únicamente aristas del polígono del cine y calcula colisiones con el contorno.
            *   [MovieTheater.java:L9-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheater.java#L9-L44): Responsable exclusivo de buscar el área rectangular máxima libre de obstáculos.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [MovieTheater.java:L9-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/model/MovieTheater.java#L9-L44): Se introdujo la estructura `Segment` en la Parte B y se modificó la lógica de búsqueda en el teatro sin necesidad de alterar el código del modelo básico `Point`.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtPointDeserializer` implementa `Deserializer<Point>` de forma limpia, lo que permite reemplazarlo por cargadores mock u otros sin romper el flujo principal.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Point>` en lugar de una implementación deserializadora concreta.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: `MovieTheater` interactúa con `Point` y `Segment` mediante sus abstracciones sin navegar por sus variables coordinadas `x` e `y` individuales.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [MovieTheater.java:L9-L14 (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/a/model/MovieTheater.java#L9-L14) (`maxRectangleArea()`): Utiliza combinaciones funcionales con `IntStream.range` y `flatMapToLong` para evaluar eficientemente las áreas de todos los pares de baldosas del cine.
        *   [MovieTheater.java:L16-L22 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day09/b/model/MovieTheater.java#L16-L22) (`maxRectangleAreaWithSegments()`): Aplica streams para filtrar combinaciones válidas que no toquen el contorno (`filter(isValidRectangle)`) y obtener el área máxima.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: El cargador recibe la instancia del deserializador por constructor.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Uso de `Deserializer<T>` parametrizado para la entidad `Point`.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres autodescriptivos como `maxRectangleArea()`, `rectangleAreaWith()`, `noSegmentIntersectsInterior()`, `isOnBoundary()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).

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
