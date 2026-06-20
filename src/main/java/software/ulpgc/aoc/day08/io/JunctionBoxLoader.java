package software.ulpgc.aoc.day08.io;

import software.ulpgc.aoc.day08.model.JunctionBox;
import java.io.IOException;
import java.util.List;

public interface JunctionBoxLoader {
    List<JunctionBox> load() throws IOException;
}
