package test.Day04.ATest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.Diagram;
import software.ulpgc.aoc.day04.model.DiagramAnalyzer;
import software.ulpgc.aoc.day04.model.DiagramLine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiagramAnalyzerTest {

    @Test
    public void sum_rolls_single_target_no_neighbors() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("..."),
                new DiagramLine(".@."),
                new DiagramLine("...")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(1);
    }

    @Test
    public void sum_rolls_two_targets_neighbors() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(2);
    }

    @Test
    public void sum_rolls_four_targets_square() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@"),
                new DiagramLine("@@")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(4);
    }

    @Test
    public void sum_rolls_dense_targets_filter_some() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@@"),
                new DiagramLine("@.@"),
                new DiagramLine("@@@")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(4);
    }

    @Test
    public void sum_rolls_all_targets() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@@"),
                new DiagramLine("@@@"),
                new DiagramLine("@@@")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(4);
    }

    @Test
    public void sum_rolls_diagonal_neighbors() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@@"),
                new DiagramLine("@@@"),
                new DiagramLine("@@@")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(4);
    }

    @Test
    public void sum_rolls_complex_example() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("..@@.@@@@."),
                new DiagramLine("@@@.@.@.@@"),
                new DiagramLine("@@@@@.@.@@"),
                new DiagramLine("@.@@@@..@."),
                new DiagramLine("@@.@@@@.@@"),
                new DiagramLine(".@@@@@@@.@"),
                new DiagramLine(".@.@.@.@@@"),
                new DiagramLine("@.@@@.@@@@"),
                new DiagramLine(".@@@@@@@@."),
                new DiagramLine("@.@.@@@.@.")));
        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        assertThat(analyzer.sumAllAccessibleRolls(diagram)).isEqualTo(13);
    }
}
