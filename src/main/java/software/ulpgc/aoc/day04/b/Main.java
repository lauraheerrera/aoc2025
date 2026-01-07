package software.ulpgc.aoc.day04.b;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.day04.io.DiagramDeserializer;
import software.ulpgc.aoc.day04.io.DiagramLoader;
import software.ulpgc.aoc.day04.io.TxtDiagramDeserializer;
import software.ulpgc.aoc.day04.model.Coordinate;
import software.ulpgc.aoc.day04.model.Diagram;
import software.ulpgc.aoc.day04.model.DiagramAnalyzer;
import software.ulpgc.aoc.day04.model.DiagramLine;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day04/input.txt");

        DiagramDeserializer deserializer = new TxtDiagramDeserializer();
        DiagramLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();

        List<DiagramLine> diagramLines = loader.load();

        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        Diagram diagram = Diagram.create(diagramLines);

        int totalRemoved = 0;
        while (true) {
            List<Coordinate> toRemove = analyzer.findAccessibleCoordinates(diagram);
            if (toRemove.isEmpty())
                break;

            totalRemoved += toRemove.size();
            diagram = diagram.withClearedCoordinates(toRemove);
        }

        System.out.println("Número de rollos a los que se pueden acceder: " + totalRemoved);
    }
}
