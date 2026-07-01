package test.Day09.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.a.model.MovieTheater;
import software.ulpgc.aoc.day09.model.Tile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieTheaterTest {

    private static final String example = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
            """;

    @Test
    public void should_solve_example_max_rectangle_area() {

        // Given a list of coordinates in text format
        String input = example;

        // And a deserializer that converts text into tiles
        TxtPointDeserializer deserializer = new TxtPointDeserializer();

        // And a list of points parsed from input
        List<Tile> points = Arrays.stream(input.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        // When computing the maximum rectangle area
        long result = MovieTheater.from(points).maxRectangleArea();

        // Then the result should match expected value
        assertThat(result).isEqualTo(50L);
    }

    @Test
    public void should_create_tile_and_compute_distance_correctly() {

        // Given two tiles
        Tile p1 = new Tile(2, 5);
        Tile p2 = new Tile(9, 7);

        // Then coordinates are stored correctly
        assertThat(p1.x()).isEqualTo(2);
        assertThat(p1.y()).isEqualTo(5);

        // And rectangle area between them is correct
        assertThat(p1.rectangleAreaWith(p2)).isEqualTo(24L);
    }

    @Test
    public void should_deserialize_negative_coordinates_correctly() {

        // Given a deserializer
        TxtPointDeserializer deserializer = new TxtPointDeserializer();

        // When parsing a negative coordinate
        Tile p = deserializer.deserialize("12,-34");

        // Then values are parsed correctly
        assertThat(p.x()).isEqualTo(12);
        assertThat(p.y()).isEqualTo(-34);
    }

    @Test
    public void should_return_zero_area_for_single_point() {

        // Given a single point theater
        MovieTheater theater = MovieTheater.from(List.of(new Tile(0, 0)));

        // When computing max rectangle area
        long result = theater.maxRectangleArea();

        // Then result should be zero
        assertThat(result).isEqualTo(0L);
    }

    @Test
    public void should_handle_duplicate_points() {

        // Given a theater with duplicate coordinates
        MovieTheater theater = MovieTheater.from(
                List.of(new Tile(1, 1), new Tile(1, 1))
        );

        // When computing max rectangle area
        long result = theater.maxRectangleArea();

        // Then area should be minimal rectangle
        assertThat(result).isEqualTo(1L);
    }
}