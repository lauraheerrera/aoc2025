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

*   **Abstracción**: Se abstrae el concepto de un grafo de red usando `Device` (que modela un nodo del grafo con su nombre y lista de salidas) y `Network` (que modela la topología global como un mapa de adyacencias). Los detalles algorítmicos de navegación y conteo recursivo no se exponen al exterior.
*   **Encapsulamiento**: La complejidad de la representación en mapa de adyacencia se encapsula al instanciar la red mediante su constructor sobrecargado `Network(List<Device>)` que llama al método privado `toAdjMap`. Además, el almacenamiento caché (`memo`) y la recursión memoizada se manejan privadamente en la clase `Network`, exponiendo únicamente la API pública `countPaths(start, end)`.
*   **Cohesión**: Cada clase tiene una responsabilidad única y atómica: `Device` se encarga de representar el nodo individual y sus propiedades físicas, mientras que `Network` se responsabiliza exclusivamente del grafo de conexiones y el algoritmo para el cálculo recursivo de caminos factibles.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.


## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**: `Network` se compone de una colección de `Device` mapeada como una lista de adyacencia sin recurrir a herencia de grafos.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Device.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Device.java): Modela exclusivamente un nodo del grafo dirigido con su nombre y lista de dispositivos de salida.
        *   [Network.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java): Se limita únicamente a construir la lista de adyacencia a partir de los dispositivos y a resolver el conteo de caminos con memoización.
        *   [TxtDeviceDeserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/io/TxtDeviceDeserializer.java): Concentra exclusivamente el parseo de líneas de texto para producir instancias de `Device`.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [Network.java:L8](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L8): La estrategia de recuento de caminos (DFS con memoización) puede refactorizarse o extenderse internamente sin alterar la interfaz pública `countPaths(String, String)` expuesta a los clientes.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [Main.java (a):L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L18): La factoría `LoaderFactory` devuelve un `TxtLoader<T>` genérico que puede sustituirse por cualquier otra implementación de carga sin alterar el flujo principal del programa.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista que expone un único método (`deserialize()`).
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java (a):L17-L18](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/a/Main.java#L17-L18): El punto de entrada depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Device>` en lugar de implementaciones concretas, desacoplando la carga de datos de la lógica de negocio.
*   **Law of Demeter (LoD - Ley de Deméter)**: La red `Network` opera sobre las propiedades del record `Device` de manera directa sin encadenar llamadas profundas sobre sus atributos de salidas.

## Técnicas de diseño aplicadas
*   **Inmutabilidad del modelo**: Las clases principales (`Device` y `Network`) están implementadas como **Records** inmutables en Java. Aunque la caché de memoización interna (`memo`) es una estructura mutable temporal por motivos de eficiencia, esta se instancia y se limita estrictamente al ámbito de la ejecución del método de cálculo, manteniendo inalterados los datos de la red.
*   **Programación funcional (con Java Streams)**:
    *   [Network.java:L14-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L14-L17) (`toAdjMap()`): Construye la lista de adyacencia de forma declarativa con `devices.stream().collect(Collectors.toMap(...))`.
    *   [Network.java:L31-L33](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day11/model/Network.java#L31-L33) (`computeAndMemoize()`): Suma funcional de los caminos de todos los vecinos del nodo con `adjacentList.get(current).stream().mapToLong(...).sum()`.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe la función de deserialización como parámetro en tiempo de ejecución, inyectándola en el `TxtLoader` genérico.
*   **Genéricos**: Uso de la interfaz parametrizada `Deserializer<T>` para la entidad `Device`.
*   **Good Naming**: Nombres autodescriptivos como `countPaths()`, `computeAndMemoize()`, `toAdjMap()`, `adjacentList`.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Uso de métodos de creación estáticos como `Network.fromDevices()` para construir redes sin necesidad de acceder a su constructor.
*   **Patrones funcionales**:
    *   **Closure**: Empleado a través de lambdas y referencias a métodos como en `countPaths()`.
    *   **Memoization**: El método privado `countPaths(String current, String end, Map<String, Long> memo)` implementa un algoritmo de **búsqueda en profundidad (DFS) con memoización** sobre el grafo dirigido, cacheando los resultados parciales en un `HashMap`.

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

