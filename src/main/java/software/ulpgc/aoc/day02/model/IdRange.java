package software.ulpgc.aoc.day02.model;

import java.util.ArrayList;
import java.util.List;

public class IdRange {
    private final List<Id> ids;

    public IdRange(long start, long end) {
        this.ids = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            ids.add(Id.create(i));
        }
    }

    public long sumInvalidIDs() {
        return ids.stream()
                .filter(id -> !id.isValid())
                .mapToLong(Id::id)
                .sum();
    }

}

