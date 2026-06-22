# Day 3: Lobby

## Descripción

El desafío consiste en encontrar la combinación de dígitos que maximice el voltaje producido por bancos de baterías. Cada banco está representado por una cadena de dígitos.
1.  **Parte 1**: Seleccionar exactamente **2** dígitos de cada banco para maximizar el valor.
2.  **Parte 2**: Seleccionar exactamente **12** dígitos de cada banco.

El objetivo final es sumar los voltajes máximos encontrados en todos los bancos de baterías proporcionados.

## Diagrama UML
Para ambas partes, el modelo de clases resulta ser igual
![Diagrama UML](../../../../../../../UML%20diagrams/uml_day03.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Oculta la lógica compleja de cálculo voraz y recursivo tras la API de `BatteryBank` (`maxJoltageOfLength()`).
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias para los modelos) y facilita su mantenimiento.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `BatteryBank` se encarga exclusivamente de resolver el algoritmo de optimización matemática para un banco de baterías, y `BatteryBankMaxJoltageCalculator` realiza la agregación total.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de dominio.
*   **Código expresivo**: Se utiliza una aproximación recursiva con nombres de métodos declarativos (`selectDigitAndRecurse`, `findMaxIndex`), lo que permite que el algoritmo "Greedy" sea autoexplicativo, evitando bucles iterativos complejos y anidados.
*   **Inmutabilidad del modelo**: Las instancias (`BatteryBank` y `BatteryBankMaxJoltageCalculator`) son completamente inmutables una vez creadas. Sus estados internos no pueden ser modificados y la adición de nuevos bancos a través del calculador genera una nueva instancia independiente del mismo.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: `BatteryBank` representa únicamente los datos de un banco individual (dígitos), `BatteryBankMaxJoltageCalculator` encapsula en exclusiva el algoritmo Greedy de maximización para un solo banco, y `TotalBatteryJoltageCalculator` realiza la agregación y cálculo del voltaje total de todos los bancos.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La clase `TotalBatteryJoltageCalculator` está parametrizada mediante el constructor para la longitud de selección deseada (`length`). Esto permite dar soporte a la Parte 1 (longitud 2) y a la Parte 2 (longitud 12) sin modificar el código de la clase.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*: Empleado en `TotalBatteryJoltageCalculator` y `BatteryBankMaxJoltageCalculator` para mapear y acumular los voltajes máximos de los bancos de baterías.
*   **Inyección de dependencias**:
    * *Definición*: Técnica de diseño que consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código.
    * *Implementación*: Se inyecta la instancia de `BatteryBankMaxJoltageCalculator` y el Value Object `Length` a través del constructor del acumulador `TotalBatteryJoltageCalculator`, promoviendo la modularidad y el desacoplamiento de componentes.
*   **Good Naming**: Nombres autoexplicativos como `maxJoltageOfLength()`, `selectDigitAndRecurse()` y `sumAll()` aumentan la legibilidad y eliminan la necesidad de comentarios explicativos redundantes.
*   **Eliminación de la obsesión por los primitivos (Primitive Obsession)**: Se han eliminado los tipos primitivos en las firmas del modelo. En su lugar:
    *   Se utiliza el objeto de dominio `Joltage` en lugar de valores numéricos primitivos (`long`) para representar el voltaje de los bancos de baterías.
    *   Se utiliza el objeto de dominio `Length` en lugar de enteros planos (`int`) para representar y validar la longitud de la selección de dígitos.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se definen métodos estáticos de creación como `BatteryBank.create()` y `BatteryBankMaxJoltageCalculator.create()`, abstrayendo la instanciación e inicialización.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Uso de **Java Streams** (como `IntStream` en `findMaxIndex` y streams colectores en `sumAll`) para procesar iteraciones sobre listas y caracteres de manera fluida y declarativa.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Uso de expresiones lambda y referencias a métodos que capturan variables locales y propiedades en el procesamiento funcional de los streams de los calculadores.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la exactitud del algoritmo Greedy sobre diversos escenarios de prueba.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day03/IOTest/TxtBatteryBankDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/IOTest/TxtBatteryBankDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day03/ATest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day03/BTest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)

### Escenarios validados

#### Parte A (`ATest/BatteryTest`)
*   **Algoritmo Voraz**: Validación de que `BatteryBank` extrae correctamente la subsecuencia de dígitos máxima de longitud 2 en diversos casos de ejemplo (ej. `"987654321111111"` -> `98`, `"811111111111119"` -> `89`).
*   **Cálculo de suma total**: Confirmación de que `BatteryBankMaxJoltageCalculator` realiza la suma acumulada precisa de los voltajes óptimos con los datos de ejemplo, esperando un valor de `357`.

#### Parte B (`BTest/BatteryTest`)
*   **Algoritmo Voraz (Escala 12)**: Validación de la correcta selección Greedy para longitudes grandes de 12 dígitos, controlando desbordamientos de tipos gracias al uso del tipo `Joltage` (ej. `"987654321111111"` -> `987654321111L`).
*   **Cálculo de suma total**: Comprobación de la suma correcta del conjunto extendido de baterías con longitud 12, esperando una suma de `3121910778619L`.
