package software.ulpgc.aoc.day07.model;

import java.util.List;

public class Grid {
    private final List<Row> rows;

    private Grid(List<Row> rows) {
        this.rows = rows;
    }

    public static Grid from(List<String> lines) {
        return new Grid(lines.stream().map(Row::from).toList());
    }

    public List<Row> rows() {
        return rows;
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public int size() {
        return rows.size();
    }
}
