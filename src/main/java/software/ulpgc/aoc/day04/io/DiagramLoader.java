package software.ulpgc.aoc.day04.io;

import software.ulpgc.aoc.day04.model.DiagramLine;

import java.io.IOException;
import java.util.List;

public interface DiagramLoader {
    List<DiagramLine> load() throws IOException;
}
