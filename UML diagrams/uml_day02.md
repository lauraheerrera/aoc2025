# Day 02

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class GiftShop~T~ {
        <<record>>
    }
    class IdRange~T~ {
        <<record>>
    }
    class InvalidatableId {
        <<interface>>
    }
    class Id {
        <<record>>
    }
    GiftShop --> IdRange
    IdRange --> InvalidatableId
    Id ..|> InvalidatableId
```
