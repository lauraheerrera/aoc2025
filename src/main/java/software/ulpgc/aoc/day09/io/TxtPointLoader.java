package software.ulpgc.aoc.day09.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day09.model.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TxtPointLoader implements PointLoader {
    private final File file;
    private final Deserializer<Point> deserializer;

    public TxtPointLoader(File file, Deserializer<Point> deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }

    @Override
    public List<Point> load() throws IOException {
        return Files.readAllLines(file.toPath()).stream()
            .map(deserializer::deserialize)
            .collect(Collectors.toList());
    }
}
