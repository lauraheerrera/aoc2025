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

*   **Abstracción**: Oculta la lógica compleja de cálculo voraz y recursivo tras la API de `BatteryBank` (`maxJoltageOfLength()`).
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias para los modelos) y facilita su mantenimiento.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `BatteryBank` se encarga exclusivamente de resolver el algoritmo de optimización matemática para un banco de baterías, y `BatteryBankMaxJoltageCalculator` realiza la agregación total.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de dominio.
*   **Código expresivo**: Se utiliza una aproximación recursiva con nombres de métodos declarativos (`selectDigitAndRecurse`, `findMaxIndex`), lo que permite que el algoritmo "Greedy" sea autoexplicativo, evitando bucles iterativos complejos y anidados.
*   **Inmutabilidad del modelo**: Las instancias (`BatteryBank` y `BatteryBankMaxJoltageCalculator`) son completamente inmutables una vez creadas. Sus estados internos no pueden ser modificados y la adición de nuevos bancos a través del calculador genera una nueva instancia independiente del mismo.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [BatteryBank.java:L5-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java#L5-L16): Representa exclusivamente el modelo de datos de una batería individual.
        *   [BatteryBankMaxJoltageCalculator.java:L8-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L8-L26): Encapsula en exclusiva la lógica del algoritmo Greedy para calcular el voltaje máximo de un banco individual.
        *   [TotalBatteryJoltageCalculator.java:L10-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L19): Responsable único de realizar la agregación de voltajes máximos de una lista de bancos.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [TotalBatteryJoltageCalculator.java:L10-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L19): Está parametrizada mediante su constructor usando la abstracción de valor `Length`, permitiendo realizar la Parte A (longitud 2) y Parte B (longitud 12) sin modificar su código interno.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtBatteryBankDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/io/TxtBatteryBankDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<BatteryBank>`, pudiendo ser utilizada indistintamente por cualquier cargador de ficheros de texto.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [BatteryBankLoader.java:L8-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/io/BatteryBankLoader.java#L8-L10): Expone únicamente el método `load()`, previniendo que los cargadores tengan dependencias con métodos no cohesivos.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [TotalBatteryJoltageCalculator.java:L10-L13](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L13): Recibe sus dependencias (`BatteryBankMaxJoltageCalculator` y `Length`) desde el constructor (Inyección de Dependencias), y el punto de entrada principal `Main` depende de las abstracciones del cargador y deserializador.


## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [TotalBatteryJoltageCalculator.java:L14-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L14-L18) (`sumAllMaxJoltageFrom()`): Utiliza `batteryBanks.stream()` para recorrer declarativamente la lista de bancos de baterías, mapeando cada banco (`map`) a su voltaje máximo optimizado (calculado por la clase delegada) y reduciéndolos (`reduce`) con la suma de joltajes (`Joltage::add`) partiendo de `Joltage.ZERO`.
        *   [BatteryBankMaxJoltageCalculator.java:L21-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L21-L25) (`findMaxIndex()`): Emplea `IntStream.rangeClosed(start, digitsNeeded)` para examinar los índices de los dígitos candidatos. Utiliza una reducción personalizada (`reduce((i, j) -> digits.charAt(i) >= digits.charAt(j) ? i : j)`) para encontrar de forma puramente funcional el índice del dígito con mayor valor numérico en el rango especificado.
*   **Inyección de dependencias**:
    * *Definición*: Técnica de diseño que consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código.
    * *Implementación*: Se inyecta la instancia de `BatteryBankMaxJoltageCalculator` y el Value Object `Length` a través del constructor del acumulador `TotalBatteryJoltageCalculator`, promoviendo la modularidad y el desacoplamiento de componentes.
*   **Good Naming**: Nombres autoexplicativos como `maxJoltageOfLength()`, `selectDigitAndRecurse()` y `sumAll()` aumentan la legibilidad y eliminan la necesidad de comentarios explicativos redundantes.
*   **Eliminación de la obsesión por los primitivos (Primitive Obsession)**: Se han eliminado los tipos primitivos en las firmas del modelo. En su lugar:
    *   Se utiliza el objeto de dominio `Joltage` en lugar de valores numéricos primitivos (`long`) para representar el voltaje de los bancos de baterías.
    *   Se utiliza el objeto de dominio `Length` en lugar de enteros planos (`int`) para representar y validar la longitud de la selección de dígitos.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se define y consume en [BatteryBank.java:L10-L12](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java#L10-L12) mediante `BatteryBank.create()`, abstrayendo la instanciación e inicialización del record del banco de baterías.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Aplicado mediante streams en [TotalBatteryJoltageCalculator.java:L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L15) (`batteryBanks.stream()`) y en [BatteryBankMaxJoltageCalculator.java:L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L22) (`IntStream.rangeClosed(...)`) para procesar secuencialmente colecciones e índices.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Empleado a través de lambdas y referencias a métodos en [TotalBatteryJoltageCalculator.java:L16-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L16-L17) (capturando `singleCalculator` y `length`) y en [BatteryBankMaxJoltageCalculator.java:L23](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L23) (capturando la cadena `digits`) para delegar lógica encapsulada en los pipelines funcionales.

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
