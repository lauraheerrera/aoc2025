package software.ulpgc.aoc.day06.io;

import software.ulpgc.aoc.common.io.Deserializer;
import software.ulpgc.aoc.day06.model.Problem;
import software.ulpgc.aoc.day06.model.Worksheet;

import java.util.List;

public record TxtMathWorksheetLoader(List<String> lines, Deserializer<Problem> deserializer) implements ProblemLoader {
    public TxtMathWorksheetLoader(String content, Deserializer<Problem> deserializer) {
        this(content.lines().toList(), deserializer);
    }

    @Override
    public List<Problem> load() {
        return new Worksheet(lines).parse(deserializer);
    }
}
