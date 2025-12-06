package software.ulpgc.aoc.day02.io;

import software.ulpgc.aoc.day02.model.IdRange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtRangeLoader implements RangeLoader {
    private final File file;
    private final RangeDeserializer deserializer;

    public TxtRangeLoader(File file, RangeDeserializer deserializer) {
        this.file = file;
        this.deserializer = deserializer;
    }


    @Override
    public List<IdRange> load() throws IOException {
        List<IdRange> ranges = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ranges.addAll(deserializer.deserialize(line));
            }
        }
        return ranges;
    }

}
