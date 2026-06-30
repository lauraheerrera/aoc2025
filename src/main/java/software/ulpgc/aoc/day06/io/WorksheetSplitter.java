package software.ulpgc.aoc.day06.io;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WorksheetSplitter {

    public static List<String> splitIntoBlocks(List<String> lines) {
        if (lines == null || lines.isEmpty() || lines.stream().allMatch(String::isBlank)) {
            throw new IllegalArgumentException("Worksheet lines cannot be null, empty or blank");
        }

        int maxWidth = maxWidth(lines);
        String contentMap = buildContentMap(lines, maxWidth);

        return Pattern.compile("#+").matcher(contentMap).results()
                .map(mr -> extractBlock(lines, mr.start(), mr.end()))
                .toList();
    }

    private static String buildContentMap(List<String> lines, int maxWidth) {
        return IntStream.range(0, maxWidth)
                .mapToObj(x -> isColumnEmpty(lines, x) ? " " : "#")
                .collect(Collectors.joining());
    }

    private static boolean isColumnEmpty(List<String> lines, int x) {
        return lines.stream().allMatch(line -> x >= line.length() || Character.isWhitespace(line.charAt(x)));
    }

    private static String extractBlock(List<String> lines, int start, int end) {
        return lines.stream()
                .map(line -> start >= line.length() ? "" : line.substring(start, Math.min(end, line.length())))
                .collect(Collectors.joining("\n"));
    }

    private static int maxWidth(List<String> lines) {
        return lines.stream().mapToInt(String::length).max().orElse(0);
    }
}