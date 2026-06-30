# Day 6: Trash Compactor

## Descripción

El desafío consiste en resolver operaciones matemáticas impresas en hojas de ejercicios (worksheets). Las hojas están representadas como cuadrículas de caracteres, donde las operaciones están separadas por columnas vacías y tienen un operador al final.
1.  **Parte 1**: Las operaciones están escritas en filas (horizontalmente). Resolver cada problema y sumar los resultados.
2.  **Parte 2**: Las operaciones están escritas en columnas (verticalmente, leídas de derecha a izquierda). Resolver cada problema y sumar los resultados.

## Diagramas UML
![Diagrama UML Modelo](../../../../../../../UML%20diagrams/uml_day06.png)


## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

* **Abstracción**: Se definen abstracciones puras del dominio matemático: `Operator` maneja la lógica de suma y multiplicación, mientras que `Operand` y `Operation` abstraen una operación matemática completa. El modelo ignora por completo la complejidad de cómo están escritos los problemas en la hoja.
* **Encapsulamiento**: La detección y extracción de columnas vacías, el cálculo de anchos máximos (`maxWidth()`) y la reconstrucción espacial de los problemas a través de bloques de texto se encapsulan dentro de los métodos privados de la clase utilitaria `WorksheetSplitter` en el paquete de Entrada/Salida (I/O).
* **Cohesión**: Cada clase tiene un único propósito estricto: `Operator`, `Operand` y `Operation` se encargan de las reglas del negocio matemático. Por otro lado, la lógica de fragmentación recae únicamente en `WorksheetSplitter`, y el parseo del texto a objetos del modelo es responsabilidad exclusiva de los deserializadores (`RowOperationDeserializer` y `ColumnR2LOperationDeserializer`).
* **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia alguna de cómo se leen o formatean los datos de entrada. El flujo de control (`Main`) conecta los datos leídos con el modelo utilizando interfaces genéricas (`Deserializer<T>`), aislando la lógica del problema de la infraestructura.
* **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI - Composición sobre herencia)**:
    *   La clase `Operation` se compone de una colección de `Operand` y del operador `Operator` sin necesidad de jerarquías de herencia.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   [Operation.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Operation.java) (`solve()`): Se encarga exclusivamente de resolver la operación matemática sobre una lista de operandos.
        *   [WorksheetSplitter.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/io/WorksheetSplitter.java): Se encarga única y exclusivamente de separar un texto bidimensional en bloques individuales.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        En lugar de utilizar estructuras condicionales (o un enum de vista) para decidir cómo leer el texto, el sistema define la interfaz `Deserializer`. Se han creado dos implementaciones (`RowOperationDeserializer` y `ColumnR2LOperationDeserializer`). Si en el futuro los cefalópodos deciden escribir en diagonal, solo habrá que añadir un nuevo deserializador sin modificar el código existente.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        Ambos deserializadores implementan Deserializer<Operation> de forma limpia y pueden ser intercambiados de manera transparente en la clase Main para resolver la Parte A o la Parte B.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista y cohesiva que expone un único método (`deserialize()`), evitando forzar la implementación de métodos innecesarios.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java (day06/a)](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/a/Main.java): El flujo principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Operation>` en lugar de clases concretas de carga.
*   **Law of Demeter (LoD - Ley de Deméter)**: Las clases interactúan solo con sus dependencias inmediatas. Por ejemplo, la clase Main se comunica con Operation (`solve()`) sin descender a manipular manualmente los dígitos internos de Operand.

## Técnicas de diseño aplicadas

*   **Inmutabilidad**: `Operation` y `Operand` son Records inmutables de Java. Por su parte, los componentes de I/O son stateless, operando mediante métodos estáticos o instancias puras que garantizan la ausencia de efectos secundarios.
*   **Programación funcional (con Java Streams)**: Utiliza `operands.stream().mapToLong(Operand::value).reduce(operator::apply)` para aplicar el operador de manera declarativa sobre la lista de operandos y resolver el problema de forma inmutable.
*   **Inyección de dependencias**: La factoría `LoaderFactory` recibe una `Function<String, T>` como parámetro. A su vez, `Worksheet.parse()` recibe el `Deserializer<Problem>` como argumento.
*   **Genéricos**: Se consume la interfaz parametrizada `Deserializer<T>` para desacoplar el deserializador.
*   **Good Naming**: Métodos autodeclarativos y descriptivos como solve(), splitIntoBlocks(), buildContentMap() o isColumnEmpty().

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**: Implementado en el método estático [Operator.java:L31-L36](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day06/model/Operator.java#L31-L36) (`Operator.from(char symbol)`), resolviendo la instancia correcta a partir del carácter.
*   **Patrones funcionales**:
    *   **Closure**: Empleado mediante expresiones lambda en `Worksheet.java` y `TxtMathProblemDeserializer.java` para capturar el contexto inmutable.
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
