package software.ulpgc.aoc.day04.model;

public record Coordinate(int row, int col) {
    public Coordinate offset(int rowOffset, int colOffset) {
        return new Coordinate(row + rowOffset, col + colOffset);
    }
}
