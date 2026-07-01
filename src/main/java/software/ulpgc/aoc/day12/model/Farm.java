package software.ulpgc.aoc.day12.model;

import java.util.List;

public record Farm(List<Region> regions, RegionFitter fitter) {

    public long count(List<Shape> shapes) {
        return regions.stream()
                .filter(r -> fitter.canFit(r, shapes))
                .count();
    }
}