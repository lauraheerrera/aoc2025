package test.Day12.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day12.model.Farm;
import software.ulpgc.aoc.day12.model.Region;
import software.ulpgc.aoc.day12.model.Shape;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmTest {

    @Test
    public void should_validate_if_region_fits_shapes() {
        List<Shape> shapes = List.of(
                new Shape(0, 5),
                new Shape(1, 6));

        Region regionFits = new Region(4, 4, List.of(2, 1));
        assertThat(regionFits.fits(shapes)).isTrue();

        Region regionDoesNotFit = new Region(4, 4, List.of(2, 2));
        assertThat(regionDoesNotFit.fits(shapes)).isFalse();
    }

    @Test
    public void should_count_regions_that_fit_in_farm() {
        List<Shape> shapes = List.of(
                new Shape(0, 5),
                new Shape(1, 6));

        List<Region> regions = List.of(
                new Region(4, 4, List.of(2, 1)),
                new Region(4, 4, List.of(2, 2)),
                new Region(10, 10, List.of(5, 5)));

        Farm farm = new Farm(regions);
        assertThat(farm.countRegionsThatFit(shapes)).isEqualTo(2);
    }
}
