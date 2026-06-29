package software.ulpgc.aoc.day04.model;

import java.util.List;

public class DiagramAnalyzer {
    private static final int MAX_ADJACENT = 4;

    public RollsCount sumAllAccessibleRolls(Diagram diagram) {
        return new RollsCount(findAccessibleCoordinates(diagram).size());
    }

    public List<Coordinate> findAccessibleCoordinates(Diagram diagram) {
        List<Coordinate> coords = diagram.coordinates();
        return coords.stream()
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
                : clearCycle(diagram.withClearedCoordinates(toRemove),
                        new RollsCount(accumulated.value() + toRemove.size()));
    }

    private boolean isAccessible(Diagram diagram, Coordinate coordinate) {
        return diagram.isInBounds(coordinate)
                && isTarget(diagram, coordinate)
                && diagram.countAdjacent(coordinate, Tile.ROLL) < MAX_ADJACENT;
    }

    private boolean isTarget(Diagram diagram, Coordinate coordinate) {
        return diagram.get(coordinate) == Tile.ROLL;
    }

}
