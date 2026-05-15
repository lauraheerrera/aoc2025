package software.ulpgc.aoc.day05.model;

import java.util.List;

public class FreshnessValidator {
    private final List<Range> validRanges;

    private FreshnessValidator(List<Range> validRanges) {
        this.validRanges = validRanges;
    }

    public static FreshnessValidator fromRanges(List<Range> validRanges) {
        return new FreshnessValidator(validRanges);
    }

    public int countFresh(List<ID> ids) {
        return (int) ids.stream()
                .filter(id -> validRanges.stream()
                        .anyMatch(r -> r.contains(id.value())))
                .count();
    }

    public long countTotalFresh() {
        return mergedRanges().stream()
                .mapToLong(Range::length)
                .sum();
    }

    private List<Range> mergedRanges() {
        return validRanges.stream()
                .sorted()
                .collect(java.util.ArrayList::new, this::accumulate, java.util.List::addAll);
    }

    private void accumulate(List<Range> list, Range range) {
        if (!list.isEmpty() && list.getLast().mergeableWith(range)) {
            list.add(list.removeLast().merge(range));
        } else {
            list.add(range);
        }
    }
}
