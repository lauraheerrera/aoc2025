package software.ulpgc.aoc.day04.model;

public record DiagramLine(String line ){
    public char[] toCharArray() {
        return line.toCharArray();
    }
}
