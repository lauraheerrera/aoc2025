# Day 12

```mermaid
%%{init: { 'themeVariables': { 'lineColor': '#FFFFFF' } } }%%
classDiagram

class Farm {
}

class Region {
}

class Shape {
}

class Point {

}

class RegionFitter {
}

Farm --> Region
Farm --> RegionFitter
Shape --> Point
RegionFitter ..> Shape
RegionFitter ..> Region
```
