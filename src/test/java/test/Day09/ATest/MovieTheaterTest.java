package test.Day09.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.a.model.MovieTheater;
import software.ulpgc.aoc.day09.b.model.Segment;
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
    public void solve_example() {
        TxtPointDeserializer deserializer = new TxtPointDeserializer();
        List<Tile> points = Arrays.stream(example.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        long result = new MovieTheater(points).maxRectangleArea();
        assertThat(result).isEqualTo(50L);
    }

    @Test
    public void test_point_creation_and_area() {
        Tile p1 = new Tile(2, 5);
        Tile p2 = new Tile(9, 7);
        assertThat(p1.x()).isEqualTo(2);
        assertThat(p1.y()).isEqualTo(5);
        assertThat(p1.rectangleAreaWith(p2)).isEqualTo(24L);
    }

    @Test
    public void test_segment_properties() {
        Segment horizontal = new Segment(new Tile(1, 5), new Tile(10, 5));
        assertThat(horizontal.isHorizontal()).isTrue();
        assertThat(horizontal.isVertical()).isFalse();
        assertThat(horizontal.minX()).isEqualTo(1);
        assertThat(horizontal.maxX()).isEqualTo(10);
        assertThat(horizontal.minY()).isEqualTo(5);
        assertThat(horizontal.maxY()).isEqualTo(5);

        Segment vertical = new Segment(new Tile(3, 10), new Tile(3, 2));
        assertThat(vertical.isHorizontal()).isFalse();
        assertThat(vertical.isVertical()).isTrue();
        assertThat(vertical.minX()).isEqualTo(3);
        assertThat(vertical.maxX()).isEqualTo(3);
        assertThat(vertical.minY()).isEqualTo(2);
        assertThat(vertical.maxY()).isEqualTo(10);
    }

    @Test
    public void test_deserializer() {
        TxtPointDeserializer deserializer = new TxtPointDeserializer();
        Tile p = deserializer.deserialize("12,-34");
        assertThat(p.x()).isEqualTo(12);
        assertThat(p.y()).isEqualTo(-34);
    }

    @Test
    public void test_single_point_area() {
        MovieTheater theater = new MovieTheater(List.of(new Tile(0, 0)));
        assertThat(theater.maxRectangleArea()).isEqualTo(0L);
    }

    @Test
    public void test_two_points_same_coordinates() {
        MovieTheater theater = new MovieTheater(List.of(new Tile(1, 1), new Tile(1, 1)));
        assertThat(theater.maxRectangleArea()).isEqualTo(1L);
    }
}
