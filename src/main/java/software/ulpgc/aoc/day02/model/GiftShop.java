package software.ulpgc.aoc.day02.model;

import java.util.List;

public record GiftShop<T extends InvalidatableId>(List<IdRange<T>> ranges) {
    public long sumAllInvalidIds() {
        return ranges.stream()
                .mapToLong(IdRange::sumInvalidIDs)
                .sum();
    }
}
