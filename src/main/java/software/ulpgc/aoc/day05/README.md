# Day 5: Cafeteria

## Descripción

El desafío consiste en validar si una lista de identificadores numéricos (IDs) se encuentra dentro de ciertos rangos numéricos de validez (frescura).
1.  **Parte 1**: Calcular cuántos IDs de la lista caen dentro de al menos uno de los rangos válidos proporcionados (IDs frescos).
2.  **Parte 2**: Fusionar los rangos solapados o contiguos y calcular el número total de IDs que quedan representados por la suma de las longitudes de todos los rangos fusionados.

## Diagramas UML

Modelo
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day05.png)

I/O
<p align="center">
  <img src="../../../../../../../UML%20diagrams/uml_day05_io.png" width="400">
</p>

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta la complejidad del cálculo de frescura y la mezcla de intervalos en `FreshnessValidator`. Además, `Range` oculta la lógica de comparación y solapamiento de sus límites.
*   **Modularidad**: Estructura el reto en paquetes independientes. El paquete `model` contiene la lógica pura de negocio, completamente aislada de la infraestructura de entrada/salida.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. El record `ID` solo representa el identificador y sus comparaciones, `Range` encapsula la lógica espacial de un intervalo individual, y `FreshnessValidator` procesa de forma exclusiva el cómputo de la validez global de una colección.
*   **Bajo acoplamiento**: Los componentes interactúan a través de interfaces y firmas limpias y mínimas. `ID` y `Range` no dependen de `FreshnessValidator`, lo que minimiza el impacto ante posibles cambios.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos de mezcla y validación de rangos se lean de forma declarativa, haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**: Las clases de dominio `ID` y `Range` son Records inmutables. Toda la manipulación de rangos (como `merge()`) genera nuevas instancias sin alterar las existentes, garantizando la predictibilidad en la lógica de negocio.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Range.java:L5-L20](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java#L5-L20): Define exclusivamente las propiedades geométricas, límites y operaciones binarias de comparación de un intervalo de IDs.
        *   [ID.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java#L5-L15): Su responsabilidad es modelar y representar un identificador individual de la cafetería.
        *   [FreshnessValidator.java:L10-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L10-L40): Encapsula de forma única el algoritmo de validación de frescura y la fusión lineal de intervalos solapados.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Range.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java#L5) e [ID.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java#L5): Implementan interfaces estándar como `Comparable`, lo que permite que algoritmos de ordenación externos ordenen sus colecciones de forma genérica sin alterar la definición original de las entidades.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtRangeDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtRangeDeserializer.java#L6) y [TxtIDDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtIDDeserializer.java#L6): Son perfectamente sustituibles bajo las firmas abstractas de `RangeDeserializer` y `IDDeserializer` en cualquier cargador adaptado.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [RangeLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/RangeLoader.java#L7-L9) e [IDLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/IDLoader.java#L7-L9): Están completamente segregadas, lo que permite que un cargador de base de datos (`TxtDatabaseLoader`) implemente de forma independiente la lectura de rangos o la lectura de identificadores.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [TxtDatabaseLoader.java:L20-L24](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtDatabaseLoader.java#L20-L24): El cargador no depende de deserializadores de texto planos fijos, sino de las interfaces `RangeDeserializer` y `IDDeserializer`, permitiendo la inyección de dependencias para cambiar de forma externa la infraestructura física de los ficheros.


## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [FreshnessValidator.java:L17-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L17-L22) (`countFresh()`): Utiliza `ids.stream()` para recorrer la lista de IDs de entrada y filtrarlos (`filter`) verificando si alguno de ellos está contenido en los rangos de frescura (`validRanges.stream().anyMatch(...)`). Cuenta los elementos válidos resultantes (`count()`).
        *   [FreshnessValidator.java:L24-L28](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L24-L28) (`countTotalFresh()`): Obtiene el stream de rangos ya consolidados (disjuntos y ordenados), mapea cada rango a su tamaño en elementos (`mapToLong(Range::length)`) y devuelve la suma total (`sum()`).
        *   [FreshnessValidator.java:L30-L34](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L30-L34) (`mergedRanges()`): Utiliza `validRanges.stream()` para ordenar los rangos por su inicio (`sorted()`) y realiza una reducción mutable con `collect(ArrayList::new, this::accumulate, List::addAll)` para fusionar linealmente los rangos solapados o contiguos en una nueva lista disjunta de forma inmutable.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `contains()`, `mergeableWith()`, `merge()`, `countFresh()`, y `countTotalFresh()` aseguran que el código sea autodocumentado.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se utiliza en [FreshnessValidator.java:L13-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L13-L15) con `FreshnessValidator.fromRanges()`. Este método estático encapsula la creación de la instancia del validador.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Aplicado mediante Java Streams en [FreshnessValidator.java:L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L18) (`ids.stream()`), [FreshnessValidator.java:L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L25) (`mergedRanges().stream()`) y [FreshnessValidator.java:L31](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L31) (`validRanges.stream()`) para iterar y filtrar secuencias de IDs y Rangos de forma abstracta.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la lógica de negocio del modelo en diversos escenarios.

### Rutas de las pruebas
* **Tests de Deserializacion**: [`src/test/java/test/Day05/IOTest/`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/IOTest/)
*   **Tests de Lógica de la Parte A**: [`src/test/java/test/Day05/ATest/ValidatorTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/ATest/ValidatorTest.java)
*   **Tests de Lógica de la Parte B**: [`src/test/java/test/Day05/BTest/ValidatorTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/BTest/ValidatorTest.java)

### Escenarios validados

#### Parte A (`ATest/ValidatorTest`)
*   **Validación de operaciones de rango**: Verificación directa de que `Range` calcula correctamente su longitud (`length()`), detecta solapamientos (`mergeableWith()`), realiza fusiones binarias (`merge()`) y compara rangos por su inicio.
*   **Conteo de IDs frescos**: Verificación de que `countFresh()` filtra y cuenta de forma precisa cuántos IDs caen en al menos un rango de validez.

#### Parte B (`BTest/ValidatorTest`)
*   **Fusión de rangos**: Validación de que `countTotalFresh()` mezcla correctamente todos los rangos solapados y contiguos de la entrada, descontando duplicidades y calculando la suma de elementos total.
