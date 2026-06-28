# Day 6: Trash Compactor

## Descripción

El desafío consiste en resolver operaciones matemáticas impresas en hojas de ejercicios (worksheets). Las hojas están representadas como cuadrículas de caracteres, donde las operaciones están separadas por columnas vacías y tienen un operador al final.
1.  **Parte 1**: Las operaciones están escritas en filas (horizontalmente). Resolver cada problema y sumar los resultados.
2.  **Parte 2**: Las operaciones están escritas en columnas (verticalmente, leídas de derecha a izquierda). Resolver cada problema y sumar los resultados.

## Diagramas UML
![Diagrama UML Modelo](../../../../../../../UML%20diagrams/uml_day06.png)


## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: Oculta los detalles del algoritmo de segmentación de bloques y la conversión de orientaciones tras la clase `Worksheet`. La carga del fichero se delega en la factoría genérica `LoaderFactory`, que abstrae la lectura línea a línea del sistema de archivos.
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: Los detalles del procesamiento matricial y transposición de filas y columnas residen dentro de `TxtMathProblemDeserializer`.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo están relacionados entre sí y colaboran para cumplir una única tarea.
    *   *Implementación*: Cada componente tiene una única responsabilidad. El record `Problem` se encarga únicamente de evaluar la operación sobre una lista de números, y `Worksheet` detecta las columnas vacías para dividir el worksheet en bloques individuales.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: Las interacciones se basan en abstracciones. La carga de ficheros se realiza mediante la factoría genérica `LoaderFactory` del paquete `common.io`, reutilizable por cualquier día. El modelo `Worksheet` depende de la interfaz `Deserializer<Problem>` en lugar de una clase de deserialización acoplada.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: Se hace un uso extensivo de la API funcional de streams y expresiones regulares para parsear los números (`Pattern.compile("\\d+")`) e identificar los límites de cada bloque de forma declarativa.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: La representación de cada operación matemática `Problem` es un **Record** inmutable de Java, lo que previene que los números u operadores sufran modificaciones indeseadas durante el cálculo.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: La clase `Problem` se compone de una colección de `Operand` y del operador `Operator` sin necesidad de jerarquías de herencia.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Problem.java:L6-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Problem.java#L6-L15) (`solve()`): Se encarga exclusivamente de resolver la operación matemática sobre una lista de operandos.
            *   [TxtMathProblemDeserializer.java:L14-L29](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L14-L29) (`deserialize()`): Lee y parsea los operandos y el operador a partir de bloques de texto.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [TxtMathProblemDeserializer.java:L15-L25](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L15-L25): La estrategia de lectura del deserializador es configurable mediante la inyección del enum `View` (`ROWS` y `COLUMNS_R2L`), lo que permite dar soporte a la Parte A y B sin alterar el flujo básico de deserialización.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtMathProblemDeserializer.java:L14](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/TxtMathProblemDeserializer.java#L14): La clase implementa `Deserializer<Problem>` de forma limpia y es inyectable de forma transparente en `Worksheet.parse()`.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista y cohesiva que expone un único método (`deserialize()`), evitando forzar la implementación de métodos innecesarios.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java (day06/a)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/a/Main.java): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Problem>` en lugar de clases concretas de carga.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: `Worksheet` interactúa directamente con `Problem` a través de su interfaz, sin descender a evaluar los dígitos internos de `Operand`.

## Técnicas de diseño aplicadas

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Problem.java:L8-L11](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Problem.java#L8-L11) (`solve()`): Utiliza `operands.stream().mapToLong(Operand::value).reduce(operator::apply)` para aplicar el operador de manera declarativa sobre la lista de operandos y resolver el problema de forma inmutable.
        *   [Worksheet.java:L25-L27](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Worksheet.java#L25-L27) (`parse()`): Usa streams para iterar los bloques de la hoja, deserializar su contenido a un `Problem` mediante `map(...)` y recolectarlo en una lista (`toList()`).
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: La factoría `LoaderFactory` recibe una `Function<String, T>` como parámetro. A su vez, `Worksheet.parse()` recibe el `Deserializer<Problem>` como argumento.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar el deserializador.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Métodos autodeclarativos como `solve()`, `splitIntoBlocks()`, `extractOperandsByColumn()`.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
        *   *Implementación*: Implementado en el método estático [Operator.java:L31-L36](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Operator.java#L31-L36) (`Operator.from(char symbol)`), resolviendo la instancia correcta a partir del carácter.
*   **Patrones funcionales**:
    *   **Closure**:
        *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
        *   *Implementación*: Empleado mediante expresiones lambda en `Worksheet.java` y `TxtMathProblemDeserializer.java` para capturar el contexto inmutable.

## Elección de diseño: Primitivos con orElse vs Optional

En el código de resolución matemática del Día 6 se utilizan los operadores `orElse` y `orElseThrow` en lugar de propagar `Optional` en varias áreas clave:

*   **Matemáticas y Dimensiones (`Problem` y `Worksheet`)**: Los métodos `solve()`, `maxWidth()` y la detección de ancho del deserializador devuelven tipos primitivos directos (`long` e `int`). Usar `orElse(0L)` y `orElse(0)` permite resolver los streams numéricos inmediatamente y evita que la lógica llamadora de formateo o acumulación de sumas tenga que lidiar con envoltorios innecesarios.
*   **Gestión de Errores Críticos (`Operator.from`)**: Si un carácter del fichero no corresponde a un operador válido, se utiliza `.orElseThrow(...)` para abortar la ejecución. Dado que las entradas del problema de Advent of Code deben ser correctas, un carácter inválido es un error de programación irrecuperable y lanzar una excepción de inmediato (fail-fast) es mucho mejor que retornar un `Optional.empty()`.

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
