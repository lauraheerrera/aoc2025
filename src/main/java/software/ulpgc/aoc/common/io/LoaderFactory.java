package software.ulpgc.aoc.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class LoaderFactory {

    private static final String SECTION_SEPARATOR = "\r?\n\r?\n";
    private static final String LINE_SEPARATOR = "\r?\n";

    public static <T> TxtLoader<T> txt(File file, Function<String, T> deserializer) {
        return new TxtLoader<>(file, deserializer, false);
    }

    public static List<List<String>> sections(File file) throws IOException {
        String content = Files.readString(file.toPath());
        return Arrays.stream(content.split(SECTION_SEPARATOR))
                .map(section -> Arrays.stream(section.split(LINE_SEPARATOR))
                        .filter(line -> !line.isBlank())
                        .toList())
                .toList();
    }
}
