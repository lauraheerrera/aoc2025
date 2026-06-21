package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.day04.model.Tile;

import java.io.IOException;
import java.util.List;

public interface DiagramLoader {
    List<Tile[]> load() throws IOException;
}
