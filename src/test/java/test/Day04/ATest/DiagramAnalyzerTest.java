package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiagramAnalyzerTest {

    private Diagram createDiagram(String... lines) {
        Tile[][] tiles = Arrays.stream(lines)
                .map(line -> line.chars().mapToObj(c -> Tile.from((char) c)).toArray(Tile[]::new))
                .toArray(Tile[][]::new);
        return Diagram.create(tiles);
    }

    @Test
    public void sum_rolls_single_target_no_neighbors() {
        Diagram diagram = createDiagram(
                "...",
                ".@.",
                "...");
        assertThat(diagram.rows()).isEqualTo(3);
        assertThat(diagram.cols()).isEqualTo(3);
        assertThat(diagram.isInBounds(new Coordinate(0, 0))).isTrue();
        assertThat(diagram.isInBounds(new Coordinate(3, 0))).isFalse();
        assertThat(diagram.get(new Coordinate(1, 1))).isEqualTo(Tile.ROLL);

        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(1);
    }

    @Test
    public void diagram_should_throw_out_of_bounds_exception() {
        Diagram diagram = createDiagram("...");
        assertThatThrownBy(() -> diagram.get(new Coordinate(0, 5)))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    public void sum_rolls_two_targets_neighbors() {
        Diagram diagram = createDiagram("@@");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(2);
    }

    @Test
    public void sum_rolls_four_targets_square() {
        Diagram diagram = createDiagram(
                "@@",
                "@@");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void sum_rolls_dense_targets_filter_some() {
        Diagram diagram = createDiagram(
                "@@@",
                "@.@",
                "@@@");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void sum_rolls_all_targets() {
        Diagram diagram = createDiagram(
                "@@@",
                "@@@",
                "@@@");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(4);
    }

    @Test
    public void sum_rolls_diagonal_neighbors() {
        Diagram diagram = createDiagram(
                "@..",
                ".@.",
                "..@");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(3);
    }

    @Test
    public void sum_rolls_complex_example() {
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
                "@.@.@@@.@.");
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram).value()).isEqualTo(13);
    }
}
