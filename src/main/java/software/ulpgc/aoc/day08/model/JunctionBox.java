package software.ulpgc.aoc.day08.model;

public record JunctionBox(int x, int y, int z) {
    public long squaredDistanceTo(JunctionBox other) {
        return squared(x - other.x) + squared(y - other.y) + squared(z - other.z);
    }

    private long squared(long val) {
        return val * val;
    }
}
