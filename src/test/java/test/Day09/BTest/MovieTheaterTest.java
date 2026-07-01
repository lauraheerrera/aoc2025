package test.Day09.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day09.io.TxtPointDeserializer;
import software.ulpgc.aoc.day09.b.model.MovieTheater;
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
    public void should_solve_movie_theater_example() {

        // Given a textual representation of points
        String input = example;

        // And a deserializer that converts text into tiles
        TxtPointDeserializer deserializer = new TxtPointDeserializer();

        // And a list of tiles parsed from input
        List<Tile> points = Arrays.stream(input.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        // When computing the maximum rectangle area
        long result = MovieTheater.from(points).maxRectangleArea();

        // Then the result should match expected value
        assertThat(result).isEqualTo(24L);
    }
}