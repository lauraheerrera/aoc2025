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

*   **Abstracción**: Oculta la lógica concreta de la validación y la manipulación de secuencias tras la interfaz `InvalidatableId`. La clase `GiftShop` y la clase `IdRange` dependen exclusivamente de esta abstracción, lo que permite desacoplar el procesamiento general de la lógica específica de cada parte del problema.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias aisladas para los modelos) y facilita su evolución o reutilización futura.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `GiftShop` es responsable únicamente de la agregación final, `IdRange` gestiona la generación de la secuencia en un rango y la suma de sus IDs inválidos, mientras que la validación de un identificador individual se delega a las implementaciones de `Id`.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. La interacción entre componentes se realiza a través de la interfaz `InvalidatableId`, lo que permite desacoplar el motor de suma de los IDs específicos. 
Asimismo, el flujo principal (`Main`) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.
*   **Diseño por contrato**: Se formalizan los acuerdos y expectativas mediante la interfaz `InvalidatableId`. La clase consumidora (`IdRange`) confía en que cualquier clase que implemente esta interfaz sabrá responder a `id()` y si cumple las condiciones de invalidez con `isInvalid()`, respetando el principio de mínima sorpresa y mínimo compromiso.
*   **Inmutabilidad del modelo**: Las clases del modelo se definen como **Records**, asegurando que sus instancias sean totalmente inmutables una vez creadas.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Id.java:L5-L16 (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/model/Id.java#L5-L16): Se limita exclusivamente a representar y validar la condición de invalidez de un ID individual para la parte A.
        *   [IdRange.java:L8-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L8-L26): Encapsula únicamente la lógica de generación del rango numérico y la suma de IDs inválidos dentro de dicho rango.
        *   [GiftShop.java:L10-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L10-L17): Agrega la suma total de todas las colecciones de rangos que componen la tienda.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [GiftShop.java:L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L10) y [IdRange.java:L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L10): Sus diseños son genéricos (`GiftShop<T extends InvalidatableId>`), lo que permite que el motor admita nuevas reglas de IDs (como los de la Parte B) extendiendo la clase mediante una nueva implementación sin modificar el código core del rango o de la tienda.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [Id.java (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/model/Id.java#L5) e [Id.java (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L5): Ambas clases son implementaciones completamente sustituibles e intercambiables bajo la interfaz común `InvalidatableId` sin alterar la corrección de la simulación.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [InvalidatableId.java:L3-L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/InvalidatableId.java#L3-L6): Define una interfaz minimalista de dos métodos (`id()` e `isInvalid()`), evitando obligar a las implementaciones concretas de dominio a arrastrar código innecesario.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [GiftShop.java:L10-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L10-L17): Depende enteramente del contrato abstracto `InvalidatableId`, logrando desacoplar por completo el procesamiento y suma del dominio real de la lógica concreta de validación.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [GiftShop.java:L12-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L12-L16) (`sumAllInvalidIds()`): Usa `ranges.stream()` para recorrer todas las colecciones de rangos del GiftShop, convirtiéndolas a valores numéricos con la suma de sus correspondientes IDs inválidos (`mapToLong(IdRange::sumInvalidIDs)`) y obteniendo el total global mediante `sum()`.
        *   [IdRange.java:L18-L24](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L18-L24) (`sumInvalidIDs()`): Emplea `LongStream.rangeClosed(start, end)` para generar de forma secuencial y eficiente todos los IDs del rango inclusive. Mapea cada ID al record `Id` del dominio, filtra los que son inválidos (`filter(InvalidatableId::isInvalid)`), extrae su valor primitivo y los suma (`sum()`).
        *   [Id.java:L26-L30 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L26-L30) (`hasRepeatedSequence()`): Utiliza `IntStream.rangeClosed(1, n / 2)` para probar todas las longitudes posibles de patrones. Filtra aquellas longitudes que dividen exactamente el tamaño total del ID (`validLength`) y evalúa si alguna de ellas compone un patrón repetitivo continuo con `anyMatch`.
        *   [Id.java:L36-L41 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L36-L41) (`isPatternRepeated()`): Usa `IntStream.iterate(len, i -> i + len)` para generar saltos secuenciales según la longitud del patrón, limitando las iteraciones a la cantidad exacta de bloques que quedan en la cadena, y comprueba con `allMatch` si todas las subcadenas coinciden exactamente con el prefijo inicial (el patrón).
        *   [Main.java (Parte A y B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/Main.java#L25-L28): Utiliza `Arrays.stream(lines.getFirst().split(","))` para procesar la primera línea de entrada. Mapea cada token quitando los espacios en blanco (`trim`), lo deserializa a un `IdRange` de IDs y colecta la estructura con `.toList()`.
*   **Inyección de dependencias**: 
    * *Definición*: Técnica de diseño que consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código.
    * *Implementación*: La creación de instancias de `Id` se delega externamente. La clase `IdRange` recibe su factoría de creación a través de su constructor (`LongFunction<T> idFactory`), reduciendo el acoplamiento y facilitando la reutilización del código.
*   **Genéricos**: El uso de clases parametrizadas como `GiftShop<T extends InvalidatableId>` y `IdRange<T extends InvalidatableId>` evita la duplicación de código (principio **DRY**) y los castings inseguros, garantizando que el sistema sea modular, tipado estáticamente y reutilizable.
*   **Good Naming**: Las clases, variables y métodos han sido nombrados con claridad semántica (`GiftShop`, `InvalidatableId`, `sumInvalidIDs()`, `hasRepeatedSequence()`), aumentando significativamente la legibilidad y la expresividad del código.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se utiliza la referencia al método estático `Id::create` (definido en [Id.java:L7-L9 (Parte A)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/a/model/Id.java#L7-L9) y [Id.java:L9-L11 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L9-L11)) inyectándose como factoría funcional en el flujo de ejecución principal en `Main.java`.
*   **Patrón Factory**:
    *   *Definición*: Patrón creacional que encapsula la lógica de creación de objetos en una clase dedicada, llamada factoría, la cual produce instancias de una familia de clases que comparten una interfaz o clase base común. Su propósito es abstraer el proceso de creación, centralizándolo.
    *   *Implementación*: Se utiliza la abstracción funcional [IdRange.java:L10-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L10-L15) mediante la interfaz genérica `LongFunction<T> idFactory`, lo que permite que el motor de rangos instancie dinámicamente subtipos del identificador sin conocer su constructor concreto.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Se utiliza mediante streams en [GiftShop.java:L13](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/GiftShop.java#L13) (`ranges.stream()`) y [IdRange.java:L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/model/IdRange.java#L19) (`LongStream.rangeClosed(...)`) para iterar y agregar secuencialmente los identificadores válidos de forma abstracta.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Se aplica a las expresiones lambda en [Id.java:L28-L29 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L28-L29) (`len -> validLength(n, len)` y `len -> isPatternRepeated(...)`) y [Id.java:L38-L40 (Parte B)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day02/b/model/Id.java#L38-L40) (`i -> i + len` e `i -> s.substring(...)`) para capturar las variables locales del contexto (`n`, `s` y `pattern`) y evaluar de forma inmutable la repetición de patrones.

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
