# Day 09 (Parte A)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class MovieTheater {
        <<record>>
        +List~Point~ redTiles
        +maxRectangleArea() long
    }
    class Point {
        <<record>>
        +int x
        +int y
        +rectangleAreaWith(Point other) long
    }
    MovieTheater --> Point
```
