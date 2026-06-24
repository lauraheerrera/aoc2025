package software.ulpgc.aoc.day08.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;

public class TxtJunctionBoxDeserializer implements Deserializer<JunctionBox> {
    @Override
    public JunctionBox deserialize(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Line cannot be null or empty");
        }
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid junction box format: " + line);
        }
        return new JunctionBox(
                Integer.parseInt(parts[0].trim()),
                Integer.parseInt(parts[1].trim()),
                Integer.parseInt(parts[2].trim()));
    }
}
