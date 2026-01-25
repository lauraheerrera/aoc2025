package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.day04.model.DiagramLine;
import software.ulpgc.aoc.common.io.Deserializer;

public class TxtDiagramDeserializer implements Deserializer<DiagramLine> {
    @Override
    public DiagramLine deserialize(String line) {
        return new DiagramLine(line);
    }
}
