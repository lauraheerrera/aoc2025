package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;
import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.io.*;
import java.util.ArrayList;
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
        List<IdRange<T>> ranges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ranges.addAll(deserializer.deserialize(line, idFactory));
            }
        }
        return ranges;
    }
}
