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
    <<interface>>
}

class BackTrackingRegionFitter{}

Farm --> Region
Farm --> RegionFitter
BackTrackingRegionFitter ..|> RegionFitter
Shape --> Point
RegionFitter ..> Shape
RegionFitter ..> Region
```
