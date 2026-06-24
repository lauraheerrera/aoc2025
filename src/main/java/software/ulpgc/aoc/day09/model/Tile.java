package software.ulpgc.aoc.day09.model;

public record Tile(int x, int y) {
    public long rectangleAreaWith(Tile other) {
        return (long) (Math.abs(x - other.x) + 1) * (Math.abs(y - other.y) + 1);
    }
}
