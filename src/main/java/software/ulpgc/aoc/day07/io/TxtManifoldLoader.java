package software.ulpgc.aoc.day07.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day07.model.Manifold;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public record TxtManifoldLoader(File file, Deserializer<Manifold> deserializer) implements ManifoldLoader {
    @Override
    public Manifold load() throws IOException {
        return deserializer.deserialize(Files.readString(file.toPath()));
    }
}
