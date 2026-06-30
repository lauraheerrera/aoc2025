# Day 08

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB

    class JunctionBox {
        <<record>>
    }

    class Connection {
        <<record>>
    }

    class ConnectionGenerator {
    }

    class Playground {
    }

    class DisjointSet~T~ {
    }

    ConnectionGenerator --> Connection
    Connection --> JunctionBox

    Playground --> Connection
    Playground --> DisjointSet~JunctionBox~
```