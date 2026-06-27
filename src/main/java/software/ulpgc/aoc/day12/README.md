# Day 12: Christmas Tree Farm

## Descripción

El desafío consiste en validar si un conjunto de regalos navideños con formas irregulares bidimensionales (polyominoes) caben en diferentes regiones rectangulares debajo de árboles de Navidad, sin solaparse.
1.  **Parte 1 / Parte 2**: Calcular cuántas de las regiones especificadas pueden acomodar con éxito las cantidades de regalos solicitadas de cada forma. Dado que la entrada física del problema permite un atajo heurístico garantizado por sus límites, basta con verificar si el área acumulada de los regalos requeridos es menor o igual al área rectangular de la región del árbol.

## Diagramas UML

Modelo
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day12.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta la complejidad espacial de los regalos y el cálculo acumulado en `Farm`. Además, `Region` oculta la lógica de validación de su espacio interior mediante el método `fits()`.
*   **Modularidad**: Estructura el reto en paquetes independientes. El paquete `model` contiene la lógica pura de negocio, completamente aislada de la infraestructura de entrada/salida y parsing.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. El record `Shape` solo representa las figuras y su área, `Region` encapsula la lógica geométrica de un árbol, y `Farm` procesa de forma exclusiva la validación agregada de las regiones válidas de la granja.
*   **Bajo acoplamiento**: Los componentes interactúan a través de interfaces y firmas limpias y mínimas. `Shape` y `Region` no dependen de `Farm`, lo que minimiza el impacto ante posibles cambios.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos de filtrado se lean de forma declarativa, haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**: Las clases de dominio son Records inmutables. Toda la manipulación de datos garantiza la predictibilidad en la lógica de negocio.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Shape.java:L3](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Shape.java#L3): Define exclusivamente las propiedades de una figura de regalo individual.
        *   [Region.java:L6-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L6-L18): Su responsabilidad es modelar una región de árbol y determinar individualmente si caben los regalos.
        *   [Farm.java:L5-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Farm.java#L5-L11): Encapsula de forma única el recuento global de regiones válidas en la granja.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   La clase `Region` y `Shape` están cerradas para la modificación de lógica de parsing o entrada física; cualquier cambio en el formato de los ficheros afectará solo a los deserializadores externos.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtShapeDeserializer.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L5) y [TxtRegionDeserializer.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtRegionDeserializer.java#L8): Son perfectamente sustituibles bajo la firma abstracta de `Deserializer<T>`.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`). Los deserializadores del Día 12 solo implementan este método específico.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   La carga de datos en el Main no depende de deserializadores acoplados físicamente, sino de la interfaz abstracta `Deserializer<T>` inyectada en el método `load()`.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Region.java:L12-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L12-L15) (`fits()`): Utiliza `IntStream.range()` para iterar y multiplicar cantidades y áreas en paralelo, acumulando el área mediante `.sum()`.
        *   [TxtShapeDeserializer.java:L13-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L13-L16) (`deserialize()`): Cuenta los caracteres `#` de las líneas del bloque de la figura usando `.flatMapToInt(String::chars).filter(c -> c == '#').count()`.
*   **Good Naming**: Nombres de métodos limpios y descriptivos como `fits()`, `countRegionsThatFit()`, y `toBlocks()`.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la lógica de negocio del modelo en diversos escenarios.

### Rutas de las pruebas
* **Tests de Deserializacion**: [`IOTest`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day12/IOTest/)
*   **Tests de la Parte A**: [`FarmTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day12/ATest/FarmTest.java)

### Escenarios validados

#### Deserialización (`IOTest`)
*   **Deserialización de figuras**: Validación de que `TxtShapeDeserializer` parsea correctamente los bloques de texto de las figuras de regalos y calcula el área total de sus celdas `#`.
*   **Deserialización de regiones**: Validación de que `TxtRegionDeserializer` decodifica las dimensiones y los requerimientos numéricos de regalos, gestionando correctamente la presencia de tabulaciones y espacios en blanco irregulares en la entrada.

#### Parte A (`ATest/FarmTest`)
*   **Validación de ajuste de región**: Verificación de que `Region.fits()` calcula de manera precisa el área consumida por las cantidades de regalos solicitadas y confirma si cabe en el espacio del árbol.
*   **Conteo de regiones válidas**: Validación de que `Farm.countRegionsThatFit()` filtra y calcula de forma acumulada el número total de regiones que admiten su combinación correspondiente de regalos.

