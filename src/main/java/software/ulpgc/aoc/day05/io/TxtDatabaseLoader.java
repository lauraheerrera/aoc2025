package software.ulpgc.aoc.day05.io;

import software.ulpgc.aoc.day05.model.ID;
import software.ulpgc.aoc.day05.model.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public record TxtDatabaseLoader(
        String content,
        RangeDeserializer rangeDeserializer,
        IDDeserializer idDeserializer
) implements RangeLoader, IDLoader {

    private static final String SECTION_SEPARATOR = "\r?\n\r?\n";
    private static final String LINE_SEPARATOR = "\r?\n";

    public static TxtDatabaseLoader fromFile(String path, RangeDeserializer rangeDeserializer,
            IDDeserializer idDeserializer) throws IOException {
        return new TxtDatabaseLoader(Files.readString(Path.of(path)), rangeDeserializer, idDeserializer);
    }

    @Override
    public List<Range> loadRanges() {
        return Arrays.stream(content.split(SECTION_SEPARATOR))
                .findFirst()
                .stream()
                .flatMap(section -> Arrays.stream(section.split(LINE_SEPARATOR)))
                .filter(line -> !line.isBlank())
                .map(rangeDeserializer::deserialize)
                .toList();
    }

    @Override
    public List<ID> loadIds() {
        return Arrays.stream(content.split(SECTION_SEPARATOR))
                .skip(1)
                .findFirst()
                .stream()
                .flatMap(section -> Arrays.stream(section.split(LINE_SEPARATOR)))
                .filter(line -> !line.isBlank())
                .map(idDeserializer::deserialize)
                .toList();
    }
}
