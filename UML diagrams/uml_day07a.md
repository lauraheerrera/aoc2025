# Day 07 (Parte A)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class Manifold {
        <<record>>
    }
    class Grid {
        <<record>>
    }
    class Row {
        <<record>>
    }
    class Tile {
        <<enumeration>>
    }
    class Column {
        <<record>>
    }
    Manifold --> Grid
    Grid --> Row
    Row --> Tile
    Row --> Column
```
