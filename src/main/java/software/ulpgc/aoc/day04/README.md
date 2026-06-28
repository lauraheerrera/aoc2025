# Day 4: Printing Department

## Descripción

El desafío consiste en optimizar el acceso a rollos de papel (`@`) en una cuadrícula.
1.  **Parte 1**: Determinar rollos accesibles (menos de 4 vecinos) en estado inicial.
2.  **Parte 2**: Simulación iterativa de eliminación de rollos accesibles hasta convergencia.

El objetivo final es encontrar el número de rollos a los que se puede acceder tras la eliminación iterativa.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day04.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Oculta la complejidad de la navegación espacial y la verificación de límites tras la clase `Diagram` y el enum `Direction`.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La representación bidimensional del mapa de papel se oculta tras la interfaz del record `Diagram`, impidiendo la manipulación directa de la matriz interna de celdas.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: En nuestro diseño, `Diagram` representa el estado de la cuadrícula de forma inmutable, y `DiagramAnalyzer` calcula las reglas de accesibilidad sobre el estado actual.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io` para leer el fichero, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: Se utiliza el enum `Direction` (`NORTH`, `SOUTH_EAST`, etc.) para representar los vectores de movimiento de vecindad de forma clara, eliminando números mágicos y haciendo el código autoexplicativo.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. El record `Diagram` es la entidad estática y completamente inmutable (que solo representa la plantilla de celdas inicial y sus dimensiones). El estado que cambia a lo largo de la simulación (qué celdas se van despejando) se encapsula en el record `DiagramStatus`. Cuando se eliminan coordenadas accesibles mediante `withClearedCoordinates()`, no se recrea la entidad `Diagram` original; en su lugar, se devuelve un nuevo `DiagramStatus` que hace referencia al mismo `Diagram` inicial, garantizando un flujo inmutable y previniendo efectos secundarios.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: La simulación en `DiagramStatus` se compone por referencia del record `Diagram` en lugar de extender de él, manteniendo las responsabilidades aisladas.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Diagram.java:L12-L30](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L12-L30): Se enfoca de manera única e inmutable en contener y proporcionar acceso a la estructura bidimensional del tablero de juego.
            *   [DiagramAnalyzer.java:L12-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L12-L40): Contiene exclusivamente las reglas de accesibilidad de vecinos y la simulación iterativa espacial.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [DiagramAnalyzer.java:L27-L40](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L40): La lógica de la simulación se puede extender agregando nuevas restricciones espaciales o algoritmos de limpieza en el tablero sin necesidad de reescribir ni modificar la estructura interna de `Diagram`.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtDiagramDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/io/TxtDiagramDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Tile[]>`, siendo sustituible por cualquier otra clase de análisis de entrada sin romper la cohesión del cargador.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java:L18-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java#L18-L21): La ejecución en `Main` interactúa con la factoría genérica `LoaderFactory` y la interfaz `Deserializer<Tile[]>`, evitando acoplar el flujo central del programa a las dependencias de ficheros físicas.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: El analizador `DiagramAnalyzer` interactúa únicamente con `Diagram` y `Coordinate`, sin navegar de forma profunda por las estructuras del array bidimensional o la representación de caracteres primitiva.
*   **Principio de mínimo compromiso**:
    *   *Definición*: Una interfaz debe exponer sólo lo necesario para operar, ocultando detalles internos y reduciendo la dependencia entre módulos.
    *   *Implementación*: La interfaz de `Diagram` no permite la manipulación directa o destructiva de su matriz interna, exponiendo solo consultas y métodos inmutables de copia.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [DiagramAnalyzer.java:L27-L31](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L27-L31) (`findAccessibleCoordinates()`): Filtra de forma funcional todas las coordenadas del tablero a través del método `diagram.coordinates()`, reteniendo sólo aquellas que resultan ser accesibles (`filter(c -> isAccessible(diagram, c))`) y recolectándolas en una lista (`toList()`).
        *   [DiagramAnalyzer.java:L54-L58](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/DiagramAnalyzer.java#L54-L58) (`countAdjacentTargets()`): Procesa las 8 posibles direcciones a partir de `Direction.values()`, filtrando aquellas en las que hay un objetivo (`ROLL`) en la celda adyacente y contando su cantidad con `count()`.
*   **Clases internas**:
    *   *Definición*: Clases definidas dentro de otra clase, útiles para agrupar elementos relacionados. Aumentan la cohesión del diseño y se clasifican en internas estáticas e internas no estáticas.
    *   *Implementación*: Se define el enum privado `Direction` como una clase interna estática en `DiagramAnalyzer` para estructurar de forma altamente cohesiva los vectores de movimiento espacial de los vecinos de una celda.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres de variables y métodos descriptivos como `sumAllAccessibleRolls()`, `isInBounds()`, `withClearedCoordinates()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Constructores**:
        *   *Definición*: Son métodos especiales que permiten crear nuevas instancias de una clase. Su propósito es inicializar el objeto con un estado válido. Pueden ser privados para restringir y controlar la creación de objetos desde el exterior.
        *   *Implementación*: La clase `Diagram` define su constructor en [Diagram.java:L12-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L12-L16) para restringir la instanciación directa externa, garantizando el encapsulamiento de la cuadrícula.
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
        *   *Implementación*: Se define en [Diagram.java:L18-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day04/model/Diagram.java#L18-L22) con el método estático `Diagram.create()`, centralizando la instanciación e inicialización del tablero a través del clonado seguro de celdas.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
        *   *Implementación*: Se utiliza en las expresiones lambda en `DiagramAnalyzer` para encapsular estados inmutables en tiempo de ejecución.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del análisis espacial en la cuadrícula.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day04/IOTest/TxtDiagramDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/IOTest/TxtDiagramDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)

### Escenarios validados

#### Parte A (`ATest/DiagramAnalyzerTest`)
*   **Vecindad trivial**: Caso base con objetivos aislados para confirmar la detectabilidad básica.
*   **Reglas de límite**: Pruebas con bloques densos (ej. cuadrados de 4) para verificar que la regla de "menos de 4 vecinos" filtra correctamente los elementos interiores.
*   **Validación de límites y métodos de la cuadrícula**: Verificación del tamaño (`rows()` y `cols()`), los bordes (`isInBounds()`) y el lanzamiento de excepciones al intentar acceder a coordenadas fuera de límites.

#### Parte B (`BTest/DiagramAnalyzerTest`)
*   **Eliminación limpia de coordenadas**: Validación de que `withClearedCoordinates()` marca correctamente como `Tile.CLEARED` las posiciones dadas en el tablero sin modificar la instancia original.
*   **Simulación de convergencia**: Verificación de la convergencia y número acumulado de rollos removidos con el tablero de ejemplo, esperando un valor total de `43`.
