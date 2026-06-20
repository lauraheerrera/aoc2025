package software.ulpgc.aoc.day07.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day07.model.Manifold;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TxtManifoldLoader implements ManifoldLoader {
    private final File file;
    private final Deserializer<Manifold> deserializer;

    public TxtManifoldLoader(File file, Deserializer<Manifold> deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }

    @Override
    public Manifold load() throws IOException {
        return deserializer.deserialize(String.join("\n", Files.readAllLines(file.toPath())));
    }
}
