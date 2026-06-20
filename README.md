# Advent of Code 2025

Este repositorio contiene las soluciones en Java para los desafíos de **Advent of Code 2025**, desarrolladas siguiendo principios de diseño de software avanzados como **Clean Architecture**, principios **SOLID** y modularización orientada al dominio.

## Estructura del proyecto

El proyecto está estructurado como un proyecto Maven estándar de Java 21, organizado de la siguiente manera:
- **[src/main/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java)**: Contiene la lógica del negocio de cada día (desglosado por paquetes `dayXX`), modelos de dominio, cargadores y deserializadores de entrada, además de la lógica común reutilizable bajo `common`.
- **[src/test/java](https://github.com/lauraheerrera/aoc2025/tree/master/src/test/java)**: Tests unitarios exhaustivos para validar las soluciones utilizando JUnit 4 y AssertJ.
- **[UML diagrams](https://github.com/lauraheerrera/aoc2025/tree/master/UML%20diagrams)**: Diagramas UML para modelar las clases.

---

## Lógica común reutilizable

Para evitar la duplicación de código e implementar la arquitectura limpia de manera coherente, se cuenta con interfaces e implementaciones genéricas de deserialización y carga en el paquete **`common`**:
- [Deserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java): Interfaz base para transformar líneas de texto en objetos de dominio.
- [LoaderFactory.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/LoaderFactory.java): Factoría estática para construir cargadores de ficheros de forma desacoplada.
- [TxtLoader.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/TxtLoader.java): Implementación genérica de lectura secuencial y carga de ficheros.

---

## Índice

A continuación se detalla cada día resuelto, con accesos directos a su correspondiente documentación, código de implementación y tests.

| Día | Título | Documentación | Código | Tests |
| :---: | :--- | :--- | :--- | :--- |
| **01** | Secret Entrance | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day01/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/DialTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/BTest/DialTest.java)** |
| **02** | Gift Shop | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day02/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/ATest/IDTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day02/BTest/IDTest.java)** |
| **03** | Lobby (Bancos de Baterías) | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day03/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)** |
| **04** | Printing Department (Diagramas) | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day04/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/ATest/DiagramAnalyzerTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day04/BTest/DiagramAnalyzerTest.java)** |
| **05** | Inventory Management | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day05/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/ATest/ValidatorTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day05/BTest/ValidatorTest.java)** |
| **06** | Math Worksheet | | **[Parte A](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/a/Main.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/b/Main.java)**<br>[Modelo](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/model) \| [IO](https://github.com/lauraheerrera/aoc2025/tree/master/src/main/java/software/ulpgc/aoc/day06/io) | **[Parte A](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/ATest/MathWorksheetTest.java)**<br>**[Parte B](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/BTest/MathWorksheetTest.java)** |
