# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day01.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Se modelan de forma abstracta los elementos del dominio: `Dial` representa el dial rotatorio, `Order` modela un movimiento individual y `DialStatus` representa la abstracción del estado físico acumulado. La complejidad de calcular las posiciones relativas y manejar el desbordamiento circular se oculta tras métodos de alto nivel.
*   **Encapsulamiento**: La clase `DialStatus` encapsula el historial de órdenes y la posición normalizada, exponiendo únicamente métodos públicos limpios para ejecutar nuevas órdenes y obtener la posición actual. De igual forma, `DialCalculator` oculta la lógica del cálculo matemático de cruces por cero (`calculateZerosCrossed`) detrás de métodos de consulta simples.
*   **Cohesión**: Cada clase tiene una única y clara responsabilidad: `Order` solo almacena el desplazamiento individual, `Dial` representa la identidad estática del objeto, `DialStatus` rastrea y normaliza el estado acumulativo del dial, y `DialCalculator` procesa las métricas de conteo que solucionan las dos partes del problema.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI)**:`DialStatus` se compone por referencia del dial `Dial` en lugar de extender su comportamiento o clase, lo que favorece el acoplamiento y reduce la complejidad.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        * [DialStatus.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java): Se encarga exclusivamente de la representación del estado actual del dial y su historial de órdenes.
        * [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java): Alberga la lógica algorítmica específica para contar las veces que el dial se sitúa o cruza por cero.
        * [TxtOrderDeserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java): Su única responsabilidad es deserializar una línea de texto plano a un objeto `Order`.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   [DialStatus.java:L21-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java#L21-L26) (`execute()`): Acepta una `List<Order>` genérica, por lo que el diseño no requiere modificar `DialStatus` para procesar nuevas secuencias de órdenes. La ausencia de lógica condicional sobre el tipo de orden evita que añadir nuevas órdenes implique tocar el código existente.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtOrderDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Order>`, pudiendo inyectarse transparentemente en cualquier cargador que precise un deserializador compatible.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista y cohesiva que expone un único método (`deserialize()`), evitando forzar la implementación de métodos innecesarios.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java:L19-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java#L19-L21): Depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Order>` en lugar de acoplarse directamente a implementaciones de bajo nivel.
*   **Don’t Repeat Yourself (DRY)**: El algoritmo matemático de conteo de cruces y desbordamiento se delega en el record `DialStatus`, siendo consumido de igual manera en ambas partes.
*   **Law of Demeter (LoD - Ley de Deméter)**: La clase `Main` interactúa únicamente con `Dial` y `DialStatus` directamente sin navegar por las propiedades de caracteres individuales del record `Order`.
*   **Principio de mínimo compromiso**: Los métodos públicos de `DialStatus` (`position()`, `countEndingInZero()`, `countCrossingZero()`) ocultan los algoritmos intermedios y de optimización de desbordamientos de cruces por cero.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**:
    *   Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. El record `Dial` es la entidad estática y completamente inmutable (sin estado). Las propiedades que cambian con las acciones (el historial de órdenes y la posición) se encapsulan en el record `DialStatus`. Al ejecutar nuevas órdenes con `execute()`, se devuelve un nuevo `DialStatus` que hace referencia al mismo `Dial` inicial, evitando la recreación innecesaria de la propia entidad del modelo. El record `Order` es también inmutable.
*   **Programación funcional (con Java Streams)**:
    *   [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java) (`countEndingInZero()`): Usa `IntStream.rangeClosed(1, orders.size())` en paralelo para evaluar todas las sumas parciales (`calculatePartialSum()`). Filtra las que son iguales a `0` y las cuenta, determinando de manera declarativa cuántas veces el dial termina en cero tras finalizar un movimiento.
    *   [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java) (`countCrossingZero()`): Utiliza `IntStream.range(0, orders.size())` para mapear de forma secuencial cada orden al número de cruces por cero intermedios (`calculateZerosCrossed()`) y sumarlos (`sum()`), resolviendo eficientemente la Parte B.
    *   [DialStatus.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java) (`calculateSum()`): Procesa la lista de órdenes con `orderList.stream()`, transformándola a un stream de enteros con `mapToInt(Order::step)` y sumándola con `sum()`. Esto calcula la posición total recorrida de forma declarativa e inmutable.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: Inyección del deserializador en la factoría de carga a través del flujo principal.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Uso de interfaces parametrizadas `Deserializer<T>` para el manejo seguro de los objetos de datos.
*   **Good Naming**:
    *   Nombres explícitos como `INITIAL_POSITION`, `calculateZerosCrossed()`, o `countEndingInZero()` aseguran que el código sea autodocumentado.
*   **Fluent API**:
    *   *Definición*: Patrón que devuelve la propia instancia del objeto en cada método, permitiendo encadenar llamadas de forma legible. Mejora la expresividad del código.
    *   *Implementación*: Empleado en `DialStatus`, cuyos métodos `initial()` y `execute(...)` permiten encadenar operaciones de forma fluida (`DialStatus.initial(dial).execute(...).execute(...)`), favoreciendo un estilo de programación más legible e inmutable.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   [Dial.java:L7-L9](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L7-L9) mediante el método estático `Dial.create()`. Este método encapsula la construcción del dial y ofrece un punto de entrada limpio en el punto de uso.
        *   [DialStatus.java:L17-L19](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java#L17-L19) mediante el método estático `DialStatus.initial(Dial)`. Este método encapsula la construcción del estado inicial del dial y ofrece un punto de entrada limpio en el punto de uso.
*   **Patrones funcionales**:
    *   **Closure**: Se utiliza en [DialCalculator.java:L23-L30](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java#L23-L30) mediante expresiones lambda que capturan el contexto (como `this::calculatePartialSum`, `s -> s == 0` y `this::calculateZerosCrossed`) para ejecutar lógica diferida e inmutable dentro de los flujos de datos.

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

