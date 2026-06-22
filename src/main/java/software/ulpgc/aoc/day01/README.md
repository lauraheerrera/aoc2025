# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day01a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day01b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: La clase `Dial` encapsula la lógica matemática modular y de desbordamientos tras una interfaz pública simple (`position()`, `count()`, `countTotalZeros()`).
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado y facilita su evolución o reutilización futura.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. El record `Order` solo contiene el dato del paso, y `Dial` procesa de forma exclusiva el comportamiento y posicionamiento físico del dial.
*   **Bajo acoplamiento**: Las dependencias entre módulos se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que le permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de modelo.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.
*   **Inmutabilidad del modelo**: El record `Order` es inmutable. Asimismo, la clase `Dial` es inmutable por diseño: cuando se ejecuta una nueva lista de órdenes mediante `execute()`, este método no modifica el estado interno sino que devuelve una nueva instancia combinada de `Dial`, evitando efectos secundarios.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: La lógica de cálculo de posición y estado reside exclusivamente en `Dial`.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: `Dial` opera sobre una lista abstracta de `Order`. Podemos extender el comportamiento agregando nuevos tipos de órdenes o lógicas de negocio sin necesidad de alterar la implementación principal de `Dial.execute`.

## Técnicas de diseño aplicadas

Se han utilizado diversas técnicas de ingeniería de software para asegurar la robustez y limpieza del proyecto:

*   **Programación funcional (con Java Streams)**:
    *   *Definición*: Paradigma de programación basado en la composición y aplicación de funciones, donde las operaciones se expresan de forma declarativa, describiendo qué se quiere obtener en lugar de cómo realizar cada paso. En Java, se materializa mediante expresiones lambda, referencias a métodos e interfaces funcionales, y a través de la API de Streams, que permite transformar, filtrar y agregar datos mediante operaciones encadenadas. Esto favorece un código más legible, modular y con menor dependencia de estados mutables.
    *   *Implementación*: Empleado en `Dial` para procesar la lista de órdenes y realizar sumas y conteos de forma declarativa utilizando la API de Streams y expresiones lambda.
*   **Fluent API**:
    *   *Definición*: Patrón que devuelve la propia instancia del objeto en cada método, permitiendo encadenar llamadas de forma legible. Mejora la expresividad del código.
    *   *Implementación*: Empleado en la clase `Dial`, cuyos métodos `create()` y `execute(...)` permiten encadenar operaciones de forma fluida (`Dial.create().execute(...).execute(...)`), favoreciendo un estilo de programación más legible e inmutable.
*   **Good Naming**: Nombres de variables y métodos descriptivos como `INITIAL_POSITION`, `calculateZerosCrossed()`, o `countTotalZeros()` aseguran que el código sea autodocumentado.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Definición*: Patrón creacional que encapsula la creación de objetos mediante un método estático, en lugar de usar directamente el constructor de la clase. El constructor suele ser privado o protegido, y el método estático se encarga de controlar la instanciación.
    *   *Implementación*: Se utiliza `Dial.create()`. Este método estático encapsula la complejidad de la creación de la instancia de `Dial`, ofreciendo una API limpia y expresiva en el punto de uso.
*   **Patrón Iterator**:
    *   *Definición*: Patrón de comportamiento. Proporciona un acceso secuencial a los elementos de una colección sin exponer su estructura interna. Separa la lógica de iteración de la estructura de datos, promoviendo la modularidad y facilitando la reutilización de código.
    *   *Implementación*: Mediante **Java Streams**, el sistema recorre y procesa las secuencias de órdenes abstrayendo el mecanismo de iteración subyacente.
*   **Patrón funcional Closure**:
    *   *Definición*: Patrón funcional. Una closure es una función o clase anónima que captura variables de su contexto de creación. Permite crear un objeto que encapsula lógica (función) y datos (estado capturado).
    *   *Implementación*: Uso de expresiones lambda dentro del flujo de `IntStream` y de Java Streams para capturar el contexto de ejecución de forma inmutable y legible.

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

