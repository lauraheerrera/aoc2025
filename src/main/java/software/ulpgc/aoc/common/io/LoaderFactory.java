package software.ulpgc.aoc.common.io;

import java.io.File;
import java.util.function.Function;

public class LoaderFactory {
    public static <T> TxtLoader<T> txt(File file, Function<String, T> deserializer) {
        return new TxtLoader<>(file, deserializer, false);
    }
}
