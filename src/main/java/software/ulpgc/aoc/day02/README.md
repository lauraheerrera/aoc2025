# Day 2: Gift Shop

## Descripción

El desafío consiste en validar identificadores numéricos dentro de rangos específicos. Los identificadores son considerados "inválidos" si cumplen con ciertos patrones de repetición.
1.  **Parte 1**: Un ID es inválido si consiste en una secuencia de dígitos repetida exactamente dos veces (ej: `1212`, `55`).
2.  **Parte 2**: Un ID es inválido si consiste en una secuencia de dígitos repetida *al menos* dos veces (ej: `123123123`, `111`).

El objetivo final es sumar todos los IDs inválidos encontrados en los rangos proporcionados.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day02a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day02b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta la lógica concreta de la validación y la manipulación de secuencias tras la interfaz `InvalidatableId`. La clase `GiftShop` y la clase `IdRange` dependen exclusivamente de esta abstracción, lo que permite desacoplar el procesamiento general de la lógica específica de cada parte del problema.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias aisladas para deserializadores y modelos) y facilita su evolución o reutilización futura.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `GiftShop` es responsable únicamente de la agregación final, `IdRange` gestiona la generación de la secuencia en un rango y la suma de sus IDs inválidos, mientras que la validación de un identificador individual se delega a las implementaciones de `Id`. La lectura de datos la gestionan `RangeLoader` y `TxtRangeDeserializer`.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. La interacción entre componentes se realiza a través de interfaces (`InvalidatableId`, `Deserializer`). `TxtRangeDeserializer` no conoce las clases concretas de `Id` sino que utiliza un `LongFunction<T>` inyectado como factoría, lo que permite que el deserializador sea completamente genérico y agnóstico a las reglas de validación.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.
*   **Diseño por contrato**: Se formalizan los acuerdos y expectativas mediante la interfaz `InvalidatableId`. La clase consumidora (`IdRange`) confía en que cualquier clase que implemente esta interfaz sabrá responder a `id()` y si cumple las condiciones de invalidez con `isInvalid()`, respetando el principio de mínima sorpresa y mínimo compromiso.
*   **Inmutabilidad del modelo**: Las clases del modelo se definen como **Records**, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que elimina efectos secundarios inesperados.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: La lógica de validación reside en las clases concretas `Id`, la generación del rango y conteo se maneja en `IdRange`, y el acumulado total en `GiftShop`. Por otra parte, la interpretación y carga del archivo de entrada se delega en `TxtRangeDeserializer` y `RangeLoader`.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La infraestructura de `GiftShop` e `IdRange` es genérica. Para soportar nuevas reglas de validación (como las de la Parte B), el diseño permite simplemente crear una nueva clase que implemente `InvalidatableId`, sin necesidad de modificar la lógica de procesamiento existente.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*: Las clases `software.ulpgc.aoc.day02.a.model.Id` y `software.ulpgc.aoc.day02.b.model.Id` son completamente intercambiables bajo la interfaz común `InvalidatableId`. El sistema funciona correctamente al inyectar cualquiera de ellas, garantizando una correcta jerarquía de tipos.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*: La interfaz `InvalidatableId` expone únicamente dos métodos esenciales (`id()` e `isInvalid()`). Es minimalista, está altamente cohesionada y no obliga a las implementaciones concretas a arrastrar código innecesario.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: La clase `GiftShop` y el deserializador `TxtRangeDeserializer` dependen de la interfaz `InvalidatableId`. Además, el deserializador depende de una abstracción funcional (`LongFunction<T>`) para instanciar los IDs, lo que permite inyectar cualquier factoría sin acoplamiento.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Inyección de dependencias**: La creación de instancias de `Id` se delega externamente. La clase `TxtRangeDeserializer` y `IdRange` reciben su factoría de creación a través de su constructor (`LongFunction<T> idFactory`), reduciendo el acoplamiento y facilitando la reutilización del código.
*   **Genéricos**: El uso de clases parametrizadas como `GiftShop<T extends InvalidatableId>` y `IdRange<T extends InvalidatableId>` evita la duplicación de código (principio **DRY**) y los castings inseguros, garantizando que el sistema sea modular, tipado estáticamente y reutilizable.
*   **Good Naming**: Las clases, variables y métodos han sido nombrados con claridad semántica (`GiftShop`, `InvalidatableId`, `sumInvalidIDs()`, `hasRepeatedSequence()`), aumentando significativamente la legibilidad y la expresividad del código.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se inyecta una referencia a método (`Id::create`) como `LongFunction<T>` en el deserializador. Esto actúa como una factoría dinámica que permite instanciar el tipo concreto de ID necesario en tiempo de ejecución, manteniendo el deserializador genérico.
*   **Patrón Iterator**:
    *   *Implementación*: Mediante **Java Streams** (específicamente `LongStream`), el sistema recorre y procesa los rangos de IDs abstrayendo el mecanismo de iteración subyacente de forma eficiente.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar tanto la lógica individual de cada ID como la agregación correcta en la tienda en diversos escenarios.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day02/ATest/TxtRangeDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/TxtRangeDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day02/ATest/IDTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/IDTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day02/BTest/IDTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/BTest/IDTest.java)

### Escenarios validados

#### Deserialización (`TxtRangeDeserializerTest`)
*   **Parseo correcto**: Validación de que cadenas de texto de rangos (ej. `"11-22"`, `"998-1012"`) se deserializan correctamente en objetos `IdRange` con sus respectivos valores de inicio y fin.
*   **Gestión de errores y robustez**:
    *   Lanzamiento de `IllegalArgumentException` ante entradas nulas, vacías, con espacios en blanco, o con formato inválido (ej. `"11"`, `"11-22-33"`).
    *   Lanzamiento de `NumberFormatException` cuando los límites del rango contienen caracteres no numéricos (ej. `"11-abc"`, `"xyz-22"`).

#### Parte A (`ATest/IDTest`)
*   **Conteo de dígitos**: Verificación de que `getDigitCount()` calcula correctamente el número de dígitos para valores positivos, negativos y cero (ej. `0` -> `1`, `123` -> `3`, `1000` -> `4`, `-456` -> `3`).
*   **Validación de IDs**: Verificación de que los IDs válidos (`123`, `1234`, `1122`) no se marcan como inválidos, y los inválidos con secuencias repetidas exactamente dos veces (`1212`, `446446`) son correctamente detectados.
*   **Cálculo de suma total**: Validación de que `GiftShop` realiza la suma correcta de todos los IDs inválidos presentes en el rango de ejemplo, esperando un resultado de `1227775554`.

#### Parte B (`BTest/IDTest`)
*   **Conteo de dígitos**: Validación similar a la Parte A para asegurar la correcta cuenta de dígitos.
*   **Validación de IDs (Secuencias repetidas)**: Comprobación de que IDs válidos (`123`, `1234`) se consideran válidos, e IDs inválidos con secuencias repetidas al menos dos veces (`1212`, `123123`, `111`, `121212`, `122122122`) son identificados correctamente como inválidos según las nuevas reglas de validación.
*   **Cálculo de suma total**: Comprobación de que la tienda realiza la suma correcta de los IDs inválidos con el conjunto de rangos extendido, validando el resultado esperado de `4174379265`.
