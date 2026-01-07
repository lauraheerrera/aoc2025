package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.day04.model.DiagramLine;

public class TxtDiagramDeserializer implements DiagramDeserializer{
    @Override
    public DiagramLine deserialize(String line) {
        return new DiagramLine(line);
    }
}
