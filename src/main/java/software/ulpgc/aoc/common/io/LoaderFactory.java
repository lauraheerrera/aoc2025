package software.ulpgc.aoc.common.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class LoaderFactory {

    public static <T> TxtLoader<T> txt(File file, Function<String, T> deserializer) {
        return new TxtLoader<>(file, deserializer, false);
    }

    public static List<List<String>> sections(File file) throws IOException {
        String content = Files.readString(file.toPath()).replace("\r", "");
        return Arrays.stream(content.split("\n\n"))
                .map(section -> Arrays.stream(section.split("\n"))
                        .filter(line -> !line.isBlank())
                        .toList())
                .filter(list -> !list.isEmpty())
                .toList();
    }

    public static <T> List<T> load(List<String> lines, Deserializer<T> deserializer) {
        return lines.stream().map(deserializer::deserialize).toList();
    }
}
