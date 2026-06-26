# Day 10 (Parte A)

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class Factory {
        <<record>>
    }
    class Machine {
        <<interface>>
    }
    class MachineImpl {
        <<record>>
    }
    class Button {
        <<record>>
    }
    Factory --> Machine
    MachineImpl ..|> Machine
    MachineImpl --> Button
```
