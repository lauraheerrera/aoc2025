package software.ulpgc.aoc.day07.model;

import java.util.List;

public record Grid(List<Row> rows) {
    public static Grid from(List<String> lines) {
        return new Grid(lines.stream().map(Row::from).toList());
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public int size() {
        return rows.size();
    }
}
