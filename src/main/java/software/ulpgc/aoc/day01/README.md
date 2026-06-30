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
*   **Encapsulamiento**: La clase `DialStatus` encapsula el estado del sistema y la lógica de transición del dial. En lugar de exponer la lista interna de órdenes, el modelo ofrece operaciones de alto nivel como `position()`, `next(index)` y `crossZeroAt(index)`, evitando que las clases externas dependan de la estructura interna del estado.
*   **Cohesión**: Cada clase tiene una única y clara responsabilidad: `Order` solo almacena el desplazamiento individual, `Dial` representa la identidad estática del objeto, `DialStatus` rastrea el estado acumulativo del dial, y `DialCalculator` procesa las métricas de conteo que solucionan las dos partes del problema.
*   **Bajo acoplamiento**: Las clases del modelo de dominio no tienen dependencia de cómo se leen o deserializan los datos de entrada. El flujo de control (`Main`) utiliza el sistema de cargadores genéricos `LoaderFactory` y deserializadores de E/S, aislando la lógica del problema de la infraestructura.
*   **Código expresivo**: El código es autoexplicativo, claro y fácil de entender sin necesidad de ser comentado.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI)**:`DialStatus` se compone por referencia del dial `Dial` en lugar de extender su comportamiento o clase, lo que favorece el acoplamiento y reduce la complejidad.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        * [Dial.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java): Representa la entidad física del dial, con su tamaño y posición inicial.
        * [Order.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Order.java): Representa una única instrucción de movimiento, encapsulando la lógica de parseo de caracteres.
        * [DialStatus.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java): representa el estado del dial en un momento concreto y permite transiciones a nuevos estados mediante órdenes individuales.
        * [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java): Alberga la lógica algorítmica específica para contar las veces que el dial se sitúa o cruza por cero.
        * [TxtOrderDeserializer.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java): Su única responsabilidad es deserializar una línea de texto plano a un objeto `Order`.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   El modelo del dial y de su estado permanece estable: su semántica básica no cambia aunque cambie la forma de analizarlo. Las variantes del problema, como nuevas formas de contar posiciones o cruces por cero, se incorporan como extensiones del diseño sin tener que modificar el núcleo del dominio.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   [TxtOrderDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Order>`, pudiendo inyectarse transparentemente en cualquier cargador que precise un deserializador compatible.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista y cohesiva que expone un único método (`deserialize()`), evitando forzar la implementación de métodos innecesarios.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   [Main.java:L19-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java#L19-L21): Depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Order>` en lugar de acoplarse directamente a implementaciones de bajo nivel.
*   **Don’t Repeat Yourself (DRY)**: El algoritmo matemático de conteo de cruces y desbordamiento se delega en la clase `DialCalculator`, siendo consumido de igual manera en ambas partes.
*   **Law of Demeter (LoD - Ley de Deméter)**: Las clases interactúan unicamente con las entidades directas, por ejemplo, la clase `DialCalculator`solo interactúa con métodos de alto nivel de `DialStatus`, respetando el principio de mínimo conocimiento entre objetos.
*   **Principio de mínimo compromiso**: Los métodos públicos de `DialStatus` (`position()`, `next(index)`) y de `DialCalculator` (`countEndingInZero()`, `countCrossingZero()`) encapsulan los cálculos intermedios y la lógica de normalización del dial.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:
*   **Inmutabilidad del modelo**:
    *   Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. La clase `Dial` es la entidad estática y completamente inmutable (sin estado). Las propiedades que cambian con las acciones (el historial de órdenes y la posición) se encapsulan en la clase `DialStatus`. Al ejecutar nuevas órdenes con `execute()`, se devuelve un nuevo `DialStatus` que hace referencia al mismo `Dial`, evitando la recreación innecesaria de la propia entidad del modelo. El record `Order` es también inmutable.
*   **Programación funcional (con Java Streams)**:
    *   El modelo no consume listas directamente; la composición funcional se realiza sobre la secuencia de estados generados por `execute(Order)`.
    *   [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java) (`countEndingInZero()`): Usa `IntStream.range(0, status.size())` para evaluar la posición siguiente de cada paso con `status::next`, filtrar las que son `0` y contarlas de forma declarativa.
    *   [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java) (`countCrossingZero()`): Utiliza `IntStream.range(0, status.size())` para mapear cada orden al número de cruces por cero intermedios con `status::crossZeroAt` y sumarlos.
    *   [DialStatus.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java): El cálculo de la posición acumulada y de los cruces por cero se expresa con streams y operaciones de agregación sobre la lista de órdenes.
*   **Inyección de dependencias**: Inyección del deserializador en la factoría de carga a través del flujo principal.
*   **Genéricos**: Uso de interfaces parametrizadas `Deserializer<T>` para el manejo seguro de los objetos de datos.
*   **Good Naming**: Nombres explícitos como `INITIAL_POSITION`, `crossZeroAt()`, `countCrossingZero()` o `countEndingInZero()` aseguran que el código sea autodocumentado.
*   **Fluent API**: El modelo expone una API inmutable donde las transiciones de estado devuelven nuevas instancias del objeto en lugar de mutar el estado interno. Cada llamada a `execute(Order)` produce un nuevo `DialStatus`, preservando el estado anterior. La composición de múltiples órdenes se realiza de forma externa mediante iteración o reducción funcional, lo que permite mantener la simplicidad del modelo sin introducir estructuras de control dentro del dominio. Este enfoque conserva la expresividad del flujo de transformación del estado sin acoplar el modelo a cómo se agregan las operaciones.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   [Dial.java:L13-L15](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L13-L15) mediante el método estático `Dial.create()`. Este método encapsula la construcción del dial y ofrece un punto de entrada limpio en el punto de uso.
        *   [DialStatus.java:L15-L17](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialStatus.java#L15-L17) mediante el método estático `DialStatus.initial(Dial)`. Este método encapsula la construcción del estado inicial del dial y ofrece un punto de entrada limpio en el punto de uso.
*   **Patrones funcionales**:
    *   **Closure**: Se utiliza en [DialCalculator.java](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/DialCalculator.java) mediante expresiones lambda y referencias a métodos como `status::next` y `status::crossZeroAt`, permitiendo componer la lógica de negocio de forma declarativa e inmutable.

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
*   **Cálculo de la posición final**: Verificación de que el dial calcula su posición correcta tras múltiples llamadas secuenciales a execute(Order), incluyendo rotaciones a la izquierda (L) y derecha (R), y desbordamientos cíclicos (ej. por encima de 99 o por debajo de 0).
*   **Veces en posición cero al finalizar movimientos**: Validación del conteo de cuántas veces el dial termina exactamente en la posición 0 al final de cada orden de rotación.
*   **Casos de borde / límite**: 
    *   Lista de órdenes vacía (`given_empty_orders_should_remain_at_50_and_count_zero_zeros`).
    *   Giros exactos que finalizan exactamente en la posición 0 (`L50`, `L150`).
    *   Giros completos de 360 grados (`R100`, `L100`) que regresan a la posición de partida.

#### Parte B (`BTest/DialTest`)
Además de los casos de inicialización, posición final y casos vacíos o cíclicos (similares a la Parte A):
*   **Total de pasos por cero durante el movimiento**: Validación precisa de cuántas veces el dial pasa por el 0 **en cualquier momento del trayecto del giro** (por ejemplo, giros continuos de más de una vuelta como `"R1000"` que cruza por cero 10 veces).
*   **Cruces en giros exactos que terminan en cero**: Validación de que giros que finalizan exactamente en `0` (como `L50` o `L150`) registran correctamente todos los pasos intermedios y finales por el cero.

