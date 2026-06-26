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

*   **Abstracción**: La lógica de conteo de caminos mediante recursión y memoización está completamente encapsulada dentro del record `Network`, ocultando los detalles del algoritmo al exterior.
*   **Modularidad**: Separación limpia entre la lógica de E/S (`io`) y las entidades operacionales del grafo (`model`).
*   **Alta cohesión**: `Device` modela únicamente un nodo del grafo con su nombre y sus conexiones de salida. `Network` es responsable exclusivo de construir la lista de adyacencia y resolver los recuentos de caminos.
*   **Bajo acoplamiento**: El punto de entrada (`Main`) depende de la interfaz `DeviceLoader` y no de ninguna implementación concreta de lectura de archivos.
*   **Inmutabilidad del modelo**: Las clases de datos clave del sistema (`Device` y `Network`) se implementan como **Records** inmutables en Java, garantizando la ausencia de efectos secundarios.
*   **Diseño por contrato**: Definición de interfaces cohesivas como `DeviceLoader` y el genérico `Deserializer<Device>`.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Device.java:L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Device.java#L5): Modela exclusivamente un nodo del grafo dirigido con su nombre y lista de dispositivos de salida.
        *   [Network.java:L8-L36](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L8-L36): Se limita únicamente a construir la lista de adyacencia a partir de los dispositivos y a resolver el conteo de caminos con memoización.
        *   [TxtDeviceDeserializer.java:L10-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/io/TxtDeviceDeserializer.java#L10-L18): Concentra exclusivamente el parseo de líneas de texto en la forma `nombre: salida1 salida2 ...` para producir instancias de `Device`.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las clases deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Network.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L8): La estrategia de recuento de caminos (DFS con memoización) puede refactorizarse o extenderse internamente sin alterar la interfaz pública `countPaths(String, String)` expuesta a los clientes.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los subtipos deben poder reemplazar a sus tipos base sin alterar el comportamiento.
    *   *Implementación*:
        *   [Main.java (a):L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L18): La implementación lambda de `DeviceLoader` puede sustituirse por cualquier otra implementación de la interfaz sin alterar el flujo principal del programa.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [DeviceLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/io/DeviceLoader.java#L7-L9): Define un único método cohesivo (`load()`), asegurando una interfaz funcional minimalista.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java (a):L17-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L17-L18): El punto de entrada depende de la interfaz genérica `Deserializer<Device>` y de `DeviceLoader` en lugar de implementaciones concretas, desacoplando la carga de datos de la lógica de negocio.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Network.java:L14-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L14-L17) (`toAdjMap()`): Construye la lista de adyacencia de forma declarativa con `devices.stream().collect(Collectors.toMap(...))`.
        *   [Network.java:L31-L33](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L31-L33) (`computeAndMemoize()`): Suma funcional de los caminos de todos los vecinos del nodo con `adjacentList.get(current).stream().mapToLong(...).sum()`.
*   **Inyección de dependencias**: El constructor de `Main` recibe la implementación de `DeviceLoader` y `Deserializer<Device>` como lambda en tiempo de ejecución.
*   **Genéricos**: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Device`.
*   **Good Naming**: Nombres autodescriptivos como `countPaths()`, `computeAndMemoize()`, `toAdjMap()`, `adjacentList`.

## Patrones de diseño

*   **Memoización (Programación Dinámica)**:
    *   *Implementación*: El método privado `countPaths(String current, String end, Map<String, Long> memo)` implementa un algoritmo de **búsqueda en profundidad (DFS) con memoización** sobre el grafo dirigido. Antes de calcular los caminos de un nodo, se consulta el `HashMap` de resultados cacheados (`memo`). Si el resultado ya fue calculado, se devuelve directamente sin recomputar los subproblemas. Esto reduce la complejidad de exponencial a lineal respecto al número de nodos, siendo esencial para redes con múltiples caminos convergentes.


*   **Consistencia con el estilo funcional**: El método es puramente funcional: dadas las mismas entradas siempre devuelve el mismo resultado sin efectos secundarios. La expresión ternaria refuerza visualmente esta naturaleza declarativa, expresando la lógica como una selección de valores en lugar de como una secuencia de instrucciones imperativas.
*   **Eliminación de cláusulas de guardia redundantes**: Cada condición actúa como una cláusula de guardia que descarta un caso base antes de llegar al caso recursivo. La estructura anidada hace explícita la prioridad de evaluación de forma compacta y sin necesidad de variables intermedias.

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

