package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;

public class TxtRangeLoader<T extends InvalidatableId> implements RangeLoader<T> {
    private final File file;
    private final RangeDeserializer<T> deserializer;
    private final LongFunction<T> idFactory;

    public TxtRangeLoader(File file, RangeDeserializer<T> deserializer, LongFunction<T> idFactory) {
        this.file = file;
        this.deserializer = deserializer;
        this.idFactory = idFactory;
    }

    @Override
    public List<IdRange<T>> load() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.readLine();
            if (content == null || content.isBlank()) return List.of();

            return Arrays.stream(content.split(","))
                    .map(String::trim)
                    .map(range -> deserializer.deserialize(range, idFactory))
                    .toList();
        }
    }
}