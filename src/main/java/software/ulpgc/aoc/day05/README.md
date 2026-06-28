# Day 5: Cafeteria

## Descripción

El desafío consiste en validar si una lista de identificadores numéricos (IDs) se encuentra dentro de ciertos rangos numéricos de validez (frescura).
1.  **Parte 1**: Calcular cuántos IDs de la lista caen dentro de al menos uno de los rangos válidos proporcionados (IDs frescos).
2.  **Parte 2**: Fusionar los rangos solapados o contiguos y calcular el número total de IDs que quedan representados por la suma de las longitudes de todos los rangos fusionados.

## Diagramas UML
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day05.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar y modelar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Oculta la complejidad del cálculo de frescura y la mezcla de intervalos en `FreshnessValidator`. Además, `Range` oculta la lógica de comparación y solapamiento de sus límites.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: Toda la manipulación de límites e intersecciones de un rango se gestiona de forma interna en el record `Range`.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo —como una clase o función— están relacionados entre sí y colaboran para cumplir una única tarea o propósito. Un módulo se considera altamente cohesivo cuando todas sus partes están directamente conectadas con la responsabilidad central que se le ha asignado, trabajando de forma coordinada hacia un objetivo común.
    *   *Implementación*: Cada componente tiene una única responsabilidad bien enfocada. El record `ID` solo representa el identificador y sus comparaciones, `Range` encapsula la lógica espacial de un intervalo individual, y `FreshnessValidator` procesa de forma exclusiva el cómputo de la validez global de una colección.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: Los componentes interactúan a través de interfaces y firmas limpias y mínimas. `ID` y `Range` no dependen de `FreshnessValidator`, lo que minimiza el impacto ante posibles cambios.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos de mezcla y validación de rangos se leer de forma declarativa, haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las clases de dominio `ID` y `Range` son Records inmutables. Toda la manipulación de rangos (como `merge()`) genera nuevas instancias sin alterar las existentes, garantizando la predictibilidad en la lógica de negocio.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `FreshnessValidator` se compone de una colección de `Range` sin necesidad de establecer jerarquías heredadas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Range.java:L5-L20](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java#L5-L20): Define exclusivamente las propiedades geométricas, límites y operaciones binarias de comparación de un intervalo de IDs.
            *   [ID.java:L5-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java#L5-L15): Su responsabilidad es modelar y representar un identificador individual de la cafetería.
            *   [FreshnessValidator.java:L10-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L10-L40): Encapsula de forma única el algoritmo de validación de frescura y la fusión lineal de intervalos solapados.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Range.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/Range.java#L5) e [ID.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/ID.java#L5): Implementan interfaces estándar como `Comparable`, lo que permite que algoritmos de ordenación externos ordenen sus colecciones de forma genérica sin alterar la definición original de las entidades.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtRangeDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtRangeDeserializer.java#L6) y [TxtIDDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtIDDeserializer.java#L6): Son perfectamente sustituibles bajo las firmas abstractas de `Deserializer<Range>` y `Deserializer<ID>` en cualquier cargador adaptado.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [TxtDatabaseLoader.java:L20-L24](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/io/TxtDatabaseLoader.java#L20-L24): El cargador no depende de deserializadores de texto planos fijos, sino de las interfaces genéricas `Deserializer<Range>` y `Deserializer<ID>`, permitiendo la inyección de dependencias para cambiar de forma externa la infraestructura física de los ficheros.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: La base de datos y validador actúan sobre los límites del record `Range` sin descender a evaluar de manera manual las partes o dígitos del ID primitivo subyacente.
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
*   **Implementación**: La firma `Deserializer<T>` define formalmente el contrato de deserialización del dominio.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [FreshnessValidator.java:L17-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L17-L22) (`countFresh()`): Utiliza `ids.stream()` para recorrer la lista de IDs de entrada y filtrarlos (`filter`) verificando si alguno de ellos está contenido en los rangos de frescura (`validRanges.stream().anyMatch(...)`). Cuenta los elementos válidos resultantes (`count()`).
        *   [FreshnessValidator.java:L24-L28](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L24-L28) (`countTotalFresh()`): Obtiene el stream de rangos ya consolidados (disjuntos y ordenados), mapea cada rango a su tamaño en elementos (`mapToLong(Range::length)`) y devuelve la suma total (`sum()`).
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: Inyección de los objetos deserializadores a través del constructor del cargador.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Utilización de la interfaz genérica parametrizada `Deserializer<T>`.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres explícitos como `contains()`, `mergeableWith()`, `merge()`, `countFresh()`, y `countTotalFresh()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
        *   *Implementación*: Se utiliza en [FreshnessValidator.java:L13-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java#L13-L15) con `FreshnessValidator.fromRanges()`.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
        *   *Implementación*: Aplicado mediante Java Streams en [FreshnessValidator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day05/model/FreshnessValidator.java) para iterar y filtrar secuencias de IDs y Rangos de forma abstracta.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).

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
