package test.Day12.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day12.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmTest {

    private Shape shape(List<Point> points) {
        return new Shape(points);
    }

    RegionFitter fitter = new GreedyRegionFitter();

    @Test
    public void empty_farm_returns_zero() {
        // Given an empty farm with no regions
        Farm farm = new Farm(List.of(), fitter);

        // When we count how many regions can fit any shapes
        long result = farm.count(List.of());

        // Then the result should be zero
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void single_region_that_fits_shape() {
        // Given a region that can fit a single shape
        Region region = new Region(4, 4, List.of(1));
        Shape shape = shape(List.of(new Point(0, 0)));
        Farm farm = new Farm(List.of(region), fitter);

        // When we count the regions that can fit the given shape
        long result = farm.count(List.of(shape));

        // Then exactly one region should fit the shape
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void farm_counts_multiple_fitting_regions() {
        // Given two regions and a shape that fits both
        Region r1 = new Region(4, 4, List.of(1));
        Region r2 = new Region(2, 2, List.of(1));
        Shape shape = shape(List.of(new Point(0, 0)));
        Farm farm = new Farm(List.of(r1, r2), fitter);

        // When we count the regions that can fit the shape
        long result = farm.count(List.of(shape));

        // Then the farm should report both regions as fitting
        assertThat(result).isEqualTo(2);
    }
}