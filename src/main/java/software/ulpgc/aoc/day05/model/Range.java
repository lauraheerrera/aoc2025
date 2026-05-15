package software.ulpgc.aoc.day05.model;

public record Range(long start, long end) implements Comparable<Range> {
    public static final Range Null = new Range(-1, -1);

    public boolean contains(long value) {
        return value >= start && value <= end;
    }

    public long length() {
        return end - start + 1;
    }

    public boolean mergeableWith(Range other) {
        return this.end >= other.start - 1;
    }

    public Range merge(Range other) {
        return new Range(Math.min(this.start, other.start), Math.max(this.end, other.end));
    }

    @Override
    public int compareTo(Range other) {
        return Long.compare(this.start, other.start);
    }
}
