package software.ulpgc.aoc.common.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TxtLoader<T> {

    private final File file;
    private final Function<String, T> deserializer;
    private final boolean skipHeader;

    public TxtLoader(File file, Function<String, T> deserializer, boolean skipHeader) {
        this.file = file;
        this.deserializer = deserializer;
        this.skipHeader = skipHeader;
    }

    public List<T> load() throws IOException {
        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            if (skipHeader) reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                result.add(deserializer.apply(line));
            }
        }
        return result;
    }
}
