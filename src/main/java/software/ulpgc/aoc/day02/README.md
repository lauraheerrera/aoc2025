# Day 2: Gift Shop

## Descripción

El desafío consiste en validar identificadores numéricos dentro de rangos específicos. Los identificadores son considerados "inválidos" si cumplen con ciertos patrones de repetición.
1.  **Parte 1**: Un ID es inválido si consiste en una secuencia de dígitos repetida exactamente dos veces (ej: `1212`, `55`).
2.  **Parte 2**: Un ID es inválido si consiste en una secuencia de dígitos repetida *al menos* dos veces (ej: `123123123`).

El objetivo final es sumar todos los IDs inválidos encontrados en los rangos proporcionados.

## Arquitectura y Diseño

La solución utiliza **Generics** para permitir la reutilización total del código de procesamiento de rangos, independientemente de la regla de validación específica.

### Fundamentos de Diseño
*   **Abstracción**: `InvalidatableId` define el contrato esencial de un identificador, ocultando si la validación es aritmética o por patrones de texto. `GiftShop` opera exclusivamente sobre esta abstracción.
*   **Modularidad**: La lógica de validación concreta está encapsulada en paquetes separados (`a.model`, `b.model`). Esto permite mantener el núcleo del sistema limpio y agnóstico a las reglas específicas de cada variante del problema.

### Principios de Diseño
*   **Principio Abierto/Cerrado (OCP)**:
    *   *Definición*: El software debe permitir añadir nueva funcionalidad sin cambiar código existente.
    *   *Implementación*: La infraestructura de `GiftShop` e `IdRange` es genérica. Para soportar nuevas reglas de validación (como las de la Parte 2), el diseño permite simplemente crear una nueva clase que implemente `Id`, sin necesidad de modificar la lógica de procesamiento existente.
*   **Principio de Sustitución de Liskov (LSP)**:
    *   *Definición*: Subtipos deben ser sustituibles por sus tipos base.
    *   *Implementación*: Las clases `software.ulpgc.aoc.day02.a.model.Id` y `b.model.Id` son intercambiables bajo la interfaz `InvalidatableId`. EL sistema funciona correctamente inyectando cualquiera de ellas, garantizando una correcta jerarquía de tipos.
*   **Principio de Responsabilidad Única (SRP)**:
    *   *Definición*: Una clase debe tener una única responsabilidad.
    *   *Implementación*: `GiftShop` es responsable de la agregación; `IdRange` de la generación de secuencias; `Id` de la validación unitaria.

### Patrones de Diseño
*   **Patrón Factory Method**:
    *   *Implementación*: Se inyecta una referencia a método (`Id::create`) en el deserializador. Esto actúa como una factoría dinámica que permite instanciar el tipo concreto de ID necesario en tiempo de ejecución, manteniendo el deserializador genérico.
*   **Patrón Iterator**:
    *   *Implementación*: Se utiliza `LongStream` para la iteración sobre rangos. Esto abstrae la complejidad de manejar bucles sobre conjuntos de datos potencialmente masivos de forma eficiente y "lazy".

### Decisiones Técnicas

*   **Generics**: El uso de `GiftShop<T>` permite una arquitectura fuertemente tipada pero flexible.
*   **Polimorfismo**: La solución aprovecha el polimorfismo para elegir la estrategia de validación más adecuada para cada caso (Aritmética para eficiencia en Parte 1, Strings para flexibilidad en Parte 2).

## Pruebas Realizadas

Se han implementado tests exhaustivos en `IDTest` para validar tanto la lógica individual de cada ID como la agregación correcta en la tienda:

*   **Validación de IDs (Unitario)**:
    *   Tests para confirmar que IDs válidos (`1234`) pasan y que IDs inválidos (`1212`, `446446`) son detectados correctamente según las reglas vigentes.
    *   Verificación de conteo de dígitos para asegurar robustez en el parsing.
*   **Cálculo de Totales (Integración)**:
    *   Validación con el conjunto de datos de ejemplo, asegurando que `GiftShop` suma correctamente todos los IDs inválidos encontrados.
*   **Deserialización Genérica**: Confirmación de que el deserializador construye correctamente los objetos `IdRange` tipados dinámicamente.
