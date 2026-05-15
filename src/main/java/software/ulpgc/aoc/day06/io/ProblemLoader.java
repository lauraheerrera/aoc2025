package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.day06.model.Problem;
import java.io.IOException;
import java.util.List;

public interface ProblemLoader {
    List<Problem> load() throws IOException;
}
