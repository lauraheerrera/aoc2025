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
        <<record>>
    }
    class Joltage {
        <<record>>
    }
    class Length {
        <<record>>
    }
    TotalBatteryJoltageCalculator --> BatteryBankMaxJoltageCalculator
    TotalBatteryJoltageCalculator --> Length
    TotalBatteryJoltageCalculator ..> BatteryBank
    TotalBatteryJoltageCalculator ..> Joltage
    BatteryBankMaxJoltageCalculator ..> BatteryBank
    BatteryBankMaxJoltageCalculator ..> Length
    BatteryBankMaxJoltageCalculator ..> Joltage
```
