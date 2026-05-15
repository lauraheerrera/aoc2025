package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Problem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TxtMathWorksheetLoader implements ProblemLoader {
    private final List<String> lines;
    private final Deserializer<Problem> deserializer;

    public TxtMathWorksheetLoader(String content, Deserializer<Problem> deserializer) {
        this.lines = List.of(content.split("\n"));
        this.deserializer = deserializer;
    }

    public TxtMathWorksheetLoader(File file, Deserializer<Problem> deserializer) throws IOException {
        this.lines = Files.readAllLines(file.toPath());
        this.deserializer = deserializer;
    }

    @Override
    public List<Problem> load() {
        return problemsFrom(lines);
    }

    private List<Problem> problemsFrom(List<String> lines) {
        return Pattern.compile("#+").matcher(contentMap(lines)).results()
                .map(mr -> extractBlock(lines, mr.start(), mr.end()))
                .map(deserializer::deserialize)
                .toList();
    }

    private String contentMap(List<String> lines) {
        return IntStream.range(0, maxWidth(lines))
                .mapToObj(x -> isColumnEmpty(lines, x) ? " " : "#")
                .collect(Collectors.joining());
    }

    private int maxWidth(List<String> lines) {
        return lines.stream().mapToInt(String::length).max().orElse(0);
    }

    private boolean isColumnEmpty(List<String> lines, int x) {
        return lines.stream().allMatch(l -> x >= l.length() || Character.isWhitespace(l.charAt(x)));
    }

    private String extractBlock(List<String> lines, int start, int end) {
        return lines.stream()
                .map(l -> l.substring(start, Math.min(end, l.length())))
                .collect(Collectors.joining("\n"));
    }
}
