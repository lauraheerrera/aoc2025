# Day 5: Cafeteria

## Descripción

El desafío consiste en validar si una lista de identificadores numéricos (IDs) se encuentra dentro de ciertos rangos numéricos de validez (frescura).
1.  **Parte 1**: Calcular cuántos IDs de la lista caen dentro de al menos uno de los rangos válidos proporcionados (IDs frescos).
2.  **Parte 2**: Fusionar los rangos solapados o contiguos y calcular el número total de IDs que quedan representados por la suma de las longitudes de todos los rangos fusionados.

## Diagramas UML
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day05.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan los conceptos clave usando el objeto de valor `ID` que abstrae un valor identificador numérico con sus comparaciones y `Range` que abstrae un intervalo cerrado con límites de inicio y fin. La lógica matemática compleja de combinación de intervalos y validación de frescura se mantiene oculta para los clientes del dominio.
*   **Encapsulamiento**: El cálculo de solapamiento de intervalos, la verificación de contenencia y la fusión de rangos (`merge`) están encapsulados dentro de los métodos del record `Range`, garantizando que ninguna clase externa manipule los límites de los rangos para realizar estas operaciones.
*   **Cohesión**: Cada clase tiene una única y clara responsabilidad: `ID` gestiona la representación y la comparación básica de IDs, `Range` encapsula los datos y operaciones geométricas del intervalo individual, y `FreshnessValidator` se responsabiliza exclusivamente del algoritmo de reducción y mezcla de intervalos para validar la frescura de los datos.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   `FreshnessValidator` se compone de una colección de `Range` sin necesidad de establecer jerarquías heredadas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Range.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java): Define exclusivamente las propiedades geométricas, límites y operaciones binarias de comparación de un intervalo de IDs.
        *   [ID.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java): Su responsabilidad es modelar y representar un identificador individual de la cafetería.
        *   [FreshnessValidator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java): Encapsula de forma única el algoritmo de validación de frescura y la fusión lineal de intervalos solapados.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [Range.java:L3](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java#L3) e [ID.java:L3](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java#L3): Implementan interfaces estándar como `Comparable`, lo que permite que algoritmos de ordenación externos ordenen sus colecciones de forma genérica sin alterar la definición original de las entidades.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtRangeDeserializer.java:L7](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtRangeDeserializer.java#L7) y [TxtIDDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtIDDeserializer.java#L6): Son perfectamente sustituibles bajo las firmas abstractas de `Deserializer<Range>` y `Deserializer<ID>` en cualquier cargador adaptado.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [TxtDatabaseLoader.java:L20-L24](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtDatabaseLoader.java#L20-L24): El cargador no depende de deserializadores de texto planos fijos, sino de las interfaces genéricas `Deserializer<Range>` y `Deserializer<ID>`, permitiendo la inyección de dependencias para cambiar de forma externa la infraestructura física de los ficheros.
*   **Law of Demeter (LoD - Ley de Deméter)**: La base de datos y validador actúan sobre los límites del record `Range` sin descender a evaluar de manera manual las partes o dígitos del ID primitivo subyacente.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**: Tanto `ID` como `Range` son **Records** de Java estrictamente inmutables. Operaciones como la fusión de rangos devuelven una nueva instancia inmutable de `Range`, evitando cambios de estado colaterales indeseados y garantizando la consistencia multihilo de los datos.
*   **Programación funcional (con Java Streams)**:
    *   [FreshnessValidator.java:L11-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L11-L16) (`countFresh()`): Utiliza `ids.stream()` para recorrer la lista de IDs de entrada y filtrarlos (`filter`) verificando si alguno de ellos está contenido en los rangos de frescura (`validRanges.stream().anyMatch(...)`). Cuenta los elementos válidos resultantes (`count()`).
    *   [FreshnessValidator.java:L17-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L17-L22) (`countTotalFresh()`): Obtiene el stream de rangos ya consolidados (disjuntos y ordenados), mapea cada rango a su tamaño en elementos (`mapToLong(Range::length)`) y devuelve la suma total (`sum()`).
*   **Inyección de dependencias**: Inyección de los objetos deserializadores a través del constructor del cargador.
*   **Genéricos**:  Utilización de la interfaz genérica parametrizada `Deserializer<T>`.
*   **Good Naming**: Nombres explícitos como `contains()`, `mergeableWith()`, `merge()`, `countFresh()`, y `countTotalFresh()`.
    
## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Se utiliza en [FreshnessValidator.java:L13-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L13-L15) con `FreshnessValidator.fromRanges()`.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como `Range::length` y `FreshnessValidator::accumulate`.

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
