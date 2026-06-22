# Day 5: Cafeteria

## Descripción

El desafío consiste en validar si una lista de identificadores numéricos (IDs) se encuentra dentro de ciertos rangos numéricos de validez (frescura).
1.  **Parte 1**: Calcular cuántos IDs de la lista caen dentro de al menos uno de los rangos válidos proporcionados (IDs frescos).
2.  **Parte 2**: Fusionar los rangos solapados o contiguos y calcular el número total de IDs que quedan representados por la suma de las longitudes de todos los rangos fusionados.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day05a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day05b.png) |

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
    *   *Implementación*: La lógica de validación de pertenencia e intervalos reside exclusivamente en `Range` e `ID`, mientras que la agregación y el cálculo global corresponden a `FreshnessValidator`.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: `Range` y `ID` implementan interfaces estándar de Java como `Comparable`, permitiendo extender las políticas de ordenación y clasificación sin modificar su estructura original.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*: Empleado en `FreshnessValidator` para filtrar IDs válidos y reducir/acumular rangos ordenados de forma declarativa e inmutable.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `contains()`, `mergeableWith()`, `merge()`, `countFresh()`, y `countTotalFresh()` aseguran que el código sea autodocumentado.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se utiliza `FreshnessValidator.fromRanges()`. Este método estático encapsula la complejidad de la creación de la instancia del validador, ofreciendo una API limpia y expresiva en el punto de uso.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Mediante **Java Streams**, el validador recorre y procesa las secuencias de IDs y Rangos abstrayendo el mecanismo de iteración subyacente.

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
