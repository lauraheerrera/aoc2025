# Day 2: Gift Shop

## Descripción

El desafío consiste en validar identificadores numéricos dentro de rangos específicos. Los identificadores son considerados "inválidos" si cumplen con ciertos patrones de repetición.
1.  **Parte 1**: Un ID es inválido si consiste en una secuencia de dígitos repetida exactamente dos veces (ej: `1212`, `55`).
2.  **Parte 2**: Un ID es inválido si consiste en una secuencia de dígitos repetida *al menos* dos veces (ej: `123123123`, `111`).

El objetivo final es sumar todos los IDs inválidos encontrados en los rangos proporcionados.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day02.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se abstrae el comportamiento de validación a través de la interfaz `InvalidatableId`, que expone únicamente los métodos `id()` e `isInvalid()`. Esto permite que las clases encargadas de la computación agregada, como `GiftShop` e `IdRange`, dependan de una abstracción genérica sin acoplarse a las complejas reglas de validación específicas de las variantes A o B.
*   **Encapsulamiento**: La lógica matemática interna del cálculo de dígitos está definida de manera privada dentro de las implementaciones del record `Id`, ocultando la complejidad del análisis numérico.
*   **Cohesión**: Cada clase tiene una única y clara responsabilidad: `GiftShop` gestiona la agregación de múltiples rangos, `IdRange` calcula la suma de IDs inválidos dentro de un rango utilizando una factoría para su creación, y las clases concretas de `Id` se encargan de la validación matemática individual.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: La clase `GiftShop` se compone de una colección de `IdRange` y estos a su vez operan componiendo la abstracción `InvalidatableId`, evitando crear una jerarquía rígida de clases heredadas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Id.java: (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/model/Id.java): Se limita exclusivamente a representar y validar la condición de invalidez de un ID individual para la parte A.
        *   [IdRange.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java): Encapsula únicamente la lógica de generación del rango numérico y la suma de IDs inválidos dentro de dicho rango.
        *   [GiftShop.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java): Agrega la suma total de todas las colecciones de rangos que componen la tienda.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [GiftShop.java:L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L10) y [IdRange.java:L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L10): Sus diseños son genéricos (`GiftShop<T extends InvalidatableId>`), lo que permite que el motor admita nuevas reglas de IDs (como los de la Parte B) extendiendo la clase mediante una nueva implementación sin modificar el código core del rango o de la tienda.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [Id.java (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/model/Id.java#L5) e [Id.java (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L5): Ambas clases son implementaciones completamente sustituibles e intercambiables bajo la interfaz común `InvalidatableId` sin alterar la corrección de la simulación.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [InvalidatableId.java:L3-L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/InvalidatableId.java#L3-L6): Define una interfaz minimalista de dos métodos (`id()` e `isInvalid()`), evitando obligar a las implementaciones concretas de dominio a arrastrar código innecesario.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [GiftShop.java:L5-L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L5-L6): Depende enteramente del contrato abstracto `InvalidatableId`, logrando desacoplar por completo el procesamiento y suma del dominio real de la lógica concreta de validación.
*   **Don’t Repeat Yourself (DRY)**:
    *   La generación de rangos numéricos y la suma lógica se centralizan en la clase genérica `IdRange`, compartiéndose entre ambas partes sin duplicidad de algoritmos.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   La clase `IdRange` interactúa directamente con el objeto de tipo `InvalidatableId` mediante su método `isInvalid()`, sin intentar navegar por sus propiedades de caracteres o su representación interna en cadena.
*   **Principio de mínimo compromiso**:
    *   La interfaz `InvalidatableId` expone únicamente el ID primitivo y el indicador de validez `isInvalid()`, ocultando por completo las funciones de cálculo de subcadenas o patrones internos.



## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**:
    *   Las clases del modelo se definen como **Records**, asegurando que sus instancias sean totalmente inmutables una vez creadas.
*   **Diseño por contrato**:
    *   Se formalizan los acuerdos y expectativas mediante la interfaz `InvalidatableId`. La clase consumidora (`IdRange`) confía en que cualquier clase que implemente esta interfaz sabrá responder a `id()` y si cumple las condiciones de invalidez con `isInvalid()`.
*   **Programación funcional (con Java Streams)**:
    *   [GiftShop.java:L6-L12](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L6-L12) (`sumAllInvalidIds()`): Usa `ranges.stream()` para recorrer todas las colecciones de rangos del GiftShop, convirtiéndolas a valores numéricos con la suma de sus correspondientes IDs inválidos (`mapToLong(IdRange::sumInvalidIDs)`) y obteniendo el total global mediante `sum()`.
    *   [IdRange.java:L8-L14](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L8-L14) (`sumInvalidIDs()`): Emplea `LongStream.rangeClosed(start, end)` para generar de forma secuencial y eficiente todos los IDs del rango inclusive. Mapea cada ID al record `Id` del dominio, filtra los que son inválidos (`filter(InvalidatableId::isInvalid)`), extrae su valor primitivo y los suma (`sum()`).
    *   [Id.java:L31-L35 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L31-L35) (`hasRepeatedSequence()`): Utiliza `IntStream.rangeClosed(1, n / 2)` para probar todas las longitudes posibles de patrones. Filtra aquellas longitudes que dividen exactamente el tamaño total del ID (`validLength`) y evalúa si alguna de ellas compone un patrón repetitivo continuo con `anyMatch`.
*   **Inyección de dependencias**:
    *   Se crea una factoría para `Id` y se le pasa al constructor de `IdRange`, de esta forma el motor no sabe nada de `Id`.
*   **Genéricos**:
    *   El uso de clases parametrizadas como `GiftShop<T extends InvalidatableId>` y `IdRange<T extends InvalidatableId>` garantiza que el sistema sea modular, tipado estáticamente y reutilizable.
*   **Good Naming**:
    *   Semántica clara con nombres como `GiftShop`, `InvalidatableId`, `sumInvalidIDs()`, `hasRepeatedSequence()`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method (Método Fábrica)**: En las clases de dominio `Id` (tanto de la parte A como de la parte B), el constructor es privado y la creación de objetos se delega en el método estático `Id::create(long id)`.
    *   **Factory (Inyección de Factoría)**: Las clases comunes `TxtRangeDeserializer` e `IdRange` son completamente genéricas (`<T extends InvalidatableId>`) y no pueden instanciar `new T()`. Para resolver esto, reciben en su constructor una factoría funcional (`LongFunction<T> idFactory`). Desde la clase `Main` se inyecta la referencia al método factoría específico (`Id::create`). Así, la clase `IdRange` puede generar e instanciar dinámicamente el tipo de ID correspondiente a la parte que se esté ejecutando sin acoplarse a ella.
*   **Patrones funcionales**:
    *   **Closure**: Se aplica a las expresiones lambda en [Id.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java) para capturar las variables locales del contexto (`n`, `s` y `pattern`) y evaluar de forma inmutable la repetición de patrones.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar tanto la lógica individual de cada ID como la agregación correcta en la tienda en diversos escenarios.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day02/IOTest/TxtRangeDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/IOTest/TxtRangeDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day02/ATest/IDTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/IDTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day02/BTest/IDTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/BTest/IDTest.java)

### Escenarios validados

#### Parte A (`ATest/IDTest`)
*   **Conteo de dígitos**: Verificación de que `getDigitCount()` calcula correctamente el número de dígitos para valores positivos, negativos y cero (ej. `0` -> `1`, `123` -> `3`, `1000` -> `4`, `-456` -> `3`).
*   **Validación de IDs**: Verificación de que los IDs válidos (`123`, `1234`, `1122`) no se marcan como inválidos, y los inválidos con secuencias repetidas exactamente dos veces (`1212`, `446446`) son correctamente detectados.
*   **Cálculo de suma total**: Validación de que `GiftShop` realiza la suma correcta de todos los IDs inválidos presentes en el rango de ejemplo, esperando un resultado de `1227775554`.

#### Parte B (`BTest/IDTest`)
*   **Conteo de dígitos**: Validación similar a la Parte A para asegurar la correcta cuenta de dígitos.
*   **Validación de IDs (Secuencias repetidas)**: Comprobación de que IDs válidos (`123`, `1234`) se consideran válidos, e IDs inválidos con secuencias repetidas al menos dos veces (`1212`, `123123`, `111`, `121212`, `122122122`) son identificados correctamente como inválidos según las nuevas reglas de validación.
*   **Cálculo de suma total**: Comprobación de que la tienda realiza la suma correcta de los IDs inválidos con el conjunto de rangos extendido, validando el resultado esperado de `4174379265`.
