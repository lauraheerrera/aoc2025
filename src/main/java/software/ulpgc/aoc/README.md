# Advent Of Code - Arquitectura General

Este repositorio contiene las soluciones a los retos del Advent Of Code, diseñadas siguiendo estrictos principios de **Ingeniería de Software**: Clean Code, SOLID y Abstracciones Reutilizables.

## Arquitectura Modular

El proyecto está estructurado para maximizar la cohesión y minimizar el acoplamiento.

### Módulo `common` (Core Compartido)

Para evitar la duplicación de código y establecer contratos estándar en toda la aplicación, se ha creado el paquete `software.ulpgc.aoc.common`.

*   **Abstracción de Entrada/Salida**:
    *   `Deserializer<T>`: Interfaz genérica que estandariza cómo se transforman las líneas de texto en objetos de dominio.
    *   `LoaderFactory` / `TxtLoader`: Implementación genérica del patrón Strategy/Factory para la carga de archivos, permitiendo que `Main` se centre en *qué* cargar y no en *cómo* leer del disco.

Esto permite que, si en el futuro se decide cambiar la forma de leer archivos (ej. de red, base de datos), solo haya que tocar este módulo común, y todos los días heredarán la mejora automáticamente.

## Estructura por Día

Cada día (`day01`, `day02`, etc.) es un módulo autocontenido que sigue la misma estructura interna:

*   `model`: **Capa de Dominio**. Objetos de Negocio (POJOs/Records) y lógica pura. Inmutables donde sea posible.
*   `io`: **Capa de Infraestructura**. Implementaciones concretas de los contratos definidos en `common` (ej. `TxtOrderDeserializer` implementa `Deserializer<Order>`).
*   `a` / `b`: **Capa de Aplicación**. Puntos de entrada (`Main`) que orquestan las dependencias (Inyección de Dependencias manual) y ejecutan la solución específica para la Parte 1 y Parte 2.

## Principios Transversales

1.  **Uniformidad**: Todos los días resuelven problemas distintos usando los mismos patrones de diseño.
2.  **Testabilidad**: La lógica de negocio está desacoplada de la entrada/salida, lo que facilita los tests unitarios.
3.  **Extensibilidad**: El uso de Generics y Interfaces permite añadir nuevas funcionalidades (como nuevas reglas de validación en el Día 2) sin modificar el código base.
