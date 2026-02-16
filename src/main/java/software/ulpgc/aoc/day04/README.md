# Day 4: Printing Department

## Descripción

El desafío consiste en optimizar el acceso a rollos de papel (`@`) en una cuadrícula.
1.  **Parte 1**: Determinar rollos accesibles (menos de 4 vecinos) en estado inicial.
2.  **Parte 2**: Simulación iterativa de eliminación de rollos accesibles hasta convergencia.

## Arquitectura y Diseño

La solución modela el problema mediante una simulación de estados inmutables sobre una cuadrícula, priorizando la claridad del código.

### Fundamentos de Diseño
*   **Código Expresivo**:
    *   Se utiliza un **Enum** `Direction` (`NORTH`, `SOUTH_EAST`, etc.) para representar los vectores de movimiento. Esto permite leer el código en lenguaje natural (ej. `dir.rowOffset`), eliminando la carga cognitiva asociada al uso de "números mágicos" o matrices de enteros crudos (`int[][]`).
*   **Alta Cohesión**: La lógica relacionada con la navegación y vecindad está encapsulada dentro de `Direction` y `DiagramAnalyzer`, manteniendo la estructura de datos `Diagram` limpia.

### Principios de Diseño
*   **Principio de Responsabilidad Única (SRP)**:
    *   *Definición*: Cada clase debe encargarse de una única parte de la funcionalidad.
    *   *Implementación*: Existe una separación estricta entre Estado y Comportamiento.
        *   `Diagram`: Actúa como un contenedor de datos inmutable.
        *   `DiagramAnalyzer`: Contiene las reglas de negocio (criterios de accesibilidad).
*   **Principio YAGNI (You Aren't Gonna Need It)**:
    *   *Definición*: No implementar funcionalidad no requerida.
    *   *Implementación*: Se optó por una simulación iterativa simple (re-evaluación del tablero) en lugar de implementar sistemas complejos de eventos o grafos, ya que esta solución es suficiente y más mantenible para el problema planteado.

### Patrones de Diseño
*   **Patrón Factory Method**:
    *   *Implementación*: `Diagram.create(List<DiagramLine>)` abstrae el proceso de conversión de la entrada (lista de strings) a la representación interna (matriz), simplificando la creación de instancias para el cliente.
*   **Patrón de Inmutabilidad** (Técnica):
    *   *Implementación*: La clase `Diagram` es inmutable. El método `withClearedCoordinates` no modifica el objeto actual, sino que devuelve una **nueva instancia** con el estado actualizado. Esto hace que cada paso de la simulación sea discreto y predecible, evitando efectos secundarios.

### Decisiones Técnicas

*   **Separación de Estado y Comportamiento**: Esta decisión arquitectónica permite modificar las reglas del juego (ej. cambiar el umbral de vecinos) sin necesidad de alterar la estructura de datos subyacente.
*   **Simulación Iterativa**: El uso de un bucle de convergencia sobre estados inmutables garantiza la corrección de la simulación paso a paso.

## Pruebas Realizadas

Se han diseñado pruebas detalladas en `DiagramAnalyzerTest` para validar la lógica de la cuadrícula:

*   **Vecindad Trivial**: Caso base con objetivos aislados para confirmar la detectabilidad básica.
*   **Reglas de Límite**: Pruebas con bloques densos (ej. cuadrados de 4) para verificar que la regla de "menos de 4 vecinos" filtra correctamente los elementos interiores.
*   **Escenarios Complejos**: Ejecución con el tablero de ejemplo completo para validar la integración de todas las reglas.
*   **Validación Espacial**: Confirmación de que el manejo de coordenadas y límites (`isInBounds`) a través del Enum `Direction` es correcto en bordes y esquinas.
