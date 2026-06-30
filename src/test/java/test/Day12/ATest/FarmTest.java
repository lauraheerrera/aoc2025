package test.Day12.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day12.model.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmTest {

    private Shape shape(List<Point> points) {
        return new Shape(points);
    }

    RegionFitter fitter = RegionFitter.create();

    @Test
    public void empty_farm_returns_zero() {
        Farm farm = new Farm(List.of(), fitter);
        long result = farm.count(List.of());
        assertThat(result).isEqualTo(0);
    }

    @Test
    public void single_region_that_fits_shape() {
        Region region = new Region(4, 4, List.of(1));
        Shape shape = shape(List.of(new Point(0, 0)));
        Farm farm = new Farm(List.of(region), fitter);
        long result = farm.count(List.of(shape));
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void farm_counts_multiple_fitting_regions() {
        Region r1 = new Region(4, 4, List.of(1));
        Region r2 = new Region(2, 2, List.of(1));
        Shape shape = shape(List.of(new Point(0, 0)));
        Farm farm = new Farm(List.of(r1, r2), fitter);
        long result = farm.count(List.of(shape));
        assertThat(result).isEqualTo(2);
    }
}