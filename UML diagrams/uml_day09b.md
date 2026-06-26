# Day 09 (Parte B)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class MovieTheater {
        <<record>>
    }
    class Segment {
        <<record>>
    }
    class Point {
        <<record>>
    }
    MovieTheater --> Point
    MovieTheater --> Segment
    Segment --> Point
```
