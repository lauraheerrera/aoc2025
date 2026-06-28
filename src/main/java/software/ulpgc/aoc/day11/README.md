# Day 11: Reactor

## Descripción

El desafío consiste en contar rutas dirigidas a través de una red de dispositivos (`Network`), donde cada dispositivo (`Device`) tiene un nombre y una lista de salidas hacia otros dispositivos, formando un grafo dirigido acíclico (DAG).
1.  **Parte 1**: Contar el número total de caminos distintos que existen desde el dispositivo `"you"` hasta el dispositivo `"out"` recorriendo la red.
2.  **Parte 2**: Calcular la suma de dos flujos de rutas posibles que comparten nodos intermedios fijos (`svr`, `dac`, `fft`) pero con ordenaciones alternativas, multiplicando las rutas parciales en cada segmento del grafo.

## Diagramas UML

| Partes A y B |
| :---: |
| ![Diagrama UML](../../../../../../../UML%20diagrams/uml_day11.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: La lógica de conteo de caminos mediante recursión y memoización está completamente encapsulada dentro del record `Network`, ocultando los detalles del algoritmo al exterior.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: La lista de adyacencia y el almacenamiento en caché (`memo`) se ocultan a los clientes de la red.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: `Device` modela únicamente un nodo del grafo con su nombre y sus conexiones de salida. `Network` es responsable exclusivo de construir la lista de adyacencia y resolver los recuentos de caminos.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: El punto de entrada (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io` y no depende de ninguna implementación concreta de lectura de archivos.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: El mapeo de adyacencias y sumas de vecinos se resuelven de manera declarativa y comprensible mediante la API de Streams en Java.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Las clases de datos clave del sistema (`Device` y `Network`) se implementan como **Records** inmutables en Java, garantizando la ausencia de efectos secundarios.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `Network` se compone de una colección de `Device` mapeada como una lista de adyacencia sin recurrir a herencia de grafos.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Device.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Device.java#L5): Modela exclusivamente un nodo del grafo dirigido con su nombre y lista de dispositivos de salida.
            *   [Network.java:L8-L36](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L8-L36): Se limita únicamente a construir la lista de adyacencia a partir de los dispositivos y a resolver el conteo de caminos con memoización.
            *   [TxtDeviceDeserializer.java:L10-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/io/TxtDeviceDeserializer.java#L10-L18): Concentra exclusivamente el parseo de líneas de texto para producir instancias de `Device`.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Network.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L8): La estrategia de recuento de caminos (DFS con memoización) puede refactorizarse o extenderse internamente sin alterar la interfaz pública `countPaths(String, String)` expuesta a los clientes.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [Main.java (a):L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L18): La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que puede sustituirse por cualquier otra implementación de carga sin alterar el flujo principal del programa.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java (a):L17-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L17-L18): El punto de entrada depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Device>` en lugar de implementaciones concretas, desacoplando la carga de datos de la lógica de negocio.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: La red `Network` opera sobre las propiedades del record `Device` de manera directa sin encadenar llamadas profundas sobre sus atributos de salidas.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Network.java:L14-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L14-L17) (`toAdjMap()`): Construye la lista de adyacencia de forma declarativa con `devices.stream().collect(Collectors.toMap(...))`.
        *   [Network.java:L31-L33](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L31-L33) (`computeAndMemoize()`): Suma funcional de los caminos de todos los vecinos del nodo con `adjacentList.get(current).stream().mapToLong(...).sum()`.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: La factoría `LoaderFactory` recibe la función de deserialización como parámetro en tiempo de ejecución, inyectándola en el `TxtLoader` genérico.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Device`.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres autodescriptivos como `countPaths()`, `computeAndMemoize()`, `toAdjMap()`, `adjacentList`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   **Memoization**:
        *   *Definición*: Técnica de optimización que consiste en almacenar los resultados de llamadas a funciones costosas y devolver el resultado almacenado en caché cuando se vuelven a producir las mismas entradas.
        *   *Implementación*: El método privado `countPaths(String current, String end, Map<String, Long> memo)` implementa un algoritmo de **búsqueda en profundidad (DFS) con memoización** sobre el grafo dirigido, cacheando los resultados parciales en un `HashMap`.

---

## Pruebas realizadas

Se han diseñado pruebas unitarias robustas utilizando **JUnit** y **AssertJ** para certificar el algoritmo de conteo de caminos con memoización y el parseo de dispositivos.

### Rutas de las pruebas
*   **Tests de Deserialización**: [TxtDeviceDeserializerTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day11/IOTest/TxtDeviceDeserializerTest.java)
*   **Tests de la Parte A**: [NetworkTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day11/ATest/NetworkTest.java)
*   **Tests de la Parte B**: [NetworkTest.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day11/BTest/NetworkTest.java)

### Escenarios validados

#### Deserialización (`TxtDeviceDeserializerTest`)
*   **Parseo con múltiples salidas**: Verificación de que `"you: bbb ccc"` produce un `Device` con nombre `"you"` y salidas `["bbb", "ccc"]`.
*   **Parseo con una sola salida**: Verificación de que `"zqk: svb"` produce un `Device` con la única salida `"svb"`.
*   **Parseo sin salidas**: Verificación de que `"iii:"` produce un `Device` con lista de salidas vacía.
*   **Gestión de errores**: Comprobación de que lanza `IllegalArgumentException` ante entradas vacías o nulas.

#### Parte A (`NetworkTest`)
*   **Conteo de caminos simple**: Verificación de que el número de caminos distintos desde `"you"` hasta `"out"` en la red de ejemplo es exactamente `5L`.

#### Parte B (`NetworkTest`)
*   **Conteo de rutas con nodos intermedios**: Verificación de que la suma de los dos flujos de rutas alternativas a través de los nodos intermedios `"svr"`, `"dac"`, `"fft"` y `"out"` da como resultado `6L`.

