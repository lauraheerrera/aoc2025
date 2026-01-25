package software.ulpgc.aoc.day04.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiagramAnalyzer {
    private static final char TARGET = '@';
    private static final int MAX_ADJACENT = 4;

    private enum Direction {
        NORTH_WEST(-1, -1), NORTH(-1, 0), NORTH_EAST(-1, 1),
        WEST(0, -1), EAST(0, 1),
        SOUTH_WEST(1, -1), SOUTH(1, 0), SOUTH_EAST(1, 1);

        final int rowOffset;
        final int colOffset;

        Direction(int rowOffset, int colOffset) {
            this.rowOffset = rowOffset;
            this.colOffset = colOffset;
        }
    }

    public int sumAllAccessibleRolls(Diagram diagram) {
        return findAccessibleCoordinates(diagram).size();
    }

    public List<Coordinate> findAccessibleCoordinates(Diagram diagram) {
        List<Coordinate> accessible = new ArrayList<>();
        for (int row = 0; row < diagram.rows(); row++) {
            for (int col = 0; col < diagram.cols(); col++) {
                if (isAccessible(diagram, row, col)) {
                    accessible.add(new Coordinate(row, col));
                }
            }
        }
        return accessible;
    }

    private boolean isAccessible(Diagram diagram, int row, int col) {
        if (!diagram.isInBounds(row, col))
            return false;
        return isTarget(diagram, row, col) &&
                countAdjacentTargets(diagram, row, col) < MAX_ADJACENT;
    }

    private boolean isTarget(Diagram diagram, int row, int col) {
        return diagram.get(row, col) == TARGET;
    }

    private int countAdjacentTargets(Diagram diagram, int row, int col) {
        return (int) Arrays.stream(Direction.values())
                .filter(dir -> isTargetAtOffset(diagram, row, col, dir))
                .count();
    }

    private boolean isTargetAtOffset(Diagram diagram, int row, int col, Direction dir) {
        int nr = row + dir.rowOffset;
        int nc = col + dir.colOffset;
        return diagram.isInBounds(nr, nc) && isTarget(diagram, nr, nc);
    }
}