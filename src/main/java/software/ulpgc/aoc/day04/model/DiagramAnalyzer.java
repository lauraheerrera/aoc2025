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

    public RollsCount sumAllAccessibleRolls(Diagram diagram) {
        return new RollsCount(findAccessibleCoordinates(diagram).size());
    }

    public List<Coordinate> findAccessibleCoordinates(Diagram diagram) {
        return diagram.coordinates()
                .filter(c -> isAccessible(diagram, c))
                .toList();
    }

    public RollsCount totalAccessibleRollsClearCycle(Diagram diagram) {
        return clearCycle(diagram, new RollsCount(0));
    }

    private RollsCount clearCycle(Diagram diagram, RollsCount accumulated) {
        List<Coordinate> toRemove = findAccessibleCoordinates(diagram);
        return toRemove.isEmpty()
                ? accumulated
                : clearCycle(diagram.withClearedCoordinates(toRemove), new RollsCount(accumulated.value() + toRemove.size()));
    }

    private boolean isAccessible(Diagram diagram, Coordinate coordinate) {
        return diagram.isInBounds(coordinate)
                && isTarget(diagram, coordinate)
                && countAdjacentTargets(diagram, coordinate) < MAX_ADJACENT;
    }

    private boolean isTarget(Diagram diagram, Coordinate coordinate) {
        return diagram.get(coordinate) == Tile.ROLL;
    }

    private int countAdjacentTargets(Diagram diagram, Coordinate coordinate) {
        return (int) Arrays.stream(Direction.values())
                .filter(dir -> isTargetAtOffset(diagram, coordinate, dir))
                .count();
    }

    private boolean isTargetAtOffset(Diagram diagram, Coordinate coordinate, Direction dir) {
        Coordinate offsetCoord = coordinate.offset(dir.rowOffset, dir.colOffset);
        return diagram.isInBounds(offsetCoord) && isTarget(diagram, offsetCoord);
    }
}