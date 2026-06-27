# Day 12

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class Farm {
        <<record>>
    }
    class Region {
        <<record>>
    }
    class Shape {
        <<record>>
    }
    Farm --> Region
    Region --> Shape
```
