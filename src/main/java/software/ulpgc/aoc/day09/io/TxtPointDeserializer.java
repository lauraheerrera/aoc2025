package software.ulpgc.aoc.day09.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day09.model.Tile;

public class TxtPointDeserializer implements Deserializer<Tile> {
    @Override
    public Tile deserialize(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Line cannot be null or empty");
        }
        String[] parts = line.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid point format: " + line);
        }
        return new Tile(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()));
    }
}
