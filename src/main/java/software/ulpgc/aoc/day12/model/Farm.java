package software.ulpgc.aoc.day12.model;

import java.util.List;

public class Farm {
    private final List<Region> regions;
    private final RegionFitter fitter;

    public Farm(List<Region> regions, RegionFitter fitter) {
        this.regions = regions;
        this.fitter = fitter;
    }

    public long count(List<Shape> shapes) {
        return regions.stream()
                .filter(r -> fitter.canFit(r, shapes))
                .count();
    }
}