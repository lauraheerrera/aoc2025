# Day 8: Junction Box

## Descripción

El desafío consiste en modelar y conectar una red tridimensional de cajas de conexiones (`JunctionBox`) en un espacio tridimensional. Cada caja tiene coordenadas enteras `(x, y, z)`. Las conexiones posibles entre dos cajas se definen por su distancia euclídea al cuadrado.
1.  **Parte 1**: Conectar las cajas utilizando el algoritmo de Kruskal (Kruskal's MST algorithm) / estructura de conjuntos disjuntos (Disjoint Set) y, tras aplicar las primeras 1000 conexiones más cortas, calcular el producto del tamaño de los 3 componentes conexos (circuitos) más grandes.
2.  **Parte 2**: Determinar cuál es la última conexión que une todos los nodos en una única red conectada (árbol de expansión mínimo completo) y calcular el producto de las coordenadas `x` de los dos extremos de dicha conexión.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day08.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar y modelar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: El modelo matemático de distancias tridimensionales y de optimización de redes está encapsulado en `JunctionBox`, `Connection` y `Playground`, ocultando los detalles algorítmicos.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La lógica de búsqueda y mezcla de componentes conexos en `DisjointSet` se encapsula ocultando los arrays internos de padres y tamaños.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo —como una clase o función— están relacionados entre sí y colaboran para cumplir una única tarea o propósito. Un módulo se considera altamente cohesivo cuando todas sus partes están directamente conectadas con la responsabilidad central que se le ha asignado, trabajando de forma coordinada hacia un objetivo común.
    *   *Implementación*: Cada componente tiene un propósito claro. `DisjointSet` se encarga exclusivamente de la estructura Union-Find (con compresión de caminos y unión por tamaño), `Connection` representa un enlace ordenable por distancia, y `JunctionBox` es un nodo en el espacio 3D.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io`, que recibe una `Function<String, T>` de deserialización, evitando el acoplamiento a formatos de texto concretos.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: La construcción y ordenación de conexiones se formula de forma completamente declarativa y comprensible con Streams en `Playground`.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las entidades principales del modelo (`JunctionBox` y `Connection`) se implementan como **Records** inmutables en Java.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: La clase `Connection` compone las dos instancias de `JunctionBox` correspondientes a sus extremos, en lugar de recurrir a la herencia.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [DisjointSet.java:L6-L48](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/DisjointSet.java#L6-L48): Se encarga de forma exclusiva de la lógica de la estructura de conjuntos disjuntos (Union-Find con compresión de caminos).
            *   [Connection.java:L3-L12](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Connection.java#L3-L12): Modela una única conexión física 3D entre dos nodos y calcula su distancia euclídea al cuadrado.
            *   [JunctionBox.java:L3-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/JunctionBox.java#L3-L11): Record inmutable que almacena únicamente las coordenadas espaciales `(x, y, z)` de un nodo.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Playground.java:L9-L63](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L9-L63): Permite implementar y ejecutar simulaciones combinatorias de red personalizadas sin necesidad de alterar la lógica nuclear de los nodos (`JunctionBox`) o de conectividad (`DisjointSet`).
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que es sustituible por cualquier implementación de carga. El `TxtJunctionBoxDeserializer` implementa `Deserializer<JunctionBox>` de forma limpia, permitiéndose reemplazar por mock u otros sin romper el flujo principal.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/a/Main.java#L17-L19): El cliente (`Main`) depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<JunctionBox>` en lugar de una clase cargadora concreta.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: `Playground` interactúa con `Connection` y `JunctionBox` a través de sus métodos expuestos, sin navegar hacia los componentes internos de sus coordenadas.
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
*   **Implementación**: La firma de `Deserializer<JunctionBox>` define el contrato formal de lectura de nodos tridimensionales.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Playground.java:L18-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L18-L25) (`allConnections()`): Genera de forma declarativa todas las posibles conexiones combinatorias entre las cajas usando `IntStream.range` y `flatMap()`, ordenándolas por distancia euclídea de menor a mayor.
        *   [Playground.java:L32-L41](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day08/model/Playground.java#L32-L41) (`multiplyThreeLargestSizes()`): Procesa de forma funcional la lista de cajas de conexiones para mapear sus representantes (`map(ds::find)`), filtrar duplicados (`distinct()`), mapear a su tamaño (`map(ds::size)`), ordenar de forma descendente, y multiplicar los 3 primeros con `reduce()`.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: Inyección del deserializador en el cargador por constructor.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: `DisjointSet<T>` y `Deserializer<T>` para desacoplar el comportamiento de los tipos concretos de datos.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres explícitos como `multiplyThreeLargestCircuitSizesAfterConnecting()`, `lastConnectionCoordinatesProduct()`, `squaredDistanceTo()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
        *   *Implementación*: Uso exhaustivo de flujos y generadores en `Playground` para emparejar y ordenar todas las conexiones combinatorias de manera funcional y declarativa.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).

## Elección de diseño: Primitivos con orElse vs Optional

En la clase `Playground`, el método `findLastConnectionWithState` utiliza el operador `orElseThrow` para resolver la búsqueda de la última conexión de red:

*   **¿Por qué es mejor `orElseThrow`?**
    El problema garantiza matemáticamente que siempre habrá una última conexión que termine de conectar todos los nodos en una única red unificada. Al resolver el stream con `.orElseThrow()`, evitamos propagar un `Optional<Connection>` a lo largo del flujo de ejecución del Playground y simplificamos las llamadas del cliente en `Main`. Si por alguna anomalía en los datos de entrada el grafo no pudiera conectarse por completo, el sistema fallaría de inmediato (fail-fast), lo cual es el comportamiento ideal para detectar datos corruptos de entrada.

---

## Pruebas realizadas

Se han diseñado pruebas unitarias exhaustivas con **JUnit** y **AssertJ** para validar el comportamiento del motor de conjuntos disjuntos y del cálculo de distancias espaciales.

### Rutas de las pruebas
*   **Tests de Deserialización**: [TxtJunctionBoxDeserializerTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/IOTest/TxtJunctionBoxDeserializerTest.java)
*   **Tests de la Parte A**: [PlaygroundTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/ATest/PlaygroundTest.java)
*   **Tests de la Parte B**: [PlaygroundTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day08/BTest/PlaygroundTest.java)

### Escenarios validados

#### Deserialización (`IOTest/TxtJunctionBoxDeserializerTest`)
*   **Parseo de coordenadas**: Conversión exitosa de líneas de texto a objetos `JunctionBox` con eliminación de espacios en blanco.
*   **Gestión de errores**: Excepciones correspondientes (`IllegalArgumentException`) si la línea es nula o vacía, si el formato es inválido, y `NumberFormatException` si contiene valores no numéricos.

#### Parte A (`ATest/PlaygroundTest`)
*   **Distancia euclídea**: Verificación del método `squaredDistanceTo` en `JunctionBox` para determinar distancias al cuadrado en 3D de manera precisa.
*   **Conectividad de Conjuntos (`DisjointSet`)**: Testeo de la compresión de caminos, inicialización implícita, tamaño de componentes conexos, y unión exitosa/redundante.
*   **Cálculo de Circuitos**: Verificación del producto de los 3 circuitos más grandes con el ejemplo base, dando `40L`.

#### Parte B (`BTest/PlaygroundTest`)
*   **Conexión Final**: Verificación del producto de coordenadas `x` de la última conexión para unir la red completa, dando `25272L`.
