# Advent Of Code - Arquitectura General

Este repositorio contiene las soluciones a los retos del Advent Of Code, diseñadas siguiendo estrictos principios de **Ingeniería de Software**. A continuación se detalla cómo se aplican los conceptos teóricos de la asignatura.

## Fundamentos de Diseño

La arquitectura global del proyecto se cimienta en:

*   **Modularidad**: El sistema está claramente dividido en un módulo núcleo (`common`) y módulos específicos para cada problema (`dayXX`). Esto facilita la navegación y el mantenimiento.
*   **Abstracción**: Se utiliza la interfaz genérica `Deserializer<T>` en el paquete `common` para ocultar la complejidad del parseo de datos. El resto del sistema interactúa con esta abstracción, desconociendo los detalles de implementación (textos, arrays, etc.).
*   **Bajo Acoplamiento**: Los módulos de alto nivel (los `Main` de cada día) no dependen de implementaciones concretas de lectura de archivos, sino de abstracciones (`Loader`, `Deserializer`). Esto permite cambiar la fuente de datos sin afectar a la lógica de negocio.
*   **Alta Cohesión**: El paquete `common` contiene exclusivamente clases relacionadas con la infraestructura transversal (IO genérico), mientras que cada paquete `dayXX` contiene solo la lógica de ese dominio específico.

## Principios de Diseño

*   **Principio de No Repetir Código (DRY)**:
    *   Se identificó que la lectura de archivos de texto era idéntica en todos los días. En lugar de copiar y pegar el código del `BufferedReader`, se centralizó esta lógica en la clase `TxtLoader` del paquete `common`.
*   **Principio de Inversión de Dependencias (DIP)**:
    *   El código de carga de datos (`TxtLoader`) no depende de una clase concreta como `Order` o `BatteryBank`, sino de un genérico `<T>` y una función de deserialización.

## Patrones de Diseño

*   **Patrón Factory Method**:
    *   La clase `LoaderFactory` provee métodos estáticos (`txt(...)`) que encapsulan la creación compleja de objetos `TxtLoader`. Esto simplifica la creación de objetos desde el cliente.
