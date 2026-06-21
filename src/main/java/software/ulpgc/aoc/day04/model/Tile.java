package software.ulpgc.aoc.day04.model;

public enum Tile {
    ROLL('@'),
    EMPTY('.'),
    CLEARED('x');

    private final char symbol;

    Tile(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public static Tile from(char symbol) {
        return switch (symbol) {
            case '@' -> ROLL;
            case '.' -> EMPTY;
            case 'x' -> CLEARED;
            default -> throw new IllegalArgumentException("Unknown tile symbol: " + symbol);
        };
    }
}
