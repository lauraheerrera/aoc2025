package software.ulpgc.aoc.day04.model;

import java.util.Arrays;
import java.util.List;

public class DiagramAnalyzer {
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

    public RollsCount sumAllAccessibleRolls(DiagramStatus status) {
        return new RollsCount(findAccessibleCoordinates(status).size());
    }

    public List<Coordinate> findAccessibleCoordinates(DiagramStatus status) {
        return status.coordinates()
                .filter(c -> isAccessible(status, c))
                .toList();
    }

    public RollsCount totalAccessibleRollsClearCycle(DiagramStatus status) {
        return clearCycle(status, new RollsCount(0));
    }

    private RollsCount clearCycle(DiagramStatus status, RollsCount accumulated) {
        List<Coordinate> toRemove = findAccessibleCoordinates(status);
        return toRemove.isEmpty()
                ? accumulated
                : clearCycle(status.withClearedCoordinates(toRemove),
                        new RollsCount(accumulated.value() + toRemove.size()));
    }

    private boolean isAccessible(DiagramStatus status, Coordinate coordinate) {
        return status.isInBounds(coordinate)
                && isTarget(status, coordinate)
                && countAdjacentTargets(status, coordinate) < MAX_ADJACENT;
    }

    private boolean isTarget(DiagramStatus status, Coordinate coordinate) {
        return status.get(coordinate) == Tile.ROLL;
    }

    private int countAdjacentTargets(DiagramStatus status, Coordinate coordinate) {
        return (int) Arrays.stream(Direction.values())
                .filter(dir -> isTargetAtOffset(status, coordinate, dir))
                .count();
    }

    private boolean isTargetAtOffset(DiagramStatus status, Coordinate coordinate, Direction dir) {
        Coordinate offsetCoord = coordinate.offset(dir.rowOffset, dir.colOffset);
        return status.isInBounds(offsetCoord) && isTarget(status, offsetCoord);
    }
}