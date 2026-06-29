package test.Day04.BTest;

import org.junit.Test;
import software.ulpgc.aoc.day04.model.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiagramAnalyzerTest {

        private Diagram createDiagram(String... lines) {
                Tile[][] tiles = Arrays.stream(lines)
                                .map(line -> line.chars()
                                                .mapToObj(c -> Tile.from((char) c))
                                                .toArray(Tile[]::new))
                                .toArray(Tile[][]::new);
                return Diagram.create(tiles);
        }

        @Test
        public void should_clear_coordinates_correctly() {
                Diagram diagram = createDiagram(
                                "@@",
                                "@@");

                List<Coordinate> toClear = List.of(new Coordinate(0, 0));

                Diagram cleared = diagram.withClearedCoordinates(toClear);

                assertThat(cleared.get(new Coordinate(0, 0))).isEqualTo(Tile.CLEARED);
                assertThat(cleared.get(new Coordinate(0, 1))).isEqualTo(Tile.ROLL);
                assertThat(cleared.get(new Coordinate(1, 0))).isEqualTo(Tile.ROLL);
                assertThat(cleared.get(new Coordinate(1, 1))).isEqualTo(Tile.ROLL);

                assertThat(diagram.get(new Coordinate(0, 0))).isEqualTo(Tile.ROLL);
        }

        @Test
        public void should_correctly_solve_part_b_example() {
                String[] lines = {
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
                };

                Diagram diagram = createDiagram(lines);
                DiagramAnalyzer analyzer = new DiagramAnalyzer();

                List<Coordinate> step1 = analyzer.findAccessibleCoordinates(diagram);
                assertThat(step1).hasSize(13);

                Diagram current = diagram.withClearedCoordinates(step1);

                List<Coordinate> step2 = analyzer.findAccessibleCoordinates(current);
                assertThat(step2).hasSize(12);
                current = current.withClearedCoordinates(step2);

                List<Coordinate> step3 = analyzer.findAccessibleCoordinates(current);
                assertThat(step3).hasSize(7);
                current = current.withClearedCoordinates(step3);

                List<Coordinate> step4 = analyzer.findAccessibleCoordinates(current);
                assertThat(step4).hasSize(5);
                current = current.withClearedCoordinates(step4);

                int totalRemoved = 13 + 12 + 7 + 5;

                while (true) {
                        List<Coordinate> toRemove = analyzer.findAccessibleCoordinates(current);

                        if (toRemove.isEmpty()) {
                                break;
                        }

                        totalRemoved += toRemove.size();
                        current = current.withClearedCoordinates(toRemove);
                }

                assertThat(totalRemoved).isEqualTo(43);

                Diagram initialDiagram = createDiagram(lines);
                assertThat(analyzer.totalAccessibleRollsClearCycle(initialDiagram).value())
                                .isEqualTo(43);
        }
}