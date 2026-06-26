# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day01.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: La clase `Dial` encapsula la lógica matemática modular y de desbordamientos tras una interfaz pública simple (`position()`, `count()`, `countTotalZeros()`).
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado y facilita su evolución o reutilización futura.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. El record `Order` solo contiene el dato del paso, y `Dial` procesa de forma exclusiva el comportamiento y posicionamiento físico del dial.
*   **Bajo acoplamiento**: Las dependencias entre módulos se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que le permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**: Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. El record `Dial` es la entidad estática y completamente inmutable (sin estado). Las propiedades que cambian con las acciones (el historial de órdenes y la posición) se encapsulan en el record `DialStatus`. Al ejecutar nuevas órdenes con `execute()`, se devuelve un nuevo `DialStatus` que hace referencia al mismo `Dial` inicial, evitando la recreación innecesaria de la propia entidad del modelo y garantizando un diseño funcional libre de efectos secundarios. El record `Order` es también inmutable.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*:
        *   [Dial.java:L8-L71](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L8-L71): Concentra exclusivamente la lógica matemática del posicionamiento y cálculo de cruces por cero del dial.
        *   [TxtOrderDeserializer.java:L6-L28](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6-L28): Su única responsabilidad es deserializar una línea de texto plano a un objeto `Order`.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*:
        *   [Dial.java:L22-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L22-L26) (`execute()`): Opera sobre una lista abstracta `List<Order>`, permitiendo procesar cualquier secuencia de órdenes nueva sin alterar el comportamiento de cálculo interno del dial.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Las subclases o implementaciones deben ser sustituibles por sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*:
        *   [TxtOrderDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Order>`, pudiendo inyectarse transparentemente en cualquier cargador que precise un deserializador compatible.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*:
        *   [OrderLoader.java:L8-L10](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/OrderLoader.java#L8-L10) y [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Son interfaces con una única responsabilidad y un único método (`load()` y `deserialize()`), evitando forzar a las implementaciones a arrastrar contratos hinchados.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*:
        *   [Main.java:L19-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java#L19-L21): La orquestación en el flujo de ejecución principal depende de las abstracciones `OrderLoader` y `Deserializer<Order>` en lugar de acoplarse directamente a implementaciones de disco de bajo nivel.


## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Dial.java:L33-L38](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L33-L38) (`count()`): Usa `IntStream.rangeClosed(1, orders.size())` en paralelo para evaluar todas las sumas parciales (`calculatePartialSum()`). Filtra las que son iguales a `0` y las cuenta, determinando de manera declarativa cuántas veces el dial termina en cero tras finalizar un movimiento.
        *   [Dial.java:L40-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L40-L44) (`countTotalZeros()`): Utiliza `IntStream.range(0, orders.size())` para mapear de forma secuencial cada orden al número de cruces por cero intermedios (`calculateZerosCrossed()`) y sumarlos (`sum()`), resolviendo eficientemente la Parte B.
        *   [Dial.java:L50-L52](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L50-L52) (`calculateSum()`): Procesa la lista de órdenes con `orderList.stream()`, transformándola a un stream de enteros con `mapToInt(Order::step)` y sumándola con `sum()`. Esto calcula la posición total recorrida de forma declarativa e inmutable.
*   **Fluent API**:
    *   *Definición*: Patrón que devuelve la propia instancia del objeto en cada método, permitiendo encadenar llamadas de forma legible. Mejora la expresividad del código.
    *   *Implementación*: Empleado en la clase `Dial`, cuyos métodos `create()` y `execute(...)` permiten encadenar operaciones de forma fluida (`Dial.create().execute(...).execute(...)`), favoreciendo un estilo de programación más legible e inmutable.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `INITIAL_POSITION`, `calculateZerosCrossed()`, o `countTotalZeros()` aseguran que el código sea autodocumentado.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se utiliza en [Dial.java:L18-L20](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L18-L20) mediante el método estático `Dial.create()`. Este método encapsula la instanciación privada del dial y ofrece una API limpia en el punto de uso.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Implementado a través de Java Streams en [Dial.java:L33-L52](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L33-L52) (`count()`, `countTotalZeros()` y `calculateSum()`) para recorrer y procesar de forma abstracta las secuencias de órdenes.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Se utiliza en [Dial.java:L33-L52](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L33-L52) mediante expresiones lambda que capturan el contexto (como `this::calculatePartialSum`, `s -> s == 0` y `this::calculateZerosCrossed`) para ejecutar lógica diferida e inmutable dentro de los flujos de datos.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar la lógica de negocio en diversos escenarios, incluyendo casos límite.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day01/IOTest/TxtOrderDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/IOTest/TxtOrderDeserializerTest.java)
*   **Tests de Lógica de la Parte A**: [`src/test/java/test/Day01/ATest/DialTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/DialTest.java)
*   **Tests de Lógica de la Parte B**: [`src/test/java/test/Day01/BTest/DialTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/BTest/DialTest.java)

### Escenarios validados

#### Parte A (`ATest/DialTest`)
*   **Posición inicial**: Comprobación de que el dial se inicializa correctamente en la posición 50 (`initial_position_should_be_50_using_create`).
*   **Cálculo de la posición final**: Verificación de que el dial calcula su posición correcta tras múltiples giros a la izquierda (L) y derecha (R), incluyendo desbordamientos cíclicos (ej. por encima de 99 o por debajo de 0).
*   **Veces en posición cero al finalizar movimientos**: Validación del conteo de cuántas veces el dial termina exactamente en la posición 0 al final de cada orden de rotación.
*   **Casos de borde / límite**: 
    *   Lista de órdenes vacía (`given_empty_orders_should_remain_at_50_and_count_zero_zeros`).
    *   Giros exactos que finalizan exactamente en la posición 0 (`L50`, `L150`).
    *   Giros completos de 360 grados (`R100`, `L100`) que regresan a la posición de partida.

#### Parte B (`BTest/DialTest`)
Además de los casos de inicialización, posición final y casos vacíos o cíclicos (similares a la Parte A):
*   **Total de pasos por cero durante el movimiento**: Validación precisa de cuántas veces el dial pasa por el 0 **en cualquier momento del trayecto del giro** (por ejemplo, giros continuos de más de una vuelta como `"R1000"` que cruza por cero 10 veces).
*   **Cruces en giros exactos que terminan en cero**: Validación de que giros que finalizan exactamente en `0` (como `L50` o `L150`) registran correctamente todos los pasos intermedios y finales por el cero.

