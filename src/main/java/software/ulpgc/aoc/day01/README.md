# Day 1: Secret Entrance

## Descripción

El desafío consiste en simular un dial de seguridad que gira en base a una serie de instrucciones ("R" para derecha, "L" para izquierda) para descubrir el código secreto.
1.  **Parte 1**: Calcular cuántas veces el dial termina exactamente en la posición `0` al finalizar una rotación.
2.  **Parte 2**: Calcular cuántas veces el dial pasa por la posición `0` en cualquier momento del movimiento.

## Arquitectura y Diseño

La solución implementa los principios de **Clean Architecture** y **SOLID**, priorizando la legibilidad, mantenibilidad y la separación de responsabilidades.

### Fundamentos de Diseño
*   **Alta Cohesión**: La clase `Dial` se centra exclusivamente en la lógica del dominio (estado y matemática del movimiento), delegando completamente cualquier tarea de lectura o parseo. Esto facilita su comprensión y testeo aislado.
*   **Bajo Acoplamiento**: El flujo principal (`Main`) depende de la abstracción `Deserializer`, no de implementaciones concretas. Esto permite intercambiar la fuente o formato de los datos sin afectar al orquestador.
*   **Código Expresivo**: El uso de **Records** (`Order`) define estructuras de datos inmutables de forma concisa. La utilización de **Streams** en los cálculos permite una lectura declarativa del algoritmo.

### Principios de Diseño
*   **Principio de Responsabilidad Única (SRP)**:
    *   *Definición*: Cada módulo o clase debe tener una sola razón para cambiar.
    *   *Implementación*: La lógica de cálculo de posición reside en `Dial`, mientras que la interpretación del archivo de texto reside en `TxtOrderDeserializer`. Cada clase evoluciona por razones independientes (cambios en reglas de negocio vs. cambios en formato de archivo).
*   **Principio Abierto/Cerrado (OCP)**:
    *   *Definición*: Las entidades de software deben estar abiertas para la extensión, pero cerradas para la modificación.
    *   *Implementación*: `Dial` está diseñado para operar sobre una lista abstracta de `Order`. El comportamiento del sistema puede extenderse soportando nuevos tipos de órdenes o pre-procesamientos sin necesidad de alterar el código interno del método `execute`.
*   **Principio de Inversión de Dependencias (DIP)**:
    *   *Definición*: Depender de abstracciones, no de concreciones.
    *   *Implementación*: El sistema utiliza la interfaz `Deserializer<Order>` para obtener los datos, desacoplando completamente la lógica de negocio de la infraestructura de entrada.

### Patrones de Diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se utiliza `Dial.create()` y `LoaderFactory.txt(...)`. Estos métodos estáticos encapsulan la complejidad de la creación de objetos, ofreciendo una API limpia y expresiva en el punto de uso.
*   **Patrón Iterator**:
    *   *Implementación*: Mediante **Java Streams**, el sistema recorre y procesa las secuencias de órdenes abstrayendo el mecanismo de iteración subyacente.

### Decisiones Técnicas

*   **Inmutabilidad**: El uso de `record` para las órdenes asegura que los datos no puedan ser modificados accidentalmente durante el procesamiento.
*   **Programación Funcional**: Los métodos clave (`calculateSum`, `count`) están implementados como cadenas de operaciones funcionales, lo que elimina el estado mutable inherente a los bucles imperativos.

## Pruebas Realizadas

Se han desarrollado tests unitarios utilizando **JUnit** y **AssertJ** (`DialTest`) para verificar la corrección del algoritmo bajo diversos escenarios:

*   **Posiciones Iniciales y Finales**: Verificación de que el dial comienza en 50 y termina en la posición correcta tras una secuencia compleja de movimientos (ej. movimientos que cruzan el límite 0/99).
*   **Cruces por Cero**: Validación precisa del conteo de veces que el dial toca o cruza el 0, tanto al finalizar una orden como durante la ejecución de la misma.
*   **Integración de Carga**: Verificación de que el flujo completo (Carga -> Deserialización -> Ejecución) funciona con los datos de ejemplo y casos límite (órdenes vacías, movimientos grandes).
