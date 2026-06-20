# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Arquitectura y diseño

La solución implementa los principios de **Clean Architecture** y **SOLID**, priorizando la legibilidad, mantenibilidad y la separación de responsabilidades.

### Fundamentos de diseño
*   **Alta cohesión**: La clase `Dial` se centra exclusivamente en la lógica del dominio (estado y matemática del movimiento), delegando cualquier tarea de lectura o parseo. Esto facilita su comprensión y testeo aislado.
*   **Bajo acoplamiento**: El flujo principal (`Main`) depende de la abstracción `Deserializer`, no de implementaciones concretas. Esto permite intercambiar la fuente o formato de los datos sin afectar al orquestador.
*   **Código expresivo**: El uso de **Records** (`Order`) define estructuras de datos inmutables de forma concisa. La utilización de **Streams** en los cálculos permite una lectura declarativa del algoritmo.

### Diagramas UML

A continuación se muestran los diagramas de clases UML que modelan la solución:

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day01a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day01b.png) |
### Principios SOLID

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

### Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se utiliza `Dial.create()` y `LoaderFactory.txt(...)`. Estos métodos estáticos encapsulan la complejidad de la creación de objetos, ofreciendo una API limpia y expresiva en el punto de uso.
*   **Patrón Iterator**:
    *   *Implementación*: Mediante **Java Streams**, el sistema recorre y procesa las secuencias de órdenes abstrayendo el mecanismo de iteración subyacente.

## Pruebas realizadas

Se han desarrollado tests unitarios automatizados utilizando **JUnit** y **AssertJ** para validar tanto el parseo de datos como la lógica de negocio en diversos escenarios, incluyendo casos límite.

### Rutas de las pruebas
*   **Tests de Deserialización (Parte A)**: [`src/test/java/test/Day01/ATest/TxtOrderDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day01/ATest/TxtOrderDeserializerTest.java)
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
