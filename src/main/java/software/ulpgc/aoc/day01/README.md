# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Diagrama UML

![Diagrama UML](../../../../../../../UML%20diagrams/uml_day01.png)

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**:
    *   *Definición*: Permite identificar y modelar solo las características esenciales de un objeto, ocultando los detalles irrelevantes para el contexto actual.
    *   *Implementación*: La clase `Dial` encapsula la lógica matemática modular y de desbordamientos tras una interfaz pública simple (`position()`, `count()`, `countTotalZeros()`).
*   **Encapsulamiento**:
    *   *Definición*: El código esconde su complejidad interna, mostrándose al exterior mediante una interfaz más simple de operar.
    *   *Implementación*: El record `Dial` oculta el estado mutable y expone operaciones de simulación, delegando la persistencia de cambios al record `DialStatus`.
*   **Cohesión**:
    *   *Definición*: Se refiere al grado en que los elementos de un módulo —como una clase o función— están relacionados entre sí y colaboran para cumplir una única tarea o propósito. Un módulo se considera altamente cohesivo cuando todas sus partes están directamente conectadas con la responsabilidad central que se le ha asignado, trabajando de forma coordinada hacia un objetivo común.
    *   *Implementación*: Cada componente tiene una única responsabilidad bien enfocada. El record `Order` solo contiene el dato del paso, y `Dial` procesa de forma exclusiva el comportamiento y posicionamiento físico del dial.
*   **Bajo acoplamiento**:
    *   *Definición*: Las dependencias entre módulos son mínimas y se basan en abstracciones.
    *   *Implementación*: Las dependencias entre módulos se basan en abstracciones. El flujo principal (`Main`) utiliza la factoría genérica `LoaderFactory` del paquete `common.io` para leer el fichero, lo que le permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**:
    *   *Definición*: El código es claro y fácil de entender.
    *   *Implementación*: El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**:
    *   *Definición*: Las clases del modelo se definen como Records, asegurando que sus instancias sean totalmente inmutables una vez creadas, lo que favorece la abstracción y evita errores relacionados con efectos secundarios.
    *   *Implementación*: Se establece mediante el patrón de separación entre **Entidad** y **Estado (Status)**. El record `Dial` es la entidad estática y completamente inmutable (sin estado). Las propiedades que cambian con las acciones (el historial de órdenes y la posición) se encapsulan en el record `DialStatus`. Al ejecutar nuevas órdenes con `execute()`, se devuelve un nuevo `DialStatus` que hace referencia al mismo `Dial` inicial, evitando la recreación innecesaria de la propia entidad del modelo y garantizando un diseño funcional libre de efectos secundarios. El record `Order` es también inmutable.

## Principios de diseño

El proyecto está diseñado siguiendo rigurosamente los principios de diseño y **SOLID**:

*   **Composition Over Inheritance (COI)**:
    *   *Definición*: Prefiere la composición de objetos frente a la herencia, utilizando atributos en lugar de extender clases, para mejorar la modularidad y facilitar el mantenimiento.
    *   *Implementación*: `DialStatus` se compone por referencia del dial `Dial` en lugar de extender su comportamiento o clase.
