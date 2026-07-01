# Day 04

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class DiagramAnalyzer {
    }
    class Diagram {
    }
    class Coordinate {
        <<record>>
    }
    class Tile {
        <<enumeration>>
    }
    class RollsCount {
        <<record>>
    }
    DiagramAnalyzer --> Diagram
Diagram ..> Coordinate
DiagramAnalyzer ..> RollsCount
Diagram --> Tile

```
