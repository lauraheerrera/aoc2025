package software.ulpgc.aoc.day12.model;

import java.util.List;

public record Farm(List<Region> regions) {
    public long countRegionsThatFit(List<Shape> shapes) {
        return regions.stream()
                .filter(r -> r.fits(shapes))
                .count();
    }
}
