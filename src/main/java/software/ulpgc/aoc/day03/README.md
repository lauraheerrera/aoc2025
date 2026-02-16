# Day 3: Lobby

## Descripción

El desafío consiste en encontrar la combinación de dígitos que maximice el voltaje producido por bancos de baterías. Cada banco está representado por una cadena de dígitos.
1.  **Parte 1**: Seleccionar exactamente **2** dígitos de cada banco para maximizar el valor.
2.  **Parte 2**: Seleccionar exactamente **12** dígitos.

## Arquitectura y Diseño

La solución implementa un algoritmo **Greedy (Voraz) y Recursivo** encapsulado bajo una arquitectura orientada a objetos limpia.

### Fundamentos de Diseño
*   **Código Expresivo**: La implementación utiliza recursividad con métodos nombrados semánticamente (`selectDigitAndRecurse`), lo que hace explícita la naturaleza "paso a paso" y la intención del algoritmo, facilitando su lectura frente a alternativas iterativas complejas.
*   **Alta Cohesión**: `BatteryBank` es una clase autocontenida que encapsula toda la lógica matemática necesaria para calcular su voltaje óptimo.

### Principios de Diseño
*   **Principio de Responsabilidad Única (SRP)**:
    *   *Definición*: Separación de preocupaciones.
    *   *Implementación*: Se distingue claramente entre el cálculo individual (`BatteryBank`) y la orquestación global (`BatteryBankMaxJoltageCalculator`). Esto permite verificar la corrección del algoritmo de maximización independientemente de la lógica de suma total.
*   **Principio Abierto/Cerrado (OCP)**:
    *   *Definición*: Extender comportamiento sin modificar código fuente.
    *   *Implementación*: La clase `BatteryBankMaxJoltageCalculator` está diseñada para ser configurable mediante el parámetro `length` en su constructor. Esto permite reutilizar la misma clase compilada para satisfacer los requisitos de la Parte 1 (longitud 2) y la Parte 2 (longitud 12) sin realizar cambios en el código.

### Patrones de Diseño
*   **Patrón Factory Method**:
    *   *Implementación*: `BatteryBank.create()` centraliza la instanciación, permitiendo validaciones previas o cambios futuros en la estrategia de creación sin impactar a los consumidores de la clase.
*   **Patrón Iterator**:
    *   *Implementación*: El uso de `IntStream` dentro de `findMaxIndex` abstrae la iteración sobre los caracteres del banco, delegando el control del flujo a la API funcional de Java.

### Decisiones Técnicas

*   **Algoritmo Codicioso (Greedy)**: Se opta por una estrategia que selecciona el óptimo local en cada paso recursivo. Esto ofrece una solución eficiente al problema de maximización, evitando la complejidad exponencial de evaluar todas las combinaciones posibles.
*   **Reutilización**: La arquitectura comparte el 100% del código del modelo entre ambas partes del problema, variando únicamente la configuración inicial.

## Pruebas Realizadas

Los tests en `BatteryTest` aseguran que la lógica recursiva y la orquestación funcionan correctamente:

*   **Algoritmo Voraz (Max Joltage)**:
    *   Pruebas unitarias sobre `BatteryBank` con cadenas de dígitos simples y complejas para verificar que el algoritmo selecciona siempre la subsecuencia máxima correcta (ej. de "987654321111111" con longitud 2 extrae "98").
*   **Orquestación de Sumas**:
    *   Verificación de `BatteryBankMaxJoltageCalculator` para asegurar que suma correctamente los máximos de múltiples bancos.
    *   Pruebas con listas incrementales de bancos para confirmar que la agregación de resultados es precisa.
*   **Consistencia**: Validación de que el algoritmo escala correctamente para diferentes longitudes de selección, confirmando la robustez del diseño OCP.
