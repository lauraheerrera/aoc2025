# Day 2: Gift Shop

## Descripción

El desafío consiste en validar identificadores numéricos dentro de rangos específicos. Los identificadores son considerados "inválidos" si cumplen con ciertos patrones de repetición.
1.  **Parte 1**: Un ID es inválido si consiste en una secuencia de dígitos repetida exactamente dos veces (ej: `1212`, `55`).
2.  **Parte 2**: Un ID es inválido si consiste en una secuencia de dígitos repetida *al menos* dos veces (ej: `123123123`).

El objetivo final es sumar todos los IDs inválidos encontrados en los rangos proporcionados.

## Arquitectura y Diseño

La solución profundiza en el uso de **Generics** para maximizar la reutilización del código entre ambas partes del problema, manteniendo una arquitectura limpia y modular.

### Principios SOLID Aplicados

*   **Open/Closed Principle (OCP)**:
    *   La infraestructura principal (`GiftShop`, `IdRange`, `TxtRangeDeserializer`) se ha diseñado utilizando genéricos (`<T extends InvalidatableId>`).
    *   Para implementar la "Parte 2", no fue necesario modificar ni una sola línea de la lógica de rangos o de la tienda. Simplemente se creó una nueva implementación del record `Id` (en el paquete `b`) con la nueva regla de validación. El sistema está cerrado a modificaciones pero abierto a nuevas reglas de validación.

*   **Single Responsibility Principle (SRP)**:
    *   `GiftShop`: Agrega los resultados de múltiples rangos.
    *   `IdRange`: Genera el flujo de números dentro de un rango y aplica el filtrado.
    *   `InvalidatableId`: Define *únicamente* el contrato de validación, delegando la regla específica a las implementaciones concretas.
    *   `TxtRangeDeserializer`: Se encarga exclusivamente del parsing de la entrada de texto.

*   **Liskov Substitution Principle (LSP)**:
    *   Gracias al diseño genérico, caulquier clase que implemente `InvalidatableId` puede ser utilizada por `GiftShop` y `IdRange` sin alterar el comportamiento correcto del programa.

### Decisiones Técnicas

*   **Uso de Generics**:
    *   Clases como `GiftShop<T>` y `IdRange<T>` permiten que la lógica de iteración y acumulación sea agnóstica al tipo específico de ID. Esto reduce drásticamente la duplicación de código.
    *   El `Deserializer` acepta una `LongFactory<T>`, permitiendo inyectar dinámicamente el constructor del tipo de ID deseado (`Id::create`).

*   **Estrategias de Validación (Polimorfismo)**:
    *   **Parte 1 (Aritmética)**: Para la regla simple (mitad izquierda == mitad derecha), se utilizaron operaciones matemáticas (`/` y `%`) sobre potencias de 10. Esto es generalmente más eficiente que la manipulación de cadenas para operaciones numéricas simples.
    *   **Parte 2 (Patrones)**: Para la regla compleja (secuencias repetidas N veces), se optó por convertir el número a `String`. Esto facilita la búsqueda de patrones repetitivos mediante iteración y subdivisión de cadenas, lo cual sería muy complejo de implementar puramente con matemáticas.

*   **Streams Primitivos**:
    *   Se utiliza `LongStream` en `IdRange`. Esto es crucial para el rendimiento al trabajar con rangos numéricos grandes, evitando el coste del "boxing" (convertir `long` a `Long`) que ocurriría con un `Stream<Long>` estándar.

### Estructura del Código

*   `model`: Contiene la lógica de negocio genérica (`GiftShop`, `IdRange`) y la interfaz base (`InvalidatableId`).
*   `io`: Manejo de entrada/salida genérico.
*   `a.model` / `b.model`: Implementaciones concretas de la lógica de validación específica para cada parte del problema.
