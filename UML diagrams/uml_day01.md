# Day 01

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class Dial {
        <<record>>
    }

    class DialStatus {
        <<record>>
    }

    class Order {
        <<record>>
    }

    DialStatus --> Dial
    DialStatus --> Order
```
