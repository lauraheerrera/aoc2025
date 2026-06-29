# Day 01

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB

    class Dial {
    }

    class DialStatus {
    }

    class DialCalculator {
    }

    class Order {
        <<record>>
    }

    DialStatus --> Dial
    DialStatus --> Order
    DialCalculator --> DialStatus
```