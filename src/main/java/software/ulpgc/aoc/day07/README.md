# Day 7: Manifold

## Descripción

El desafío consiste en simular la división de flujos (splits) y el recuento de caminos a través de una rejilla de divisores (`^`). El sistema se inicializa en una columna de partida marcada como `S` en la primera fila.
1.  **Parte 1**: Calcular cuántas divisiones de haz (splits) ocurren en total al desplazarse hacia abajo por las filas.
2.  **Parte 2**: Calcular el número total de caminos distintos posibles desde la salida `S` hasta el final de la rejilla.

## Diagramas UML

| Parte A | Parte B |
| :---: | :---: |
| ![Diagrama UML Parte A](../../../../../../../UML%20diagrams/uml_day07a.png) | ![Diagrama UML Parte B](../../../../../../../UML%20diagrams/uml_day07b.png) |

## Fundamentos de diseño

La solución está construida siguiendo los fundamentos de la ingeniería del software:

*   **Abstracción**: Toda la lógica matemática y de propagación de caminos está encapsulada en las clases del modelo `Manifold` y `Paths`, ocultando al exterior la forma en la que se calculan las transiciones.
*   **Modularidad**: Organización limpia en paquetes específicos de lógica de entrada/salida (`io`) y lógica de negocio (`model`), permitiendo testear de forma aislada cada una de las partes.
*   **Alta cohesión**: Las clases y registros representan conceptos atómicos del problema: `Column` (coordenada horizontal), `Row` (fila con celdas), `Tile` (tipo de celda), `Grid` (matriz de celdas), `Paths` (caminos acumulados) y `Manifold` (orquestador del cálculo).
*   **Bajo acoplamiento**: La lectura de datos se realiza a través de interfaces (`ManifoldLoader`), haciendo que el flujo principal de ejecución no dependa directamente de la lectura física del archivo.
*   **Código expresivo**: Se resuelven las propagaciones por filas mediante acumulaciones declarativas usando el método `reduce` sobre los flujos de filas (`grid.rows().stream().reduce(...)`), lo que evita la necesidad de bucles de control anidados complejos y variables globales mutables.
*   **Diseño por contrato**: Definición de interfaces limpias y cohesivas para la carga y deserialización de datos.
*   **Inmutabilidad del modelo**: Las estructuras de datos principales (`Column`, `Row`, `Grid`, `Paths`, `Manifold`) se implementan como **Records** inmutables. Las transiciones de estado por cada fila devuelven nuevas instancias de `Paths` o de los estados intermedios, protegiendo al sistema contra efectos secundarios.

## Principios SOLID

El proyecto está diseñado siguiendo rigurosamente los principios **SOLID**:

*   **Principio de Responsabilidad Única (SRP - Single Responsibility Principle)**:
    *   *Definición*: Cada clase debe tener una única razón para cambiar.
    *   *Implementación*: `Column` se encarga de la navegación horizontal, `Row` contiene los elementos de una fila y sabe si hay un divisor, `Paths` calcula la propagación matemática de los caminos, y `Manifold` realiza el cálculo del problema.
*   **Principio Abierto/Cerrado (OCP - Open/Closed Principle)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: La rejilla `Grid` y las estructuras espaciales son reutilizables, lo que permitió añadir el cálculo de caminos de la Parte B simplemente extendiendo el modelo con la clase `Paths` y agregando un método a `Manifold` sin modificar la estructura del tablero.
*   **Principio de Sustitución de Liskov (LSP - Liskov Substitution Principle)**:
    *   *Definición*: Los subtipos deben poder reemplazar a sus tipos base sin alterar el comportamiento correcto del programa.
    *   *Implementación*: El flujo de carga es transparente ante cualquier subtipo o implementación de la interfaz `ManifoldLoader`.
*   **Principio de Segregación de Interfaces (ISP - Interface Segregation Principle)**:
    *   *Definición*: No se debe obligar a una clase a implementar interfaces que no utiliza.
    *   *Implementación*: `ManifoldLoader` contiene únicamente la firma necesaria para la lectura del manifold.
*   **Principio de Inversión de Dependencias (DIP - Dependency Inversion Principle)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: `TxtManifoldLoader` depende de la interfaz `Deserializer<Manifold>` para procesar la entrada de datos.

## Técnicas de diseño aplicadas

*   **Inyección de dependencias**: La factoría y cargador reciben el deserializador por constructor.
*   **Genéricos**: Uso de la interfaz común `Deserializer<T>` parametrizada para la entidad `Manifold`.
*   **Good Naming**: Nombres de variables y métodos autodescriptivos como `countSplits()`, `countPaths()`, `isSplitterAt()`, `findStartColumn()`.

## Patrones de diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Uso de métodos de creación estáticos como `Grid.from()` y `Row.from()`.
*   **Patrón Iterator**:
    *   *Implementación*: Uso de Java Streams (`rows().stream()`, `IntStream.range()`) para recorrer y mapear la matriz espacial.

---

## Pruebas realizadas

Se han diseñado pruebas detalladas utilizando **JUnit** y **AssertJ** para validar el comportamiento del motor de cálculo de flujos sobre la rejilla.

### Rutas de las pruebas
*   **Tests de Deserialización**: [`src/test/java/test/Day07/ATest/TxtManifoldDeserializerTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/ATest/TxtManifoldDeserializerTest.java)
*   **Tests de la Parte A**: [`src/test/java/test/Day07/ATest/ManifoldTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/ATest/ManifoldTest.java)
*   **Tests de la Parte B**: [`src/test/java/test/Day07/BTest/ManifoldTest.java`](https://github.com/lauraheerrera/aoc2025/blob/master/src/test/java/test/Day07/BTest/ManifoldTest.java)

### Escenarios validados

#### Deserialización (`TxtManifoldDeserializerTest`)
*   **Parseo correcto**: Comprobación del correcto parseo de la rejilla de caracteres y dimensiones.
*   **Robustez**: Excepciones correspondientes ante bloques de texto nulos o vacíos.

#### Parte A (`ATest/ManifoldTest`)
*   **Comportamiento de Columnas y Filas**: Validación de la obtención del índice, el movimiento lateral y la correcta identificación de celdas vacías, de divisor o de inicio.
*   **Comportamiento de Celdas (Tile)**: Verificación de que mapea los caracteres de forma segura y lanza `IllegalArgumentException` si detecta un caracter desconocido.
*   **Cálculo de Splits**: Verificación del número de splits de los ejemplos base y del ejemplo complejo de 15x15, esperando un resultado de `21L`.

#### Parte B (`BTest/ManifoldTest`)
*   **Propagación de Caminos (Paths)**: Validación del cálculo matemático de caminos a través de los divisores y su acumulación dinámica por fila.
*   **Conteo de Caminos del Manifold**: Confirmación de la suma total de caminos del ejemplo, esperando un valor de `40` (representado como `BigInteger`).
