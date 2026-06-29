package software.ulpgc.aoc.day07.model;

import java.util.List;

public class Manifold {
    private final List<Row> rows;

    private Manifold(List<Row> rows) {
        this.rows = rows;
    }

    public static Manifold from(List<String> lines) {
        return new Manifold(lines.stream().map(Row::from).toList());
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public int size() {
        return rows.size();
    }

    public Column findStartColumn() {
        return rows.get(0).findStartColumn();
    }

    public List<Row> rows() {
        return rows;
    }
}