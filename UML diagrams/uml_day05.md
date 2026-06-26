# Day 05

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class FreshnessValidator {
        <<record>>
    }
    class Range {
        <<record>>
    }
    class ID {
        <<record>>
    }
    FreshnessValidator --> Range
    FreshnessValidator --> ID
```
