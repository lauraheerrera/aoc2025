package software.ulpgc.aoc.day02.model;

import java.util.List;

public class GiftShop {

    private final List<IdRange> ranges;

    public GiftShop(List<IdRange> ranges) {
        this.ranges = ranges;
    }

    public long sumAllInvalidIds() {
        return ranges.stream()
                .mapToLong(IdRange::sumInvalidIDs)
                .sum();
    }
}