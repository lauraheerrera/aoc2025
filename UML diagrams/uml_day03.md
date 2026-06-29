# Day 03

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram
    direction TB
    class TotalBatteryJoltageCalculator {
        <<record>>
    }
    class BatteryBankMaxJoltageCalculator {
    }
    class BatteryBank {
    }
    class Joltage {
        <<record>>
    }
    class Length {
        <<record>>
    }
TotalBatteryJoltageCalculator --> BatteryBankMaxJoltageCalculator
    BatteryBankMaxJoltageCalculator --> Length 
    BatteryBankMaxJoltageCalculator ..> BatteryBank
    BatteryBankMaxJoltageCalculator ..> Joltage
```
