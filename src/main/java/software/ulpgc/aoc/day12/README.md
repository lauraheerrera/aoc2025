# Day 12: Christmas Tree Farm

## Descripción

El desafío consiste en validar si un conjunto de regalos navideños con formas irregulares bidimensionales (polyominoes) caben en diferentes regiones rectangulares debajo de árboles de Navidad, sin solaparse.
1.  **Parte 1 / Parte 2**: Calcular cuántas de las regiones especificadas pueden acomodar con éxito las cantidades de regalos solicitadas de cada forma. Dado que la entrada física del problema permite un atajo heurístico garantizado por sus límites, basta con verificar si el área acumulada de los regalos requeridos es menor o igual al área rectangular de la región del árbol.

## Diagramas UML

Modelo
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day12.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Oculta la complejidad espacial de los regalos y el cálculo acumulado en `Farm`. Además, `Region` oculta la lógica de validación de su espacio interior mediante el método `fits()`.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: El cálculo de las sumas de áreas geométricas e intersecciones de polyominoes está oculto tras la API pública de `Region`.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: Cada componente tiene una única responsabilidad bien enfocada. El record `Shape` solo representa las figuras y su área, `Region` encapsula la lógica geométrica de un árbol, y `Farm` procesa de forma exclusiva la validación agregada de las regiones válidas de la granja.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: Los componentes interactúan a través de interfaces y firmas limpias y mínimas. `Shape` y `Region` no dependen de `Farm`, lo que minimiza el impacto ante posibles cambios.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos de filtrado se lean de forma declarativa, haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las clases de dominio son Records inmutables. Toda la manipulación de datos garantiza la predictibilidad en la lógica de negocio.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `Farm` se compone de una colección de `Region`, evitando herencias o acoplamientos rígidos entre granjas y sus sectores.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Shape.java:L3](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Shape.java#L3): Define exclusivamente las propiedades de una figura de regalo individual.
            *   [Region.java:L6-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L6-L18): Su responsabilidad es modelar una región de árbol y determinar individualmente si caben los regalos.
            *   [Farm.java:L5-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Farm.java#L5-L11): Encapsula de forma única el recuento global de regiones válidas en la granja.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   La clase `Region` y `Shape` están cerradas para la modificación de lógica de parsing o entrada física; cualquier cambio en el formato de los ficheros afectará solo a los deserializadores externos.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtShapeDeserializer.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L5) y [TxtRegionDeserializer.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtRegionDeserializer.java#L8): Son perfectamente sustituibles bajo la firma abstracta de `Deserializer<T>`.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz genérica minimalista que expone un único método (`deserialize()`). Los deserializadores del Día 12 solo implementan este método específico.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   La carga de datos en el Main no depende de deserializadores acoplados físicamente, sino de la interfaz abstracta `Deserializer<T>` inyectada en el método `load()`.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: `Farm` interactúa directamente con `Region` para consultar si encaja la forma, sin examinar las dimensiones o límites específicos de la misma.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Region.java:L12-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/model/Region.java#L12-L15) (`fits()`): Utiliza `IntStream.range()` para iterar y multiplicar cantidades y áreas en paralelo, acumulando el área mediante `.sum()`.
        *   [TxtShapeDeserializer.java:L13-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day12/io/TxtShapeDeserializer.java#L13-L16) (`deserialize()`): Cuenta los caracteres `#` de las líneas del bloque de la figura usando `.flatMapToInt(String::chars).filter(c -> c == '#').count()`.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres de métodos limpios y descriptivos como `fits()`, `countRegionsThatFit()`, y `toBlocks()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).

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

