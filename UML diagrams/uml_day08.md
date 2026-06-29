# Day 08

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB

    class Playground {
        <<record>>
    }

    class ConnectionGenerator

    class Connection {
        <<record>>
    }

    class JunctionBox {
        <<record>>
    }

    class DisjointSet~T~ {
        <<record>>
    }

    Playground --> ConnectionGenerator
    Playground --> DisjointSet~JunctionBox~

    ConnectionGenerator --> Connection
    Connection --> JunctionBox

```