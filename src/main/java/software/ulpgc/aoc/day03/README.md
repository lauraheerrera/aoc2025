# Day 3: Lobby

## Descripción

El desafío consiste en encontrar la combinación de dígitos que maximice el voltaje producido por bancos de baterías. Cada banco está representado por una cadena de dígitos.
1.  **Parte 1**: Seleccionar exactamente **2** dígitos de cada banco, respetando su orden original, tal que el número resultante sea el mayor posible.
2.  **Parte 2**: Seleccionar exactamente **12** dígitos de cada banco bajo las mismas condiciones.

El resultado final es la suma de estos voltajes máximos calculados para cada banco.

## Arquitectura y Diseño

La solución mantiene la coherencia con los días anteriores, aplicando **Clean Architecture** y **SOLID**, destacando por su enfoque algorítmico recursivo.

### Principios SOLID Aplicados

*   **Single Responsibility Principle (SRP)**:
    *   `BatteryBank`: Representa un único banco de baterías y encapsula la lógica pura para extraer el número máximo dado una longitud.
    *   `BatteryBankMaxJoltageCalculator`: Orquesta el cálculo sobre una colección de bancos y agrega los resultados. No conoce los detalles de cómo se extraen los dígitos.
    *   `TxtBatteryBankDeserializer`: Se limita a convertir líneas de texto en objetos del dominio.

*   **Open/Closed Principle (OCP)**:
    *   La clase `BatteryBankMaxJoltageCalculator` está diseñada para ser cerrada a modificaciones. El cambio de requisito de "2 dígitos" a "12 dígitos" se maneja mediante inyección de configuración en el constructor (`length`), sin alterar una sola línea de lógica interna.

### Decisiones Técnicas

*   **Algoritmo Codicioso (Greedy) y Recursivo**:
    *   Para construir el número más grande de longitud `N`, el algoritmo selecciona el dígito más alto posible en el rango que permite completar los `N-1` dígitos restantes, y luego repite el proceso recursivamente.
    *   Esta estrategia es más eficiente que generar todas las combinaciones posibles, especialmente cuando la longitud requerida crece (como en la Parte 2).
    
*   **Programación Funcional**:
    *   Se utiliza `IntStream` en el método `findMaxIndex` para buscar índices de manera declarativa, favoreciendo la legibilidad sobre bucles `for` anidados propensos a errores de límites.

*   **Reutilización de Código**:
    *   Tanto la **Parte A** como la **Parte B** utilizan exactamente las mismas clases del modelo. La única diferencia reside en la configuración inicial instanciada en el `Main` (`create(2)` vs `create(12)`).

### Estructura del Código

*   `model`: Contiene la lógica central (`BatteryBank`) y el servicio de cálculo (`BatteryBankMaxJoltageCalculator`).
*   `io`: Capa de persistencia y deserialización.
*   `a` / `b`: Ejecutables para cada parte del problema, que configuran y lanzan el proceso de cálculo.
