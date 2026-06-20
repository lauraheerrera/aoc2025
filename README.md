# Advent of Code 2025

Este repositorio contiene las soluciones en Java para los desafíos de **Advent of Code 2025**, desarrolladas siguiendo principios de diseño de software avanzados como **Clean Architecture**, principios **SOLID** y modularización orientada al dominio.

## Estructura del proyecto

El proyecto está estructurado como un proyecto Maven estándar de Java 21, organizado de la siguiente manera:
- **[src/main/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java)**: Contiene la lógica del negocio de cada día (desglosado por paquetes `dayXX`), modelos de dominio, cargadores y deserializadores de entrada, además de la lógica común reutilizable bajo `common`.
- **[src/test/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/test/java)**: Tests unitarios exhaustivos para validar las soluciones utilizando JUnit 4 y AssertJ.
- **[UML diagrams](https://github.com/lauraheerrera/aoc2025/tree/master/UML%20diagrams)**: Diagramas UML para modelar las clases.

---

## Arquitectura y diseño

Este proyecto ha sido diseñado siguiendo estándares de ingeniería de software, priorizando la mantenibilidad, extensibilidad y claridad del código. La estructura se divide en módulos que separan la infraestructura técnica de la lógica de negocio de cada día.

### Fundamentos de diseño

La arquitectura global se cimienta sobre cuatro pilares fundamentales:
- **Modularidad**: El sistema se organiza en un núcleo común ([common](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/common)) y módulos independientes para cada reto (`dayXX`). Esto permite que el crecimiento del proyecto no aumente la complejidad de navegación.
- **Abstracción**: Se utiliza la interfaz genérica [Deserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java) para ocultar los detalles del parseo de datos. El resto del sistema interactúa con esta abstracción, desacoplándose del formato de entrada (texto, binario, etc.).
- **Bajo acoplamiento**: Los puntos de entrada (`Main`) no dependen de implementaciones concretas de lectura de archivos, sino de interfaces (como `Loader`), lo que facilita el intercambio de fuentes de datos.
- **Alta cohesión**: Cada componente tiene una única responsabilidad bien definida: los modelos gestionan la lógica, los deserializadores el parseo y los cargadores el acceso a datos.

### Principios aplicados

- **SOLID**:
    *   **Principio de Responsabilidad Única (SRP)**: Cada módulo o clase tiene una sola razón para cambiar, separando la lógica del dominio de la infraestructura de entrada/salida.
    *   **Principio de Inversión de Dependencias (DIP)**: Se depende siempre de abstracciones (interfaces como `Loader` o `Deserializer`), no de concreciones.
    *   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
        *   *Definición*: Los objetos de un programa deberían ser reemplazables por instancias de sus subtipos sin alterar el correcto funcionamiento del programa.
        *   *Implementación*: Cualquier implementación concreta de las interfaces `Deserializer<Order>` o `OrderLoader` puede sustituir a la actual (por ejemplo, deserializadores para otros formatos de archivo) sin alterar el comportamiento esperado por las clases consumidoras como `Main`.
    *   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes. 
        *   *Implementación*: Las interfaces del sistema son altamente específicas y cohesivas. Por ejemplo, `OrderLoader` y `Deserializer` definen un único método preciso (`load()` y `deserialize()` respectivamente), evitando forzar a las clases implementadoras a arrastrar métodos innecesarios.
- **DRY (Don't Repeat Yourself)**: La lógica de lectura de archivos se ha centralizado en [TxtLoader.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/TxtLoader.java), evitando duplicidad de código en el manejo de `BufferedReader`.
- **YAGNI (You Aren't Gonna Need It)**: Se han implementado las soluciones de forma sencilla, sin añadir complejidad innecesaria más allá de lo requerido por el problema.

### Patrones de diseño

1. **Factory method**: La clase [LoaderFactory.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/LoaderFactory.java) encapsula la creación compleja de cargadores, simplificando la vida del desarrollador cliente.

---

## Índice

A continuación se detalla cada día resuelto, con accesos directos a su correspondiente documentación, código de implementación y tests.

| Día | Título | Documentación | Código | Tests |
| :---: | :--- | :--- | :--- | :--- |
| **01** | Secret Entrance | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/DialTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/BTest/DialTest.java)** |
| **02** | Gift Shop | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/IDTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/BTest/IDTest.java)** |
| **03** | Lobby | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)** |
| **04** | Printing Department | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)** |
| **05** | Cafeteria | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/ATest/ValidatorTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/BTest/ValidatorTest.java)** |
| **06** | Trash Compactor | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/ATest/MathWorksheetTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/BTest/MathWorksheetTest.java)** |
---
