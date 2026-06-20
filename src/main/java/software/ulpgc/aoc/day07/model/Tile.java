package software.ulpgc.aoc.day07.model;

public enum Tile {
    EMPTY,
    SPLITTER,
    START;

    public static Tile from(char symbol) {
        return switch (symbol) {
            case '.' -> EMPTY;
            case '^' -> SPLITTER;
            case 'S' -> START;
            default -> throw new IllegalArgumentException("Unknown tile: " + symbol);
        };
    }

    public boolean isSplitter() {
        return this == SPLITTER;
    }

    public boolean isStart() {
        return this == START;
    }
}
