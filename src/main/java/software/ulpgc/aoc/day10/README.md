# Day 10: Machine Factory

## Descripción

El desafío consiste en optimizar el control de una serie de máquinas (`Machine`) en una planta de fabricación (`Factory`). Cada máquina dispone de una serie de botones que afectan a sus componentes, y el objetivo es encontrar el número mínimo de pulsaciones necesarias para alcanzar un estado deseado. Se calcula la suma total de las pulsaciones mínimas de todas las máquinas de la fábrica.
1.  **Parte A**: Encontrar el número mínimo de pulsaciones de botón necesarias para alcanzar el estado objetivo desde un estado inicial donde todos los componentes están apagados, utilizando conmutación binaria de componentes.
2.  **Parte B**: Encontrar el número mínimo de pulsaciones necesarias para reducir a cero una serie de contadores numéricos de voltaje objetivo, donde cada botón contribuye a decrementar ciertos contadores y el estado se reduce progresivamente en cada paso.

## Diagramas UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day10.png) 

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan de forma abstracta los componentes físicos: `Button` (que representa un botón con sus índices de luces que altera), `Machine` (abstracción del hardware de luces y objetivos), e interacciones a través de `MachineCommand` y `Solver`. La manipulación a bajo nivel de máscaras y conmutación XOR se maneja abstractamente.
*   **Encapsulamiento**: El estado de conmutación XOR (`currentLights ^ buttonMask`) y las operaciones de bits se ocultan completamente en `MachineStatus` y `Solver`. La clase agregadora `Factory` o el método principal no acceden a estos cálculos binarios; solo ejecutan `totalMinPresses()`.
*   **Cohesión**: Cada clase tiene un único propósito: `Button` modela el pulsador físico, `Factory` agrupa las máquinas y delega su ejecución, `MachineStatus` representa el estado de conmutación de las luces de la máquina, y `Solver` (que implementa `MachineCommand`) encapsula el algoritmo recursivo de ramificación y poda para hallar la solución óptima.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.


## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: La clase `Factory` se compone de una colección de `Machine`, en lugar de heredar de una clase base abstracta de maquinaria o planta.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Machine.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Machine.java#L5-L16): Se limita de manera única e inmutable a modelar los botones de conmutación XOR y a buscar el mínimo de pulsaciones para una máquina individual.
            *   [Factory.java:L5-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Factory.java#L5-L22): Únicamente se responsabiliza de la agregación y coordinación total del coste de las máquinas que componen la fábrica.
            *   [Fraction.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Fraction.java#L5-L15): Concentra exclusivamente el soporte de aritmética racional exacta.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Machine.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Machine.java#L5-L16): La lógica interna de búsqueda recursiva de pulsaciones óptimas (backtracking con poda de profundidad) puede optimizarse o modificarse internamente sin alterar la interfaz expuesta hacia `Factory` o los tests existentes.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtMachineDeserializer` implementa `Deserializer<Machine>` de forma limpia, lo que permite reemplazarlo por mock u otros sin romper el flujo principal.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/a/Main.java#L17-L19): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Machine>` en lugar de una implementación deserializadora concreta.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: `Factory` interactúa directamente con `Machine` sin navegar por sus máscaras o valores de botones internos.

## Técnicas de diseño aplicadas
*   **Inmutabilidad del modelo**: Las clases principales del modelo de datos (`Button`, `Factory`, `MachineStatus`) se implementan como **Records** inmutables. El paso a un nuevo estado de máquina no altera el estado anterior, sino que genera una nueva instancia de `MachineStatus` a través de `nextStatus()`.
*   **Programación funcional (con Java Streams)**:
    *   [Factory.java:L6-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Factory.java#L6-L10) (`totalMinPresses()`): Utiliza `machines.stream().mapToInt().sum()` para acumular de forma funcional el número total de pulsaciones mínimas de todas las máquinas en la planta.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe la función de deserialización como parámetro, inyectándola en el `TxtLoader` genérico. Además, la clase `Factory` de máquinas recibe el algoritmo de resolución (`MachineCommand`) inyectado por constructor, evitando acoplamientos internos con implementaciones concretas del `Solver`.
*   **Genéricos**:Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Machine`.
*   **Good Naming**: Nombres autodescriptivos como `minPresses()`, `isFullyConfigured()`, `calculateButtonImpact()`, `isConfigurationFeasible()`.


## Patrones de diseño
*   **Patrones de comportamiento**:
    *   **Command**: La resolución de cada máquina ha sido extraída a la interfaz `MachineCommand`. Ahora `Machine` es un objeto de datos puro sin métodos de cálculo, rompiendo la dependencia cíclica con `Solver` y mejorando la cohesión.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como en `totalMinPresses()`.

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
