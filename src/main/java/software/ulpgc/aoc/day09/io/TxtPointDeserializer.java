package software.ulpgc.aoc.day09.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day09.model.Point;

public class TxtPointDeserializer implements Deserializer<Point> {
    @Override
    public Point deserialize(String line) {
        return new Point(
            Integer.parseInt(line.split(",")[0]),
            Integer.parseInt(line.split(",")[1])
        );
    }
}
