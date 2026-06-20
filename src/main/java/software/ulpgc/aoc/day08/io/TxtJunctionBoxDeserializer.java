package software.ulpgc.aoc.day08.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;

public class TxtJunctionBoxDeserializer implements Deserializer<JunctionBox> {
    @Override
    public JunctionBox deserialize(String line) {
        return new JunctionBox(
            Integer.parseInt(line.split(",")[0]),
            Integer.parseInt(line.split(",")[1]),
            Integer.parseInt(line.split(",")[2])
        );
    }
}
