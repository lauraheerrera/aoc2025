package test.Day04.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.Coordinate;
import software.ulpgc.aoc.day04.model.Diagram;
import software.ulpgc.aoc.day04.model.DiagramAnalyzer;
import software.ulpgc.aoc.day04.model.DiagramLine;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiagramAnalyzerTest {

    @Test
    public void should_clear_coordinates_correctly() {
        Diagram diagram = Diagram.create(List.of(
                new DiagramLine("@@"),
                new DiagramLine("@@")));

        List<Coordinate> toClear = List.of(new Coordinate(0, 0));
        Diagram newDiagram = diagram.withClearedCoordinates(toClear);

        assertThat(newDiagram.get(0, 0)).isEqualTo('x');
        assertThat(newDiagram.get(0, 1)).isEqualTo('@');
        assertThat(newDiagram.get(1, 0)).isEqualTo('@');
        assertThat(newDiagram.get(1, 1)).isEqualTo('@');
        assertThat(diagram.get(0, 0)).isEqualTo('@');
    }

    @Test
    public void should_correctly_solve_part_b_example() {
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

        List<Coordinate> step1 = analyzer.findAccessibleCoordinates(diagram);
        assertThat(step1).hasSize(13);
        diagram = diagram.withClearedCoordinates(step1);
        List<Coordinate> step2 = analyzer.findAccessibleCoordinates(diagram);
        assertThat(step2).hasSize(12);
        diagram = diagram.withClearedCoordinates(step2);
        List<Coordinate> step3 = analyzer.findAccessibleCoordinates(diagram);
        assertThat(step3).hasSize(7);
        diagram = diagram.withClearedCoordinates(step3);
        List<Coordinate> step4 = analyzer.findAccessibleCoordinates(diagram);
        assertThat(step4).hasSize(5);
        diagram = diagram.withClearedCoordinates(step4);

        int totalRemoved = 13 + 12 + 7 + 5;
        while (true) {
            List<Coordinate> toRemove = analyzer.findAccessibleCoordinates(diagram);
            if (toRemove.isEmpty())
                break;
            totalRemoved += toRemove.size();
            diagram = diagram.withClearedCoordinates(toRemove);
        }

        assertThat(totalRemoved).isEqualTo(43);
    }
}
