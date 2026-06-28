# Day 3: Lobby

## Descripción

El desafío consiste en encontrar la combinación de dígitos que maximice el voltaje producido por bancos de baterías. Cada banco está representado por una cadena de dígitos.
1.  **Parte 1**: Seleccionar exactamente **2** dígitos de cada banco para maximizar el valor.
2.  **Parte 2**: Seleccionar exactamente **12** dígitos de cada banco.

El objetivo final es sumar los voltajes máximos encontrados en todos los bancos de baterías proporcionados.

## Diagrama UML
Para ambas partes, el modelo de clases resulta ser igual
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day03.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar y modelar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Oculta la lógica compleja de cálculo voraz y recursivo tras la API de `BatteryBank` (`maxJoltageOfLength()`).
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La lógica de ordenación Greedy recursiva está escondida dentro del calculador de joltajes.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo —como una clase o función— están relacionados entre sí y colaboran para cumplir una única tarea o propósito. Un módulo se considera altamente cohesivo cuando todas sus partes están directamente conectadas con la responsabilidad central que se le ha asignado, trabajando de forma coordinada hacia un objetivo común.
    *   *Implementación*: En nuestro diseño, `BatteryBank` se encarga exclusivamente de representar un banco de baterías, y `BatteryBankMaxJoltageCalculator` realiza de forma única el algoritmo voraz de optimización matemática.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io` para leer el fichero, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de dominio.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: Se utiliza una aproximación recursiva con nombres de métodos declarativos (`selectDigitAndRecurse`, `findMaxIndex`), lo que permite que el algoritmo "Greedy" sea autoexplicativo, evitando bucles iterativos complejos y anidados.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las instancias (`BatteryBank` y `BatteryBankMaxJoltageCalculator`) son completamente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `TotalBatteryJoltageCalculator` se compone de una referencia a `BatteryBankMaxJoltageCalculator` y al objeto de valor `Length`, evitando crear jerarquías de herencia rígidas para evaluar las distintas longitudes de la Parte A y B.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [BatteryBank.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java#L5-L16): Representa exclusivamente el modelo de datos de una batería individual.
            *   [BatteryBankMaxJoltageCalculator.java:L8-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L8-L26): Encapsula en exclusiva la lógica del algoritmo Greedy para calcular el voltaje máximo de un banco individual.
            *   [TotalBatteryJoltageCalculator.java:L10-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L19): Responsable único de realizar la agregación de voltajes máximos de una lista de bancos.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [TotalBatteryJoltageCalculator.java:L10-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L19): Está parametrizada mediante su constructor usando la abstracción de valor `Length`, permitiendo realizar la Parte A (longitud 2) y Parte B (longitud 12) sin modificar su código interno.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtBatteryBankDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/io/TxtBatteryBankDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<BatteryBank>`, pudiendo ser utilizada indistintamente por cualquier cargador de ficheros de texto.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [TotalBatteryJoltageCalculator.java:L10-L13](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L13): Recibe sus dependencias (`BatteryBankMaxJoltageCalculator` y `Length`) desde el constructor (Inyección de Dependencias).
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: El acumulador `TotalBatteryJoltageCalculator` interactúa de forma directa con `BatteryBankMaxJoltageCalculator` y `Length`, sin requerir inspecciones profundas de la cadena de dígitos del banco de baterías.
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
*   **Implementación**: Se formalizan los acuerdos y expectativas mediante la interfaz de deserialización y el Value Object `Length`.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [TotalBatteryJoltageCalculator.java:L14-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L14-L18) (`sumAllMaxJoltageFrom()`): Utiliza `batteryBanks.stream()` para recorrer declarativamente la lista de bancos de baterías, mapeando cada banco (`map`) a su voltaje máximo optimizado (calculado por la clase delegada) y reduciéndolos (`reduce`) con la suma de joltajes (`Joltage::add`) partiendo de `Joltage.ZERO`.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: Se inyecta la instancia de `BatteryBankMaxJoltageCalculator` y el Value Object `Length` a través del constructor del acumulador `TotalBatteryJoltageCalculator`.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar los tipos concretos de datos de entrada.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres claros como `maxJoltageOfLength()`, `selectDigitAndRecurse()` y `sumAll()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
        *   *Implementación*: Se define y consume en [BatteryBank.java:L10-L12](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java#L10-L12) mediante `BatteryBank.create()`.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
        *   *Implementación*: Aplicado mediante streams en [TotalBatteryJoltageCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java) y en [BatteryBankMaxJoltageCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java).
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
        *   *Implementación*: Empleado a través de lambdas y referencias a métodos en `TotalBatteryJoltageCalculator` capturando el calculador y la longitud.

## Elección de diseño: Primitivos con orElse vs Optional

En la clase `BatteryBankMaxJoltageCalculator`, el método `findMaxIndex` utiliza el operador `orElse` para resolver la reducción de un stream de enteros:

*   **¿Por qué es mejor `orElse`?**
    El método debe devolver un índice de carácter como tipo primitivo `int` para realizar el troceado recursivo directo de la cadena. Si retornara un `OptionalInt`, obligaría a la función recursiva llamadora a gestionar la verificación de presencia de valor. Dado que el flujo del algoritmo Greedy garantiza que el rango tiene elementos válidos, resolver el flujo con `.orElse(start)` proporciona una salvaguarda segura y directa (retornando el propio índice de inicio) manteniendo el código ágil y sin envoltorios.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la exactitud del algoritmo Greedy sobre diversos escenarios de prueba.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day03/IOTest/TxtBatteryBankDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/IOTest/TxtBatteryBankDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day03/ATest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day03/BTest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)

### Escenarios validados

#### Parte A (`ATest/BatteryTest`)
*   **Algoritmo Voraz**: Validación de que `BatteryBank` extrae correctamente la subsecuencia de dígitos máxima de longitud 2 en diversos casos de ejemplo (ej. `"987654321111111"` -> `98`, `"811111111111119"` -> `89`).
*   **Cálculo de suma total**: Confirmación de que `BatteryBankMaxJoltageCalculator` realiza la suma acumulada precisa de los voltajes óptimos con los datos de ejemplo, esperando un valor de `357`.

#### Parte B (`BTest/BatteryTest`)
*   **Algoritmo Voraz (Escala 12)**: Validación de la correcta selección Greedy para longitudes grandes de 12 dígitos, controlando desbordamientos de tipos gracias al uso del tipo `Joltage` (ej. `"987654321111111"` -> `987654321111L`).
*   **Cálculo de suma total**: Comprobación de la suma correcta del conjunto extendido de baterías con longitud 12, esperando una suma de `3121910778619L`.
