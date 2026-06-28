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

    class DialCalculator {
    }

    DialStatus --> Dial
    DialStatus --> Order
    DialCalculator ..> DialStatus
    DialCalculator --> Order
```
