# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento (durante la rotación o al final).

## Arquitectura y Diseño

La solución se ha diseñado siguiendo principios de **Clean Architecture** y **SOLID**, priorizando la legibilidad, mantenibilidad y la separación de responsabilidades.

### Principios SOLID Aplicados

*   **Single Responsibility Principle (SRP)**:
    *   `Dial`: Encapsula exclusivamente la lógica de negocio (estado del dial, cálculos de posición).
    *   `TxtOrderDeserializer`: Responsable únicamente de transformar texto en objetos `Order`.
    *   `OrderLoader`: Se encarga de la abstracción de la fuente de datos.

*   **Open/Closed Principle (OCP)**:
    *   El sistema depende de abstracciones (`OrderDeserializer`). Nuevos formatos de entrada pueden añadirse implementando esta interfaz sin modificar la lógica del `Dial`.

*   **Dependency Inversion Principle (DIP)**:
    *   Los módulos de alto nivel (la lógica en `Main`) dependen de abstracciones (interfaces), no de detalles de implementación de bajo nivel (como la lectura de archivos específica).

### Decisiones Técnicas

*   **Inmutabilidad (Records)**:
    *   Uso del record `Order` para modelar las instrucciones. Esto garantiza la integridad de los datos a través de todo el flujo de ejecución.
    
*   **Programación Funcional (Streams)**:
    *   Uso extensivo de **Java Streams** dentro de `Dial` para realizar cálculos de posición y conteos. Esto elimina la necesidad de bucles con estado mutable, reduciendo la complejidad ciclomática y posibles errores de lógica.
    *   Los métodos `calculateSum`, `calculatePartialSum` y `count` son puros y declarativos.

*   **Manejo de Colecciones**:
    *   Uso de `List<Order>` para mantener la secuencia determinista de instrucciones.

### Estructura del Código

*   `model`: Núcleo de la lógica de negocio (`Dial`) y definiciones de datos (`Order`).
*   `io`: Capa de entrada de datos, desacoplada del dominio.
*   `a` / `b`: Paquetes que contienen los puntos de entrada (`Main`) específicos para cada parte del problema, reutilizando el mismo núcleo de dominio.
