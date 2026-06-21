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

*   **Abstracción**: Oculta la lógica compleja de cálculo voraz y recursivo tras la API de `BatteryBank` (`maxJoltageOfLength()`). Asimismo, los detalles de la persistencia de datos se abstraen tras la interfaz `BatteryBankLoader`.
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias para deserializadores y modelos) y facilita su mantenimiento.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `BatteryBank` se encarga exclusivamente de resolver el algoritmo de optimización matemática para un banco de baterías, `BatteryBankMaxJoltageCalculator` realiza la agregación total, y `TxtBatteryBankDeserializer` se encarga de convertir el formato de entrada de texto a objeto.
*   **Bajo acoplamiento**: Las dependencias entre módulos se basan en abstracciones. El flujo principal (`Main`) depende de interfaces como `BatteryBankLoader` y `Deserializer<BatteryBank>`, permitiendo cambiar la fuente de datos (como ficheros a bases de datos) sin modificar el modelo.
*   **Código expresivo**: Se utiliza una aproximación recursiva con nombres de métodos declarativos (`selectDigitAndRecurse`, `findMaxIndex`), lo que permite que el algoritmo "Greedy" sea autoexplicativo, evitando bucles iterativos complejos y anidados.
*   **Diseño por contrato**: Se formalizan los acuerdos mediante las interfaces `Deserializer` y `BatteryBankLoader`, donde se definen de forma precisa los contratos de entrada y salida para el flujo de carga.
*   **Inmutabilidad del modelo**: Las instancias (`BatteryBank` y `BatteryBankMaxJoltageCalculator`) son completamente inmutables una vez creadas. Sus estados internos no pueden ser modificados y la adición de nuevos bancos a través del calculador genera una nueva instancia independiente del mismo.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: `BatteryBank` representa únicamente los datos de un banco individual (dígitos), `BatteryBankMaxJoltageCalculator` encapsula en exclusiva el algoritmo Greedy de maximización para un solo banco, y `TotalBatteryJoltageCalculator` realiza la agregación y cálculo del voltaje total de todos los bancos.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La clase `TotalBatteryJoltageCalculator` está parametrizada mediante el constructor para la longitud de selección deseada (`length`). Esto permite dar soporte a la Parte 1 (longitud 2) y a la Parte 2 (longitud 12) sin modificar el código de la clase.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*: Cualquier cargador alternativo que implemente `BatteryBankLoader` puede ser utilizado en el flujo principal sin peligro de romper la lógica de negocio de la aplicación.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*: Las interfaces del dominio como `BatteryBankLoader` y `Deserializer<T>` son simples, cohesivas y definen un único método abstracto de firma clara.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: Los flujos principales de ejecución (`Main`) no dependen directamente de archivos físicos ni de parseos específicos, sino de las abstracciones `BatteryBankLoader` y `Deserializer`.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Inyección de dependencias**: La clase `Main` inyecta en el flujo la instancia del deserializador y el cargador necesarios, logrando un desacoplamiento completo de la infraestructura.
*   **Genéricos**: La interfaz del núcleo `Deserializer<T>` es genérica, permitiendo que la lógica del deserializador de baterías comparta e implemente la misma base que los días anteriores.
*   **Good Naming**: Nombres autoexplicativos como `maxJoltageOfLength()`, `selectDigitAndRecurse()` y `sumAll()` aumentan la legibilidad y eliminan la necesidad de comentarios explicativos redundantes.
*   **Evitar la obsesión por primitivos (Value Objects)**: Se introducen los tipos de dominio `Joltage` (que encapsula el valor de voltaje en un `long`) y `Length` (que encapsula la longitud de selección y añade reglas de validación en un `int`), evitando la dispersión de primitivos sin significado y aumentando la seguridad de tipos.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se definen métodos estáticos de creación como `BatteryBank.create()` y `BatteryBankMaxJoltageCalculator.create()`, abstrayendo la instanciación e inicialización.
*   **Patrón Iterator**:
    *   *Implementación*: Uso de **Java Streams** (como `IntStream` en `findMaxIndex` y streams colectores en `sumAll`) para procesar iteraciones sobre listas y caracteres de manera fluida y declarativa.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar el parseo de datos de entrada y la exactitud del algoritmo Greedy sobre diversos escenarios de prueba.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day03/ATest/TxtBatteryBankDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/TxtBatteryBankDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day03/ATest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/ATest/BatteryTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day03/BTest/BatteryTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day03/BTest/BatteryTest.java)

### Escenarios validados

#### Deserialización (`TxtBatteryBankDeserializerTest`)
*   **Parseo correcto**: Comprobación de la creación correcta de objetos `BatteryBank` desde cadenas de entrada planas, incluyendo la eliminación de espacios en blanco al inicio y al final.
*   **Robustez y gestión de errores**:
    *   Lanzamiento de `IllegalArgumentException` si la línea de entrada es nula.
    *   Lanzamiento de `IllegalArgumentException` si la línea está vacía o solo contiene espacios en blanco.

#### Parte A (`ATest/BatteryTest`)
*   **Algoritmo Voraz**: Validación de que `BatteryBank` extrae correctamente la subsecuencia de dígitos máxima de longitud 2 en diversos casos de ejemplo (ej. `"987654321111111"` -> `98`, `"811111111111119"` -> `89`).
*   **Cálculo de suma total**: Confirmación de que `BatteryBankMaxJoltageCalculator` realiza la suma acumulada precisa de los voltajes óptimos con los datos de ejemplo, esperando un valor de `357`.

#### Parte B (`BTest/BatteryTest`)
*   **Algoritmo Voraz (Escala 12)**: Validación de la correcta selección Greedy para longitudes grandes de 12 dígitos, controlando desbordamientos de tipos gracias al uso del tipo `Joltage` (ej. `"987654321111111"` -> `987654321111L`).
*   **Cálculo de suma total**: Comprobación de la suma correcta del conjunto extendido de baterías con longitud 12, esperando una suma de `3121910778619L`.
