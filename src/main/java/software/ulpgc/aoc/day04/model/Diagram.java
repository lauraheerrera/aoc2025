package software.ulpgc.aoc.day04.model;

import java.util.List;

public class Diagram {
    private final char[][] grid;
    private final int rows;
    private final int cols;

    private Diagram(char[][] grid, int rows, int cols) {
        this.grid = grid;
        this.rows = rows;
        this.cols = cols;
    }

    public static Diagram create(List<DiagramLine> lines) {
        if (lines == null || lines.isEmpty()) {
            return new Diagram(new char[0][0], 0, 0);
        }

        char[][] grid = lines.stream()
                .map(DiagramLine::toCharArray)
                .toArray(char[][]::new);

        return new Diagram(grid, grid.length, grid[0].length);
    }

    public char get(int row, int col) {
        if (!isInBounds(row, col))
            throw new IndexOutOfBoundsException();
        return grid[row][col];
    }

    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < grid[row].length;
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public Diagram withClearedCoordinates(List<Coordinate> coordinates) {
        char[][] newGrid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, cols);
        }

        for (Coordinate coord : coordinates) {
            if (isInBounds(coord.row(), coord.col())) {
                newGrid[coord.row()][coord.col()] = 'x';
            }
        }
        return new Diagram(newGrid, rows, cols);
    }
}