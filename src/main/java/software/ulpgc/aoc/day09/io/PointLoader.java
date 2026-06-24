package software.ulpgc.aoc.day09.io;

import software.ulpgc.aoc.day09.model.Tile;
import java.io.IOException;
import java.util.List;

public interface PointLoader {
    List<Tile> load() throws IOException;
}
