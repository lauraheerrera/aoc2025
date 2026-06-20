package software.ulpgc.aoc.day07.model;

public record Column(int index) {
    public Column left() {
        return new Column(index - 1);
    }

    public Column right() {
        return new Column(index + 1);
    }
}
