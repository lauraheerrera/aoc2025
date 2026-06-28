package software.ulpgc.aoc.day12.model;

import java.util.List;

public class Farm {
    private final List<Region> regions;

    public static Farm of(List<Region> regions) {
        return new Farm(regions);
    }

    private Farm(List<Region> regions) {
        this.regions = regions;
    }

    public long countRegionsThatFit(List<Shape> shapes) {
        return regions.stream()
                .filter(r -> r.fits(shapes))
                .count();
    }
}