*   **SOLID**:
    *   **Single Responsibility Principle (SRP - Principio de Responsabilidad Única)**:
        *   *Definición*: Cada clase o módulo debe tener una única responsabilidad o razón para cambiar, favoreciendo la cohesión y la claridad del diseño.
        *   *Implementación*:
            *   [Dial.java:L8-L71](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L8-L71): Concentra exclusivamente la lógica matemática del posicionamiento y cálculo de cruces por cero del dial.
            *   [TxtOrderDeserializer.java:L6-L28](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6-L28): Su única responsabilidad es deserializar una línea de texto plano a un objeto `Order`.
    *   **Open/Closed Principle (OCP - Principio de Abierto/Cerrado)**:
        *   *Definición*: Las clases deben estar abiertas a la extensión pero cerradas a la modificación, permitiendo añadir funcionalidad sin alterar el código existente.
        *   *Implementación*:
            *   [Dial.java:L22-L26](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L22-L26) (`execute()`): Opera sobre una lista abstracta `List<Order>`, permitiendo procesar cualquier secuencia de órdenes nueva sin alterar el comportamiento de cálculo interno del dial.
    *   **Liskov Substitution Principle (LSP - Principio de Sustitución de Liskov)**:
        *   *Definición*: Los objetos de una subclase deben poder reemplazar a los de su superclase sin alterar el funcionamiento del programa, garantizando consistencia, modularidad e interoperabilidad y la sustitución segura de componentes (Evolución de la Ley de Deméter).
        *   *Implementación*:
            *   [TxtOrderDeserializer.java:L6](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/io/TxtOrderDeserializer.java#L6): Implementa la interfaz genérica `Deserializer<Order>`, pudiendo inyectarse transparentemente en cualquier cargador que precise un deserializador compatible.
    *   **Interface Segregation Principle (ISP - Principio de Segregación de Interfaces)**:
        *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza, reduciendo el acoplamiento y favoreciendo la especialización.
        *   *Implementación*:
            *   [Deserializer.java:L3-L5](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/common/io/Deserializer.java#L3-L5): Interfaz minimalista y cohesiva que expone un único método (`deserialize()`), evitando forzar la implementación de métodos innecesarios.
    *   **Dependency Inversion Principle (DIP - Principio de Inversión de Dependencias)**:
        *   *Definición*: Los módulos de alto nivel no deben depender de módulos de bajo nivel, sino de abstracciones, lo que disminuye la dependencia entre componentes.
        *   *Implementación*:
            *   [Main.java:L19-L21](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/a/Main.java#L19-L21): La orquestación en el flujo de ejecución principal depende de la factoría genérica `LoaderFactory` y de la interfaz `Deserializer<Order>` en lugar de acoplarse directamente a implementaciones de disco de bajo nivel.
*   **Don’t Repeat Yourself (DRY)**:
    *   *Definición*: Evita la duplicación de código, promoviendo la reutilización mediante funciones o componentes comunes para mejorar la mantenibilidad.
    *   *Implementación*: El algoritmo matemático de conteo cíclico y desbordamiento se delega en el record de utilidad `Dial`, siendo consumido de igual manera en ambas partes.
*   **Law of Demeter (LoD - Ley de Deméter)**:
    *   *Definición*: Una unidad de software debe conocer solo a sus colaboradores directos, evitando el acceso profundo a objetos y reduciendo así el acoplamiento y facilitando la prueba y mantenimiento del código.
    *   *Implementación*: La clase orquestadora interactúa únicamente con `Dial` y `DialStatus` directamente sin navegar por las propiedades de caracteres individuales del record `Order`.
*   **You Aren’t Gonna Need It (YAGNI)**:
    *   *Definición*: No se debe implementar funcionalidad hasta que realmente sea necesaria, evitando complejidad innecesaria.
*   **Convention Over Configuration (CoC - Convención sobre configuración)**:
    *   *Definición*: El sistema debe funcionar con una configuración mínima, asumiendo convenciones por defecto para simplificar su uso.
*   **Principio de mínima sorpresa**:
    *   *Definición*: El comportamiento de un componente debe ser predecible e intuitivo, sin efectos secundarios inesperados.
*   **Principio de mínimo compromiso**:
    *   *Definición*: Una interfaz debe exponer sólo lo necesario para operar, ocultando detalles internos y reduciendo la dependencia entre módulos.
    *   *Implementación*: La interfaz pública de `Dial` oculta los algoritmos intermedios y de optimización de desbordamientos de cruces por cero.
*   **Keep It Simple, Stupid (KISS)**:
    *   *Definición*: El código debe ser claro, directo y fácil de entender, evitando la complejidad innecesaria.

## Diseño por contrato
*   **Definición**: El diseño por contrato es un enfoque de diseño que formaliza los acuerdos entre un componente y sus consumidores (por ejemplo, entre una clase y quien la utiliza), a través de interfaces claras y bien definidas. Se basa en la idea de que cada componente ofrece una serie de servicios bajo ciertas condiciones (precondiciones) y, a cambio, garantiza ciertos resultados (postcondiciones), mientras mantiene invariantes internas.
*   **Implementación**: La firma de la interfaz `Deserializer<Order>` define formalmente el contrato del comportamiento de lectura de órdenes en el sistema.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*:
        *   [Dial.java:L33-L38](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L33-L38) (`count()`): Usa `IntStream.rangeClosed(1, orders.size())` en paralelo para evaluar todas las sumas parciales (`calculatePartialSum()`). Filtra las que son iguales a `0` y las cuenta, determinando de manera declarativa cuántas veces el dial termina en cero tras finalizar un movimiento.
        *   [Dial.java:L40-L44](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L40-L44) (`countTotalZeros()`): Utiliza `IntStream.range(0, orders.size())` para mapear de forma secuencial cada orden al número de cruces por cero intermedios (`calculateZerosCrossed()`) y sumarlos (`sum()`), resolviendo eficientemente la Parte B.
        *   [Dial.java:L50-L52](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L50-L52) (`calculateSum()`): Procesa la lista de órdenes con `orderList.stream()`, transformándola a un stream de enteros con `mapToInt(Order::step)` y sumándola con `sum()`. Esto calcula la posición total recorrida de forma declarativa e inmutable.
*   **Inyección de dependencias**:
    *   *Definición*: Consiste en separar la creación de objetos de su uso. En lugar de que una clase cree sus dependencias, estas son proporcionadas desde fuera, reduciendo el acoplamiento y facilitando la reutilización y prueba del código. Esto se puede hacer con constructores o mediante propiedades.
    *   *Implementación*: Inyección del deserializador en la factoría de carga a través del flujo principal.
*   **Genéricos**:
    *   *Definición*: Permiten definir estructuras de datos tipadas evitando castings.
    *   *Implementación*: Uso de interfaces parametrizadas `Deserializer<T>` para el manejo seguro de los objetos de datos.
*   **Good Naming**:
    *   *Definición*: Consiste en asignar nombres claros, significativos y relacionados con su propósito a clases, variables y métodos, mejorando la claridad y expresividad del código.
    *   *Implementación*: Nombres explícitos como `INITIAL_POSITION`, `calculateZerosCrossed()`, o `countTotalZeros()` aseguran que el código sea autodocumentado.
*   **Inversión del control (IoC)**:
    *   *Definición*: Delega el flujo del programa a un contenedor externo, facilitando la modularidad y reduciendo el acoplamiento.
    *   *Implementación*: Se delega el flujo de lectura al framework de E/S genérico en `TxtLoader`.
*   **Fluent API**:
    *   *Definición*: Patrón que devuelve la propia instancia del objeto en cada método, permitiendo encadenar llamadas de forma legible. Mejora la expresividad del código.
    *   *Implementación*: Empleado en la clase `Dial`, cuyos métodos `create()` y `execute(...)` permiten encadenar operaciones de forma fluida (`Dial.create().execute(...).execute(...)`), favoreciendo un estilo de programación más legible e inmutable.

## Patrones de diseño
*   **Patrones creacionales**:
    *   **Factory Method**:
        *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
        *   *Implementación*: Se utiliza en [Dial.java:L18-L20](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L18-L20) mediante el método estático `Dial.create()`. Este método encapsula la instanciación privada del dial y ofrece una API limpia en el punto de uso.
*   **Patrones de comportamiento**:
    *   **Iterator**:
        *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
        *   *Implementación*: Implementado a través de Java Streams en [Dial.java:L33-L52](https://github.com/lauraheerrera/aoc2025/blob/master/src/main/java/software/ulpgc/aoc/day01/model/Dial.java#L33-L52) (`count()`, `countTotalZeros()` y `calculateSum()`) para recorrer y procesar de forma abstracta las secuencias de órdenes.
*   **Patrones funcionales**:
    *   **Closure**:
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

