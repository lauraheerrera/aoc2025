package software.ulpgc.aoc.day05.io;

import java.io.IOException;
import java.util.List;

import software.ulpgc.aoc.day05.model.Range;

public interface RangeLoader {
    List<Range> loadRanges() throws IOException;
}
