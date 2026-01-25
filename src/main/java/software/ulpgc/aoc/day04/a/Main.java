package software.ulpgc.aoc.day04.a;

import software.ulpgc.aoc.common.io.LoaderFactory;
import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day04.io.DiagramLoader;
import software.ulpgc.aoc.day04.io.TxtDiagramDeserializer;
import software.ulpgc.aoc.day04.model.Diagram;
import software.ulpgc.aoc.day04.model.DiagramAnalyzer;
import software.ulpgc.aoc.day04.model.DiagramLine;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources/day04/input.txt");

        Deserializer<DiagramLine> deserializer = new TxtDiagramDeserializer();
        DiagramLoader loader = () -> LoaderFactory
                .txt(file, deserializer::deserialize)
                .load();

        List<DiagramLine> diagramLines = loader.load();

        DiagramAnalyzer analyzer = new DiagramAnalyzer();
        Diagram diagram = Diagram.create(diagramLines);
        System.out.println("Número de rollos a los que se pueden acceder: " + analyzer.sumAllAccessibleRolls(diagram));
    }
}
