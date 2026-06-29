# Day 09 (Parte B)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB

    class MovieTheaterInterface {
        <<interface>>
        +maxRectangleArea() long
    }

    class MovieTheater {
        <<record>>  
    }

    class Tile {
        <<record>>
    }

    MovieTheaterInterface <|.. MovieTheater
    MovieTheater --> Tile
```
