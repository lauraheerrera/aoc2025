package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Problem;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.util.List;

public class TxtMathWorksheetLoader implements ProblemLoader {
    private final List<String> lines;
    private final Deserializer<Problem> deserializer;

    public TxtMathWorksheetLoader(String content, Deserializer<Problem> deserializer) {
        this.lines = content.lines().toList();
        this.deserializer = deserializer;
    }

    @Override
    public List<Problem> load() {
        return new Worksheet(lines).parse(deserializer);
    }
}
