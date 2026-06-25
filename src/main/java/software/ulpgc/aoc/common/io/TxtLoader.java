package software.ulpgc.aoc.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

public record TxtLoader<T>(File file, Function<String, T> deserializer, boolean skipHeader) {

    public List<T> load() throws IOException {
        try (var lines = Files.lines(file.toPath())) {
            return lines.skip(skipHeader ? 1 : 0)
                    .map(deserializer)
                    .toList();
        }
    }
}
