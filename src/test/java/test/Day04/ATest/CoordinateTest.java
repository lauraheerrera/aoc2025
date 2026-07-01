package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.Coordinate;

import static org.assertj.core.api.Assertions.assertThat;

public class CoordinateTest {

    @Test
    public void should_offset_coordinate_correctly() {

        // Given an initial coordinate
        Coordinate coordinate = new Coordinate(5, 5);

        // When applying an offset
        Coordinate offsetCoordinate = coordinate.offset(2, -3);

        // Then the new coordinate should reflect the offset
        assertThat(offsetCoordinate.row()).isEqualTo(7);
        assertThat(offsetCoordinate.col()).isEqualTo(2);
    }
}