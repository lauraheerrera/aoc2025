package software.ulpgc.aoc.day05.model;

public record Range(ID start, ID end) implements Comparable<Range> {
    public static final Range Null = new Range(new ID(-1), new ID(-1));

    public boolean contains(ID id) {
        return id.compareTo(start) >= 0 && id.compareTo(end) <= 0;
    }

    public long length() {
        return end.value() - start.value() + 1;
    }

    public boolean mergeableWith(Range other) {
        return this.end.value() >= other.start.value() - 1;
    }

    public Range merge(Range other) {
        return new Range(
                new ID(Math.min(this.start.value(), other.start.value())),
                new ID(Math.max(this.end.value(), other.end.value()))
        );
    }

    @Override
    public int compareTo(Range other) {
        return this.start.compareTo(other.start);
    }
}
