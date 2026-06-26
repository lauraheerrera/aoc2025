# Day 07 - I/O
 
```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class ManifoldLoader {
        <<interface>>
    }
    class Deserializer~T~ {
        <<interface>>
    }
    class TxtManifoldLoader {
    }
    class TxtManifoldDeserializer {
    }
    class Manifold {
        <<record>>
    }
 
    ManifoldLoader <|.. TxtManifoldLoader
    Deserializer <|.. TxtManifoldDeserializer
 
    TxtManifoldLoader --> Deserializer~Manifold~
    TxtManifoldLoader ..> Manifold
    TxtManifoldDeserializer ..> Manifold
```
