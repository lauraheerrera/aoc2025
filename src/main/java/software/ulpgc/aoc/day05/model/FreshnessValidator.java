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

    private boolean isFresh(ID id) {
        return validRanges.stream()
                .anyMatch(range -> range.contains(id.value()));
    }

    public int countFresh(List<ID> ids) {
        return (int) ids.stream()
                .filter(this::isFresh)
                .count();
    }

    private List<Range> sortedRanges() {
        return validRanges.stream()
                .sorted((a, b) -> Long.compare(a.start(), b.start()))
                .toList();
    }

    public long countTotalFresh() {
        long total = 0, end = -1;

        for (Range r : sortedRanges()) {
            if (r.start() > end + 1) {
                total += Math.max(0, end + 1 - r.start() + r.length());
                end = r.end();
            } else {
                end = Math.max(end, r.end());
            }
        }

        return total;
    }

}
