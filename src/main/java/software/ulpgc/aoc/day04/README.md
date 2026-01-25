# Day 4: Printing Department

## Descripción

El desafío consiste en optimizar el acceso a rollos de papel (`@`) en una cuadrícula. Un rollo es accesible si tiene menos de 4 vecinos inmediatos (en las 8 direcciones posibles).
1.  **Parte 1**: Determinar cuántos rollos de papel son accesibles en el estado inicial de la cuadrícula.
2.  **Parte 2**: Simular un proceso iterativo donde los rollos accesibles son "removidos" (cambiados a vacío), lo cual puede hacer accesibles nuevos rollos. Calcular el total de rollos eliminados hasta que no quede ninguno accesible.

## Arquitectura y Diseño

La solución sigue los principios de **Clean Architecture**, modelando el problema como una simulación de estados inmutables en una cuadrícula (Grid).

### Principios SOLID Aplicados

*   **Single Responsibility Principle (SRP)**:
    *   `Diagram`: Representa el estado inmutable de la cuadrícula. No contiene lógica de negocio compleja, solo acceso a datos y creación de nuevos estados (`withClearedCoordinates`).
    *   `DiagramAnalyzer`: Contiene puramente la lógica de reglas del dominio (qué vecinos contar, cuándo es accesible un rollo). No guarda estado.
    *   `Coordinate`: Record simple para transferencia de datos (DTO) de posición.

*   **Immutability Strategy**:
    *   La clase `Diagram` es inmutable. En la Parte 2, en lugar de modificar la matriz original (lo cual es propenso a errores en simulaciones paso a paso), el método `withClearedCoordinates` devuelve una *nueva instancia* del diagrama con los cambios aplicados. Esto facilita razonar sobre cada "paso" de la simulación de forma aislada.

### Decisiones Técnicas

*   **Separación de Estado y Comportamiento**:
    *   A diferencia de un enfoque orientado a objetos clásico donde la celda podría saber si es accesible, aquí se desacopla el estado (`Diagram`) del análisis (`DiagramAnalyzer`). Esto permite cambiar las reglas de accesibilidad (ej: "menos de 4 vecinos" vs "menos de 2") sin tocar la estructura de datos subyacente.

*   **Simulación Iterativa**:
    *   En la Parte 2, el bucle `while(true)` en el `Main` orquesta la simulación. En cada iteración:
        1.  El `Analyzer` determina qué eliminar basándose en el diagrama actual.
        2.  Se genera un nuevo diagrama sin esos elementos.
        3.  Se repite hasta convergencia (no más cambios).

### Estructura del Código

*   `model`:
    *   `Diagram`: La matriz de caracteres.
    *   `Coordinate`: Posicion (fila, columna).
    *   `DiagramAnalyzer`: Lógica de reglas de negocio.
*   `io`: Carga del archivo de texto a objetos `DiagramLine`.
*   `a` / `b`: Controladores de la simulación.
