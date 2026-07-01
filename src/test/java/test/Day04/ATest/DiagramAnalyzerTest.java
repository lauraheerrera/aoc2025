package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiagramAnalyzerTest {

    private Diagram createDiagram(String... lines) {

        // Given raw string representation of a diagram
        Tile[][] tiles = Arrays.stream(lines)
                .map(line -> line.chars()
                        .mapToObj(c -> Tile.from((char) c))
                        .toArray(Tile[]::new))
                .toArray(Tile[][]::new);

        // When building a diagram object
        return Diagram.create(tiles);
    }

    @Test
    public void should_analyze_single_roll_with_no_neighbors() {

        // Given a simple diagram
        Diagram diagram = createDiagram(
                "...",
                ".@.",
                "..."
        );

        // Then diagram dimensions should be correct
        assertThat(diagram.rows()).isEqualTo(3);
        assertThat(diagram.cols()).isEqualTo(3);

        // And bounds checking should work
        assertThat(diagram.isInBounds(new Coordinate(0, 0))).isTrue();
        assertThat(diagram.isInBounds(new Coordinate(3, 0))).isFalse();

        // And tile retrieval should work
        assertThat(diagram.get(new Coordinate(1, 1))).isEqualTo(Tile.ROLL);

        // And analyzer should count accessible rolls correctly
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(1);
    }

    @Test
    public void should_throw_exception_when_coordinates_out_of_bounds() {

        // Given a small diagram
        Diagram diagram = createDiagram("...");

        // Then accessing invalid position should fail
        assertThatThrownBy(() -> diagram.get(new Coordinate(0, 5)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void should_count_rolls_in_simple_neighbor_cases() {

        // Given adjacent targets
        Diagram diagram = createDiagram("@@");

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        // Then all accessible rolls should be counted
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(2);
    }

    @Test
    public void should_count_rolls_in_square_grid() {

        // Given a 2x2 full grid
        Diagram diagram = createDiagram(
                "@@",
                "@@"
        );

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void should_filter_center_blocked_cases() {

        // Given a grid with a blocked center
        Diagram diagram = createDiagram(
                "@@@",
                "@.@",
                "@@@"
        );

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void should_handle_full_grid_case() {

        // Given a full grid
        Diagram diagram = createDiagram(
                "@@@",
                "@@@",
                "@@@"
        );

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void should_count_diagonal_connections() {

        // Given diagonal pattern
        Diagram diagram = createDiagram(
                "@..",
                ".@.",
                "..@"
        );

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(3);
    }

    @Test
    public void should_handle_complex_diagram() {

        // Given complex input
        Diagram diagram = createDiagram(
                "..@@.@@@@.",
                "@@@.@.@.@@",
                "@@@@@.@.@@",
                "@.@@@@..@.",
                "@@.@@@@.@@",
                ".@@@@@@@.@",
                ".@.@.@.@@@",
                "@.@@@.@@@@",
                ".@@@@@@@@.",
                "@.@.@@@.@."
        );

        DiagramAnalyzer analyzer = new DiagramAnalyzer();

        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(13);
    }
}