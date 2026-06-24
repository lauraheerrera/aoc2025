# Day 6: Trash Compactor

## Descripción

El desafío consiste en resolver operaciones matemáticas impresas en hojas de ejercicios (worksheets). Las hojas están representadas como cuadrículas de caracteres, donde las operaciones están separadas por columnas vacías y tienen un operador al final.
1.  **Parte 1**: Las operaciones están escritas en filas (horizontalmente). Resolver cada problema y sumar los resultados.
2.  **Parte 2**: Las operaciones están escritas en columnas (verticalmente, leídas de derecha a izquierda). Resolver cada problema y sumar los resultados.

## Diagramas UML
Modelo
![Diagrama UML Modelo](../../../../../../../UML%20diagrams/uml_day06.png)

I/O
<p align="center">
  <img src="../../../../../../../UML%20diagrams/uml_day06_io.png" width="400">
</p>

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta los detalles del algoritmo de segmentación de bloques y la conversión de orientaciones tras la clase `TxtMathWorksheetLoader`. Asimismo, la carga y extracción de problemas se realiza tras la interfaz `ProblemLoader`.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`), facilitando el desarrollo y testeo por separado de cada lógica de entrada o resolución.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad. El record `Problem` se encarga únicamente de evaluar la operación sobre una lista de números, y `TxtMathWorksheetLoader` detecta las columnas vacías para dividir el worksheet en bloques individuales.
*   **Bajo acoplamiento**: Las interacciones se basan en abstracciones. El cargador de hojas de ejercicios depende de la interfaz `Deserializer<Problem>` en lugar de una clase de deserialización acoplada.
*   **Código expresivo**: Se hace un uso extensivo de la API funcional de streams y expresiones regulares para parsear los números (`Pattern.compile("\\d+")`) e identificar los límites de cada bloque de forma declarativa.
*   **Diseño por contrato**: El cargador y deserializador cumplen rigurosamente con las firmas genéricas de `ProblemLoader` y `Deserializer`.
*   **Inmutabilidad del modelo**: La representación de cada operación matemática `Problem` es un **Record** inmutable de Java, lo que previene que los números u operadores sufran modificaciones indeseadas durante el cálculo.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Problem.java:L6-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Problem.java#L6-L15) (`solve()`): Se encarga exclusivamente de resolver la operación matemática sobre una lista de operandos.
        *   [TxtMathWorksheetLoader.java:L9-L22](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathWorksheetLoader.java#L9-L22): Su única responsabilidad es cargar las líneas del archivo de ejercicios y delegar su creación.
        *   [TxtMathProblemDeserializer.java:L14-L29](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L14-L29) (`deserialize()`): Lee y parsea los operandos y el operador a partir de bloques de texto.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [TxtMathProblemDeserializer.java:L15-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L15-L25): La estrategia de lectura del deserializador es configurable mediante la inyección del enum `View` (`ROWS` y `COLUMNS_R2L`), lo que permite dar soporte a la Parte A y B sin alterar el flujo básico de deserialización.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtMathProblemDeserializer.java:L14](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L14): La clase implementa `Deserializer<Problem>` de forma limpia, y la clase `TxtMathWorksheetLoader` en [TxtMathWorksheetLoader.java:L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathWorksheetLoader.java#L9) implementa `ProblemLoader`. Ambas son inyectables de forma transparente en la clase `Main`.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [ProblemLoader.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/ProblemLoader.java#L7-L9) y [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Son interfaces minimalistas y cohesivas que exponen un único método (`load()` y `deserialize()` respectivamente), evitando forzar la implementación de métodos innecesarios.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [TxtMathWorksheetLoader.java:L11-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathWorksheetLoader.java#L11-L16): El cargador `TxtMathWorksheetLoader` depende de la interfaz genérica `Deserializer<Problem>` en lugar de una clase deserializadora concreta, facilitando el desacoplamiento de la lógica de análisis.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Problem.java:L8-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Problem.java#L8-L11) (`solve()`): Utiliza `operands.stream().mapToLong(Operand::value).reduce(operator::apply)` para aplicar el operador de manera declarativa sobre la lista de operandos y resolver el problema de forma inmutable.
        *   [Worksheet.java:L25-L27](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Worksheet.java#L25-L27) (`parse()`): Usa streams para iterar los bloques de la hoja, deserializar su contenido a un `Problem` mediante `map(...)` y recolectarlo en una lista (`toList()`).
        *   [Worksheet.java:L31-L33](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Worksheet.java#L31-L33) (`splitIntoBlocks()`): Consume los resultados de un `Matcher` como un stream de resultados (`results()`), mapeando cada segmento encontrado a un record `ProblemBlock` (`map(...)`) y colectándolo.
        *   [TxtMathProblemDeserializer.java:L36-L39](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L36-L39) (`extractOperandsByRow()`): Convierte las líneas a un stream de operandos mediante `flatMap(...)` y `parseNumbers()`, mapeando cada número a un objeto `Operand`.
        *   [TxtMathProblemDeserializer.java:L44-L49](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L44-L49) (`extractOperandsByColumn()`): Utiliza `IntStream.range` para transponer y recorrer las columnas de forma secuencial y declarativa de derecha a izquierda.
        *   [Operator.java:L32-L35](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Operator.java#L32-L35) (`from()`): Utiliza `Arrays.stream(values()).filter(...)` para buscar el operador correspondiente a un carácter simbólico de forma funcional.
*   **Inyección de dependencias**:
    *   *Definición*: Técnica de diseño que consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código.
    *   *Implementación*: El cargador `TxtMathWorksheetLoader` en [TxtMathWorksheetLoader.java:L13-L16](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathWorksheetLoader.java#L13-L16) recibe el `Deserializer<Problem>` en su constructor.
*   **Genéricos**:
    *   *Definición*: Permiten parametrizar clases, interfaces y métodos con tipos para proveer seguridad de tipos en tiempo de compilación y evitar duplicidades.
    *   *Implementación*: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar el deserializador del tipo concreto `Problem`.
*   **Good Naming**: Métodos autodeclarativos como `solve()`, `splitIntoBlocks()`, `extractOperandsByColumn()`, y `columnString()`.

## Patrones de diseño

*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Implementado en el método estático [Operator.java:L31-L36](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Operator.java#L31-L36) (`Operator.from(char symbol)`), resolviendo y construyendo la instancia correcta del enum a partir del símbolo del carácter.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Consumido de forma abstracta a través de Java Streams en clases como `Worksheet`, `Problem` y `TxtMathProblemDeserializer` para iterar y procesar cuadrículas, caracteres y líneas sin bucles explícitos.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Empleado mediante expresiones lambda en [Worksheet.java:L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Worksheet.java#L26) (capturando `deserializer`), [Worksheet.java:L32](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Worksheet.java#L32) (capturando `this`), y en [TxtMathProblemDeserializer.java:L46](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L46) (capturando `lines`) para encapsular el comportamiento funcional con estados en tiempo de ejecución.


---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del motor de resolución matemática.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day06/ATest/TxtMathProblemDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/ATest/TxtMathProblemDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day06/ATest/MathWorksheetTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/ATest/MathWorksheetTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day06/BTest/MathWorksheetTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day06/BTest/MathWorksheetTest.java)

### Escenarios validados

#### Deserialización (`TxtMathProblemDeserializerTest`)
*   **Parseo de filas**: Verificación del correcto parseo horizontal de números y operador.
*   **Parseo de columnas**: Confirmación del correcto procesado de dígitos leídos en vertical.
*   **Robustez**: Excepciones correspondientes ante bloques de texto nulos o vacíos.

#### Parte A (`ATest/MathWorksheetTest`)
*   **Operaciones Simples**: Validación de sumas (`10 + 20 = 30`) y multiplicaciones (`10 * 20 = 200`).
*   **Suma de Hoja de Ejercicio**: Confirmación de la correcta resolución de una hoja completa, esperando una suma acumulada de `4277556L`.
*   **Caso Límite**: Validación de que una operación sin operandos retorna `0L`.

#### Parte B (`BTest/MathWorksheetTest`)
*   **Lectura Transpuesta**: Verificación del correcto agrupamiento de números leídos de derecha a izquierda.
*   **Suma de Hoja de Ejercicio (Columnas)**: Confirmación de la suma acumulada total correcta para la lectura vertical en el ejemplo, esperando `4400588L`.
