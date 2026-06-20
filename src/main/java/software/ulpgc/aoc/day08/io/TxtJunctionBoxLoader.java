package software.ulpgc.aoc.day08.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day08.model.JunctionBox;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TxtJunctionBoxLoader implements JunctionBoxLoader {
    private final File file;
    private final Deserializer<JunctionBox> deserializer;

    public TxtJunctionBoxLoader(File file, Deserializer<JunctionBox> deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }

    @Override
    public List<JunctionBox> load() throws IOException {
        return Files.readAllLines(file.toPath()).stream()
            .map(deserializer::deserialize)
            .collect(Collectors.toList());
    }
}
