package software.ulpgc.aoc.day10.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day10.model.Machine;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class TxtMachineLoader implements MachineLoader {
    private final File file;
    private final Deserializer<Machine> deserializer;

    public TxtMachineLoader(File file, Deserializer<Machine> deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }

    @Override
    public List<Machine> load() throws IOException {
        return Files.readAllLines(file.toPath()).stream()
            .map(deserializer::deserialize)
            .collect(Collectors.toList());
    }
}
