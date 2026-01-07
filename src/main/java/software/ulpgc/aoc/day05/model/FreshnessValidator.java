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

}
