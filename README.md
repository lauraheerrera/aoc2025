# Advent of Code 2025

Este repositorio contiene las soluciones en Java para los desafíos de **Advent of Code 2025**, desarrolladas siguiendo principios de diseño de software avanzados como **Clean Architecture**, principios **SOLID** y modularización orientada al dominio.

## Estructura del proyecto

El proyecto está estructurado como un proyecto Maven estándar de Java 21, organizado de la siguiente manera:
- **[src/main/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java)**: Contiene la lógica del negocio de cada día (desglosado por paquetes `dayXX`), modelos de dominio, cargadores y deserializadores de entrada, además de la lógica común reutilizable bajo `common`.
- **[src/test/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/test/java)**: Tests unitarios exhaustivos para validar las soluciones utilizando JUnit 4 y AssertJ.
- **[UML diagrams](https://github.com/lauraheerrera/aoc2025/tree/master/UML%20diagrams)**: Diagramas UML para modelar las clases.

---

## Arquitectura y diseño

Este proyecto ha sido diseñado siguiendo estándares de ingeniería de software, priorizando la mantenibilidad, extensibilidad y claridad del código. La estructura se divide en módulos que separan la infraestructura técnica de la lógica de negocio.

### Fundamentos de diseño

La arquitectura global se cimienta sobre pilares fundamentales:
- **Modularidad**: El sistema se organiza en un núcleo común ([common](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/common)) y módulos independientes para cada reto (`dayXX`). Esto permite que el crecimiento del proyecto no aumente la complejidad de navegación.
- **Abstracción**: Se utiliza la interfaz genérica [Deserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java) para ocultar los detalles del parseo de datos. El resto del sistema interactúa con esta abstracción, desacoplándose del formato de entrada (texto, binario, etc.).
- **Bajo acoplamiento**: Los puntos de entrada (`Main`) no dependen de implementaciones concretas de lectura de archivos, sino de interfaces (como `Loader`), lo que facilita el intercambio de fuentes de datos.
- **Alta cohesión**: Cada componente tiene una única responsabilidad bien definida: los modelos gestionan la lógica, los deserializadores el parseo y los cargadores el acceso a datos.
- **Diseño por contrato**: Se formalizan los acuerdos de comportamiento entre los componentes mediante interfaces claras y acotadas (como `Deserializer` y `Loader`), lo que asegura contratos de entrada/salida limpios.
- **Inmutabilidad del modelo**: Las clases de dominio (records de Java como `Order` e `Id`) se diseñan con inmutabilidad estructural para evitar efectos secundarios y asegurar la predictibilidad del flujo de datos.

### Principios aplicados

- **SOLID**:
    *   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
        *   *Definición*: Cada clase o módulo debe tener una única razón para cambiar.
        *   *Implementación*: Se divide claramente la lógica de negocio (los modelos del dominio), la lectura de archivos de entrada (cargadores) y el análisis de cadenas (deserializadores).
    *   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
        *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
        *   *Implementación*: A través del uso de tipos genéricos e interfaces, podemos crear e inyectar nuevas variantes de `Loader` o `Deserializer` sin alterar la infraestructura de procesamiento genérica del núcleo.
    *   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
        *   *Definición*: Los objetos de un programa deberían ser reemplazables por instancias de sus subtipos sin alterar el correcto funcionamiento del programa.
        *   *Implementación*: Cualquier implementación concreta de las interfaces `Deserializer` o `Loader` puede sustituir a la actual sin alterar el comportamiento esperado por las clases consumidoras como `Main`.
    *   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*: Las interfaces del sistema son altamente específicas y cohesivas. Por ejemplo, `Loader` y `Deserializer` definen un único método preciso (`load()` y `deserialize()` respectivamente), evitando forzar a las clases implementadoras a arrastrar métodos innecesarios.
    *   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
        *   *Definición*: Depender de abstracciones, no de concreciones.
        *   *Implementación*: Las clases consumidoras y los cargadores dependen de abstracciones (interfaces como `Loader` o `Deserializer`), reduciendo las dependencias directas entre componentes.
- **DRY (Don't Repeat Yourself)**: La lógica de lectura de archivos se ha centralizado en [TxtLoader.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/TxtLoader.java), evitando duplicidad de código en el manejo de `BufferedReader`.
- **YAGNI (You Aren't Gonna Need It)**: Se han implementado las soluciones de forma sencilla, sin añadir complejidad innecesaria más allá de lo requerido por el problema.

### Técnicas de diseño aplicadas

*   **Inyección de dependencias**: 
    * *Definición*: Técnica de diseño que consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código.
    * *Implementación*: La creación y provisión de dependencias se desacopla de su uso, pasándolas dinámicamente a través de constructores (por ejemplo, inyectando la factoría de IDs o cargadores en tiempo de ejecución).
*   **Genéricos**: La infraestructura de entrada/salida está parametrizada con tipos genéricos para promover la reutilización de código libre de castings manuales.
*   **Good Naming**: Se priorizan nombres de clases y métodos autoexplicativos que actúan como documentación viva del código.

### Patrones de diseño

*   **Patrón Factory Method**:
    *   *Implementación*: La clase [LoaderFactory.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/LoaderFactory.java) encapsula la creación compleja de cargadores, simplificando la creación de objetos desde el cliente.
*   **Patrón Iterator**:
    *   *Implementación*: Uso sistemático de **Java Streams** para recorrer secuencialmente las colecciones de datos abstrayendo los mecanismos de control de bucles.

### Patrones y técnicas no aplicadas

Siguiendo los principios **YAGNI** (You Aren't Gonna Need It) y **KISS** (Keep It Simple, Stupid), se descartó intencionadamente el uso de ciertos patrones y técnicas comunes para evitar sobreingeniería y complejidad innecesaria en las soluciones:

*   **Patrón Singleton**: Al ser un proyecto de resolución de algoritmos por lotes, no existe estado mutable global compartido ni servicios compartidos de red o base de datos. Toda la lógica se maneja de forma funcional o instanciando modelos inmutables, evitando el acoplamiento global innecesario y efectos secundarios.
*   **Patrón Builder**: Los modelos de datos y records de cada día (`JunctionBox`, `Point`, `Dial`, `Machine`) son sencillos y tienen pocos atributos. Sus constructores estándar o métodos factoría son suficientes, evitando la verbosidad y el código repetitivo que introduce un Builder.
*   **Patrón Command**: Las órdenes e instrucciones se modelan directamente como tipos del dominio (por ejemplo, `Order` en el Día 1 o `Operator` en el Día 6) y se resuelven en pipelines lineales. Al no requerir colas de ejecución dinámicas, programadores o soporte para deshacer/rehacer, este patrón no aportaba valor real.
*   **Patrones Observer / Listener**: Las soluciones se ejecutan de manera secuencial y por lotes (procesando ficheros de entrada a salida estándar). Al carecer de interfaces de usuario (GUI) u operaciones reactivas orientadas a eventos en tiempo real, no se requiere la complejidad de suscripciones asíncronas.
*   **Patrón Adapter**: Debido a que todo el sistema y sus modelos se construyeron de forma nativa desde cero bajo nuestro propio diseño e interfaces comunes, no fue necesario integrar librerías externas o APIs incompatibles que requirieran adaptadores.
*   **Patrón Decorador**: Las variaciones de comportamiento para la segunda parte de cada día se resolvieron mediante parametrización o implementando las interfaces base directamente (como en el Día 2). No se requirió añadir responsabilidades dinámicas envolviendo objetos en tiempo de ejecución.
*   **Patrón Null Object**: Las referencias nulas o comportamientos vacíos se controlan a través del uso de colecciones vacías (`List.of()`) y del tipo `Optional` de Java (actuando como mónada), lo que hace innecesario definir objetos nulos artificiales del dominio.
*   **Inversión de Control (IoC con contenedores)**: El flujo principal y la inyección de dependencias se configuran de manera manual y directa en el método `Main` de cada reto. Utilizar un framework de contenedor externo (como Spring) violaría el principio de mínima sorpresa y simplicidad en un proyecto de algoritmos.

---

## Arquitectura de Entrada/Salida (I/O)

La entrada/salida de datos en este proyecto se ha estructurado para desacoplar por completo el acceso físico a los datos de la lógica de negocio de los retos. Sin embargo, debido a la naturaleza cambiante de los inputs de cada día, la arquitectura de I/O no es idéntica para todos los problemas.

### Modelo de la I/O

El modelo se apoya en dos conceptos clave con diferentes niveles de abstracción:

1.  **Interfaz de Deserialización (`Deserializer<T>`) [Común para todos]**: Es una interfaz genérica que define el contrato `deserialize(String content)` para transformar texto plano en una entidad o colección del dominio de negocio. Todos los retos reutilizan esta abstracción genérica de `common.io`.
2.  **Cargador (Loader) y Factoría (`LoaderFactory`) [Depende del reto]**: 
    *   **Carga Genérica por Línea**: Para retos donde el input consiste en una lista homogénea de registros línea por línea (Días 1, 2, 3, 4, 8), se reutiliza el cargador genérico [TxtLoader.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/TxtLoader.java) y la factoría [LoaderFactory.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/LoaderFactory.java).
    *   **Carga Personalizada**: Cuando la estructura del input requiere procesar el archivo completo en bloque, secciones múltiples o estructuras bidimensionales (Días 5, 6, 7), no es viable la carga secuencial línea a línea de `TxtLoader`. En estos casos, se desarrollan cargadores específicos para el día (ej. `TxtDatabaseLoader`, `TxtMathWorksheetLoader`, `TxtManifoldLoader`), aunque se sigue respetando el contrato de la interfaz de deserialización.

### Diagrama de I/O

El siguiente diagrama modela la arquitectura de I/O utilizada para los retos con carga secuencial **línea a línea** (Días 1, 2, 3, 4, 8):

![UML io](UML%20diagrams/uml_io.png)

### Componentes genéricos (`common.io`)
*   **[Deserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java)**: Interfaz genérica que define el contrato `T deserialize(String line)` para transformar texto plano en objetos estructurados del dominio.
*   **[TxtLoader.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/TxtLoader.java)**: Implementación genérica que utiliza un `BufferedReader` para leer secuencialmente las líneas de un archivo físico y delegar su deserialización mediante una función (`Function<String, T>`).
*   **[LoaderFactory.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/LoaderFactory.java)**: Factoría que centraliza y simplifica la creación de cargadores de ficheros de texto.

### Implementación específica por reto
Cada día cuenta con un paquete `io` específico para aislar el parseo correspondiente:
1.  **Día 1**: Define `OrderLoader` (interfaz) y `TxtOrderDeserializer` que implementa `Deserializer<Order>`, parseando entradas como `L5` o `R10`.
2.  **Día 2**: Define `RangeLoader` y `TxtRangeDeserializer` que implementa `Deserializer<IdRange>`, procesando rangos de tipo `start-end`.
3.  **Día 3**: Define `BatteryBankLoader` y `TxtBatteryBankDeserializer` que implementa `Deserializer<BatteryBank>`, deserializando bancos de baterías a partir de secuencias de dígitos.
4.  **Día 4**: Define `DiagramLoader` y `TxtDiagramDeserializer` que implementa `Deserializer<Tile[]>`, convirtiendo mapas bidimensionales de caracteres (`.`, `@`) a un array estructurado de casillas.

### Pruebas de deserialización
Todos los deserializadores específicos se validan de forma independiente mediante tests unitarios dedicados:
*   Se verifica el parseo correcto de cadenas válidas y la correcta inicialización de los tipos del dominio.
*   Se comprueba la robustez del sistema y el control de errores mediante el lanzamiento de excepciones esperadas (como `IllegalArgumentException` o `NumberFormatException`) ante entradas vacías, nulas, con espacios en blanco o mal formateadas.

---

## Índice

A continuación se detalla cada día resuelto, con accesos directos a su correspondiente documentación, código de implementación y tests.

| Día | Título | Documentación | Código | Tests |
| :---: | :--- | :---: | :--- | :--- |
| **01** | Secret Entrance | [Día 1](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01) | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/DialTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/BTest/DialTest.java)** |
| **02** | Gift Shop |  [Día 2](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02)| **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/IDTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/BTest/IDTest.java)** |
| **03** | Lobby | [Día 3](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03) | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)** |
| **04** | Printing Department |  [Día 4](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04) | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)** |
| **05** | Cafeteria | [Día 5](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05) | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/ATest/ValidatorTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/BTest/ValidatorTest.java)** |
| **06** | Trash Compactor |  [Día 6](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06)| **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/ATest/MathWorksheetTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/BTest/MathWorksheetTest.java)** |
| **07** | Laboratories | [Día 7](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day07) | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day07/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day07/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day07/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day07/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/ATest/ManifoldTest.java)** <br> **[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/BTest/ManifoldTest.java)**
