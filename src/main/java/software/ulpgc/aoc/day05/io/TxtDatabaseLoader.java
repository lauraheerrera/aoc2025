package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TxtDatabaseLoader implements RangeLoader, IDLoader {
    private static final String SECTION_SEPARATOR = "\r?\n\r?\n";
    private static final String LINE_SEPARATOR = "\r?\n";

    private final String content;
    private final RangeDeserializer rangeDeserializer;
    private final IDDeserializer idDeserializer;

    public TxtDatabaseLoader(String content, RangeDeserializer rangeDeserializer, IDDeserializer idDeserializer) {
        this.content = content;
        this.rangeDeserializer = rangeDeserializer;
        this.idDeserializer = idDeserializer;
    }

    public static TxtDatabaseLoader fromFile(String path, RangeDeserializer rangeDeserializer,
            IDDeserializer idDeserializer) throws IOException {
        return new TxtDatabaseLoader(Files.readString(Path.of(path)), rangeDeserializer, idDeserializer);
    }

    @Override
    public List<Range> loadRanges() {
        List<Range> ranges = new ArrayList<>();
        String[] sections = content.split(SECTION_SEPARATOR);
        if (sections.length > 0) {
            for (String line : sections[0].split(LINE_SEPARATOR)) {
                if (!line.isBlank()) {
                    ranges.add(rangeDeserializer.deserialize(line));
                }
            }
        }
        return ranges;
    }

    @Override
    public List<ID> loadIds() {
        List<ID> ids = new ArrayList<>();
        String[] sections = content.split(SECTION_SEPARATOR);
        if (sections.length > 1) {
            for (String line : sections[1].split(LINE_SEPARATOR)) {
                if (!line.isBlank()) {
                    ids.add(idDeserializer.deserialize(line));
                }
            }
        }
        return ids;
    }
}
