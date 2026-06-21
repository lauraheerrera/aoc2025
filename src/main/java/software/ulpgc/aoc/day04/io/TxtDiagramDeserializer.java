package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day04.model.Tile;

public class TxtDiagramDeserializer implements Deserializer<Tile[]> {
    @Override
    public Tile[] deserialize(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Line cannot be null");
        }
        return line.chars().mapToObj(c -> Tile.from((char) c)).toArray(Tile[]::new);
    }
}
