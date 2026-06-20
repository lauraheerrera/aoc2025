package software.ulpgc.aoc.day09.io;

import software.ulpgc.aoc.day09.model.Point;
import java.io.IOException;
import java.util.List;

public interface PointLoader {
    List<Point> load() throws IOException;
}
