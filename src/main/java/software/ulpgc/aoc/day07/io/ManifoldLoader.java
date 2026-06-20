package software.ulpgc.aoc.day07.io;

import software.ulpgc.aoc.day07.model.Manifold;
import java.io.IOException;

public interface ManifoldLoader {
    Manifold load() throws IOException;
}
