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

*   **Abstracción**: Oculta los detalles del parseo y la carga de datos tras las interfaces `Deserializer<Order>` y `OrderLoader`. Asimismo, la clase `Dial` encapsula la lógica matemática modular y de desbordamientos tras una interfaz pública simple (`position()`, `count()`, `countTotalZeros()`).
*   **Modularidad**: Estructura el reto en paquetes independientes (`model`, `io`, `a`, `b`). Esto permite que los componentes se desarrollen y prueben por separado (mediante pruebas unitarias aisladas para deserializadores y modelos) y facilita su evolución o reutilización futura.
*   **Alta cohesión**: Cada componente tiene una única responsabilidad bien enfocada. `TxtOrderDeserializer` se encarga únicamente de parsear cadenas de texto a `Order`, el record `Order` solo contiene el dato del paso, y `Dial` procesa de forma exclusiva el comportamiento y posicionamiento físico del dial.
*   **Bajo acoplamiento**: Las dependencias entre módulos son mínimas y se basan en abstracciones. El flujo principal (`Main`) depende de interfaces, lo que permite cambiar el formato o el cargador de datos sin afectar en absoluto a las clases de dominio.
*   **Código expresivo**: El código es autoexplicativo y legible. El uso de **Records** inmutables y la programación funcional con **Java Streams** permiten que los algoritmos se lean de forma declarativa (evitando variables mutables e instrucciones anidadas), haciendo innecesarios los comentarios aclaratorios.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: La lógica de cálculo de posición y estado reside exclusivamente en `Dial`, mientras que la interpretación y carga del archivo de entrada se delega en `TxtOrderDeserializer` y `OrderLoader`. Cada componente tiene una única responsabilidad bien delimitada.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: `Dial` opera sobre una lista abstracta de `Order`. Podemos extender el comportamiento agregando nuevos tipos de órdenes o lógicas de negocio sin necesidad de alterar la implementación principal de `Dial.execute`.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: La clase `Main` y el flujo principal no dependen directamente de una lectura concreta de archivos, sino de abstracciones como la interfaz `OrderLoader` y `Deserializer<Order>`, facilitando la inyección de dependencias y el desacoplamiento.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se utiliza `Dial.create()` y `LoaderFactory.txt(...)`. Estos métodos estáticos encapsulan la complejidad de la creación de objetos, ofreciendo una API limpia y expresiva en el punto de uso.
*   **Patrón Iterator**:
    *   *Implementación*: Mediante **Java Streams**, el sistema recorre y procesa las secuencias de órdenes abstrayendo el mecanismo de iteración subyacente.

---

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar tanto el parseo de datos como la lógica de negocio en diversos escenarios, incluyendo casos límite.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day01/ATest/TxtOrderDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/TxtOrderDeserializerTest.java)
*   **Tests de Lógica de la Parte A**: [`src/test/java/test/Day01/ATest/DialTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/DialTest.java)
*   **Tests de Lógica de la Parte B**: [`src/test/java/test/Day01/BTest/DialTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/BTest/DialTest.java)

### Escenarios validados

#### Deserialización (`TxtOrderDeserializerTest`)
*   **Parseo correcto**: Validación de giros a la izquierda (`"L5"` -> `-5`) y a la derecha (`"R10"` -> `10`), incluyendo números de varios dígitos.
*   **Gestión de errores y robustez**: 
    *   Lanzamiento de `IllegalArgumentException` ante entradas nulas, vacías o con espacios en blanco.
    *   Lanzamiento de `NumberFormatException` cuando el formato numérico del paso es inválido (ej. `"L"`, `"Rabc"`).

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
