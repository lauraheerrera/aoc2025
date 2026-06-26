# Day 08

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class Playground {
        <<record>>
    }
    class Connection {
        <<record>>
    }
    class JunctionBox {
        <<record>>
    }
    class DisjointSet~T~ {
        <<record>>
    }
    Playground --> JunctionBox
    Playground --> Connection
    Playground --> DisjointSet
    Connection --> JunctionBox
```
