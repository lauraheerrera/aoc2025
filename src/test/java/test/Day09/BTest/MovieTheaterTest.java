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
    public void solve_example() {
        TxtPointDeserializer deserializer = new TxtPointDeserializer();
        List<Tile> points = Arrays.stream(example.split("\n"))
                .map(deserializer::deserialize)
                .collect(Collectors.toList());

        long result = new MovieTheater(points).maxRectangleArea();
        assertThat(result).isEqualTo(24L);
    }
}
