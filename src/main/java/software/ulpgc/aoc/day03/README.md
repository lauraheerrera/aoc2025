# Day 3: Lobby

## Descripción

El desafío consiste en encontrar la combinación de dígitos que maximice el voltaje producido por bancos de baterías. Cada banco está representado por una cadena de dígitos.
1.  **Parte 1**: Seleccionar exactamente **2** dígitos de cada banco para maximizar el valor.
2.  **Parte 2**: Seleccionar exactamente **12** dígitos de cada banco.

El objetivo final es sumar los voltajes máximos encontrados en todos los bancos de baterías proporcionados.

## Diagrama UML
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day03.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan conceptos del dominio mediante objetos de valor semánticos como `Joltage` y `Length` en lugar de usar tipos primitivos básicos, abstrayendo la complejidad de las operaciones y las reglas de validación asociadas.
*   **Encapsulamiento**: La lógica del algoritmo y las operaciones de manipulación de cadenas se ocultan en métodos privados (`buildMaxNumber`, `selectDigitAndRecurse`, `findMaxIndex`) de `BatteryBankMaxJoltageCalculator`. El exterior solo interactúa con el método público `calculate()`.
*   **Cohesión**: Cada clase tiene una única y clara responsabilidad: `BatteryBank` representa la entidad, `Joltage` y `Length` encapsulan los valores numéricos del dominio con sus reglas, `BatteryBankMaxJoltageCalculator` implementa el algoritmo para un banco individual, y `TotalBatteryJoltageCalculator` realiza la agregación final.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   `TotalBatteryJoltageCalculator` se compone de una referencia a `BatteryBankMaxJoltageCalculator` y al objeto de valor `Length`, evitando crear jerarquías de herencia rígidas para evaluar las distintas longitudes de la Parte A y B.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [BatteryBank.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java): Representa exclusivamente el modelo de datos de una batería individual.
        *   [BatteryBankMaxJoltageCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java): Encapsula en exclusiva la lógica del algoritmo Greedy para calcular el voltaje máximo de un banco individual.
        *   [TotalBatteryJoltageCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java): Responsable único de realizar la agregación de voltajes máximos de una lista de bancos.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [TotalBatteryJoltageCalculator.java:L10-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L10-L19): Está parametrizada mediante su constructor usando la abstracción de valor `Length`, permitiendo realizar la Parte A (longitud 2) y Parte B (longitud 12) sin modificar su código interno.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtBatteryBankDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/io/TxtBatteryBankDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<BatteryBank>`, pudiendo ser utilizada indistintamente por cualquier cargador de ficheros de texto.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [TotalBatteryJoltageCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java): Recibe sus dependencias (`BatteryBankMaxJoltageCalculator` y `Length`) desde el constructor (Inyección de Dependencias).
*   **Don’t Repeat Yourself (DRY)**:
    *   El acumulador `TotalBatteryJoltageCalculator` interactúa de forma directa con `BatteryBankMaxJoltageCalculator` y `Length`, sin requerir inspecciones profundas de la cadena de dígitos del banco de baterías.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**: Las instancias (`BatteryBank` y `BatteryBankMaxJoltageCalculator`) son completamente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios. 
*   **Programación funcional (con Java Streams)**: [TotalBatteryJoltageCalculator.java:L6-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/TotalBatteryJoltageCalculator.java#L6-L10) (`sumAllMaxJoltageFrom()`): Utiliza `batteryBanks.stream()` para recorrer declarativamente la lista de bancos de baterías, mapeando cada banco (`map`) a su voltaje máximo optimizado (calculado por la clase delegada) y reduciéndolos (`reduce`) con la suma de joltajes (`Joltage::add`) partiendo de `Joltage.ZERO`.
*   **Inyección de dependencias**: Se inyecta la instancia de `BatteryBankMaxJoltageCalculator` y el Value Object `Length` a través del constructor del acumulador `TotalBatteryJoltageCalculator`.
*   **Genéricos**: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar los tipos concretos de datos de entrada.
*   **Good Naming**: Nombres claros como `maxJoltageOfLength()`, `selectDigitAndRecurse()` y `sumAll()`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Se define y consume en [BatteryBank.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBank.java#L7-L9) mediante `BatteryBank.create()`.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos en `TotalBatteryJoltageCalculator` capturando el calculador y la longitud.
*   **Patrones de comportamiento**:
    *   **Iterator**: Se define y consume en [BatteryBankMaxJoltageCalculator.java:L13-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day03/model/BatteryBankMaxJoltageCalculator.java#L13-L18) mediante `Stream.iterate()`. Aunque no implementa la interfaz `Iterator`, el estado del recorrido se encapsula en un `Selector`, proporcionando operaciones equivalentes a `hasNext()` y `next()`, siendo la iteración controlada por `Stream.iterate()`

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
