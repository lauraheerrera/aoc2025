# Day 07 (Parte B)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    
    class Manifold
    class Row
    class Tile {
        <<enumeration>>
    }
    class Column {
        <<record>>
    }
    class Paths
    class PathCounter

    PathCounter --> Manifold
    Manifold --> Row
    Row --> Tile
    Row ..> Column
    
    PathCounter ..> Paths
```
