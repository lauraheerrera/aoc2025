package software.ulpgc.aoc.day08.model;

public record Connection(JunctionBox first, JunctionBox second, long squaredDistance) implements Comparable<Connection> {
    public Connection(JunctionBox first, JunctionBox second) {
        this(first, second, first.squaredDistanceTo(second));
    }

    @Override
    public int compareTo(Connection other) {
        return Long.compare(this.squaredDistance, other.squaredDistance);
    }
}
