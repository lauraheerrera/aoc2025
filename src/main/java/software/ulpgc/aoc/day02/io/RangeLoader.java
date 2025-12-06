package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;

import java.io.IOException;
import java.util.List;

public interface RangeLoader {
    List<IdRange> load() throws IOException;
}