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

*   **Abstracción**:
    *   *Definición*: Permite identificar y modelar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Toda la manipulación de estados y resolución de caminos mediante máscara de bits XOR está encapsulada dentro del record `Machine`, aislando la lógica binaria del negocio principal.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La máscara de bits y la conmutación XOR son invisibles para la clase `Factory`, que solo conoce la interfaz pública para consultar el coste de la máquina.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo —como una clase o función— están relacionados entre sí y colaboran para cumplir una única tarea o propósito. Un módulo se considera altamente cohesivo cuando todas sus partes están directamente conectadas con la responsabilidad central que se le ha asignado, trabajando de forma coordinada hacia un objetivo común.
    *   *Implementación*: `Machine` representa la lógica de resolución individual de una máquina de estados, `Factory` agrega los resultados del conjunto de máquinas, y `Fraction` proporciona soporte para operaciones algebraicas exactas.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io`, que recibe una `Function<String, T>` de deserialización, en lugar de una implementación concreta de parseo.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: El uso de Records de Java e inmutabilidad nos permite modelar las relaciones de coste y botones sin recurrir a clases y variables mutables complejas.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las clases de datos clave del sistema (`Machine`, `Factory` y `Fraction`) se implementan como **Records** inmutables en Java.

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
*   **You Aren’t Gonna Need It (YAGNI)**:
    *   *Definición*: No se debe implementar funcionalidad hasta que realmente sea necesaria, evitando complejidad innecesaria.
*   **Convention Over Configuration (CoC - Convención sobre configuración)**:
    *   *Definición*: El sistema debe funcionar con una configuración mínima, asumiendo convenciones por defecto para simplificar su uso.
*   **Principio de mínima sorpresa**:
    *   *Definición*: El comportamiento de un componente debe ser predecible e intuitivo, sin efectos secundarios inesperados.
*   **Principio de mínimo compromiso**:
    *   *Definición*: Una interfaz debe exponer sólo lo necesario para operar, ocultando detalles internos y reduciendo la dependencia entre módulos.
*   **Keep It Simple, Stupid (KISS)**:
    *   *Definición*: El código debe ser claro, directo y fácil de entender, evitando la complejidad innecesaria.

## Diseño por contrato
*   **Definición**: El diseño por contrato es un enfoque de diseño que formaliza los acuerdos entre un componente y sus consumidores (por ejemplo, entre una clase y quien la utiliza), a través de interfaces claras y bien definidas. Se basa en la idea de que cada componente ofrece una serie de servicios bajo ciertas condiciones (precondiciones) y, a cambio, garantiza ciertos resultados (postcondiciones), mientras mantiene invariantes internas.
*   **Implementación**: La interfaz `Deserializer<Machine>` establece el contrato formal de deserialización del dominio.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Factory.java:L6-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day10/model/Factory.java#L6-L10) (`totalMinPresses()`): Utiliza `machines.stream().mapToInt().sum()` para acumular de forma funcional el número total de pulsaciones mínimas de todas las máquinas en la planta.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: La factoría `LoaderFactory` recibe la función de deserialización como parámetro, inyectándola en el `TxtLoader` genérico.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Machine`.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres autodescriptivos como `minPresses()`, `totalMinPresses()`, `parseTargetMask()`, `parseButtonMask()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).

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
