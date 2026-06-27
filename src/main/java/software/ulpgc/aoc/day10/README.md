# Day 10: Machine Factory

## Descripción

El desafío consiste en optimizar el control de una serie de máquinas (`Machine`) en una planta de fabricación (`Factory`). Cada máquina tiene un estado objetivo (representado por una máscara de bits `targetMask`) que indica qué componentes o luces deben encenderse (`#`). Para lograr esto, disponemos de una serie de botones, donde cada botón conmuta (invierte con una operación XOR) un conjunto específico de componentes.
1.  **Parte Única**: Encontrar el número mínimo de pulsaciones de botón necesarias para alcanzar el estado objetivo desde un estado inicial donde todos los componentes están apagados. Se calcula la suma total de las pulsaciones mínimas de todas las máquinas de la fábrica. Si un estado no es alcanzable, se modela con un coste máximo por defecto.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day10a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day10b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Toda la manipulación de estados y resolución de caminos mediante máscara de bits XOR está encapsulada dentro del record `Machine`, aislando la lógica binaria del negocio principal.
*   **Modularidad**: Separación limpia entre la lógica del cargador y deserializador (`io`) y las entidades operacionales (`model`).
*   **Alta cohesión**: `Machine` representa la lógica de resolución individual de una máquina de estados, `Factory` agrega los resultados del conjunto de máquinas, y `Fraction` proporciona soporte para operaciones algebraicas exactas.
*   **Bajo acoplamiento**: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io`, que recibe una `Function<String, T>` de deserialización, en lugar de una implementación concreta de parseo.
*   **Inmutabilidad del modelo**: Las clases de datos clave del sistema (`Machine`, `Factory` y `Fraction`) se implementan como **Records** inmutables en Java.
*   **Diseño por contrato**: Definición limpia de la interfaz genérica `Deserializer<T>` para la deserialización, y uso de la factoría `LoaderFactory` que respeta el contrato genérico `TxtLoader<T>`.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Machine.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Machine.java#L5-L16): Se limita de manera única e inmutable a modelar los botones de conmutación XOR y a buscar el mínimo de pulsaciones para una máquina individual.
        *   [Factory.java:L5-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Factory.java#L5-L22): Únicamente se responsabiliza de la agregación y coordinación total del coste de las máquinas que componen la fábrica.
        *   [Fraction.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Fraction.java#L5-L15): Concentra exclusivamente el soporte de aritmética racional exacta.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las clases deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Machine.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Machine.java#L5-L16): La lógica interna de búsqueda recursiva de pulsaciones óptimas (backtracking con poda de profundidad) puede optimizarse o modificarse internamente sin alterar la interfaz expuesta hacia `Factory` o los tests existentes.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los subtipos deben poder reemplazar a sus tipos base.
    *   *Implementación*:
        *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtMachineDeserializer` implementa `Deserializer<Machine>` de forma limpia, lo que permite reemplazarlo por mock u otros sin romper el flujo principal.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`). La factoría `LoaderFactory` utiliza la interfaz funcional `Function<String, T>`, igualmente mínima.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Machine>` en lugar de una implementación deserializadora concreta.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Factory.java:L6-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Factory.java#L6-L10) (`totalMinPresses()`): Utiliza `machines.stream().mapToInt().sum()` para acumular de forma funcional el número total de pulsaciones mínimas de todas las máquinas en la planta.
        *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/a/Main.java#L17-L19) (`load()`): La factoría `LoaderFactory` procesa de forma funcional las líneas leídas del fichero mapeándolas con el deserializador y colectándolas en una lista.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe la función de deserialización como parámetro, inyectándola en el `TxtLoader` genérico.
*   **Genéricos**: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Machine`.
*   **Good Naming**: Nombres autodescriptivos como `minPresses()`, `totalMinPresses()`, `parseTargetMask()`, `parseButtonMask()`.

## Patrones de diseño

*   **Backtracking (Búsqueda con Retroceso)**:
    *   *Implementación*: La función recursiva privada `findMinPresses` realiza un recorrido en profundidad con poda de ramas (cuando las pulsaciones actuales superan la mejor solución encontrada) para buscar el conjunto óptimo de botones.

## Elección de diseño: Primitivos con orElse vs Optional

En la implementación del Solver de la **Parte B**, se optó por eliminar el uso de `Optional` y `OptionalInt` en la búsqueda recursiva de programación dinámica, prefiriendo tipos primitivos (`int`) junto con el operador `orElse`:

*   **Rendimiento**: La recursión con memoización evalúa miles de estados. El uso de `Optional` requería la asignación constante de envoltorios de objetos en memoria. El uso de `int` primitivos elimina por completo esta sobrecarga (evitando saturar el recolector de basura).
*   **Simplicidad en Streams**: Trabajar con `Optional` obligaba a usar flujos complejos y mapeos como `.flatMap(Optional::stream)`. Con tipos primitivos se utiliza la API nativa `.mapToInt(...)` y `.min().orElse(999999)` de forma directa y clara.
*   **Control de Desbordamiento con Centinelas**: Se utiliza el valor `999999` como centinela de "estado inalcanzable" (en lugar de un `Optional.empty()`), propagando el coste de manera controlada y evitando desbordamientos mediante la función pura `cost(presses, subResult)`.

---

## Pruebas realizadas

Se han diseñado pruebas unitarias detalladas utilizando **JUnit** y **AssertJ** para certificar el motor de backtracking XOR y las operaciones con números racionales.

### Rutas de las pruebas
*   **Tests de Deserialización**: [TxtMachineDeserializerTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day10/ATest/TxtMachineDeserializerTest.java)
*   **Tests de la Parte A**: [FactoryTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day10/ATest/FactoryTest.java)
*   **Tests de la Parte B**: [FactoryTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day10/BTest/FactoryTest.java)

### Escenarios validados

#### Deserialización (`TxtMachineDeserializerTest`)
*   **Parseo correcto**: Conversión exitosa de la representación de caracteres a máscaras de bits XOR para el objetivo y los botones.
*   **Validaciones**: Confirmación de que lanza `IllegalArgumentException` ante entradas nulas, vacías o con formato incorrecto, y `NumberFormatException` si los botones contienen valores no enteros.

#### Parte A (`FactoryTest`)
*   **Pulsaciones mínimas**: Validación del algoritmo de búsqueda recursiva de pulsaciones óptimas con el escenario base de ejemplo, esperando un resultado de `7` pulsaciones en total.
*   **Casos Límite**: Comprobación del comportamiento cuando el objetivo ya se encuentra en el estado inicial (`0` pulsaciones) y cuando un estado es completamente inalcanzable.

#### Parte B (`FactoryTest`)
*   **Pulsaciones mínimas extendidas**: Validación del algoritmo con el mismo escenario de ejemplo usando el deserializador de la Parte B, esperando un resultado de `33` pulsaciones en total.
