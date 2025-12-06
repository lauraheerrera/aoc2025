package software.ulpgc.aoc.day02.model;

import java.util.List;

public class GiftShop<T extends InvalidatableId> {
    private final List<IdRange<T>> ranges;

    public GiftShop(List<IdRange<T>> ranges) {
        this.ranges = ranges;
    }

    public long sumAllInvalidIds() {
        return ranges.stream()
                .mapToLong(IdRange::sumInvalidIDs)
                .sum();
    }
}
