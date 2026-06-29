package software.ulpgc.aoc.day01.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DialStatus {

    private final Dial dial;
    private final List<Order> orders;

    private DialStatus(Dial dial, List<Order> orders) {
        this.dial = dial;
        this.orders = List.copyOf(orders);
    }

    public static DialStatus initial(Dial dial) {
        return new DialStatus(dial, List.of());
    }

    public DialStatus execute(List<Order> newOrders) {
        List<Order> combined = new ArrayList<>(orders);
        combined.addAll(newOrders);
        return new DialStatus(dial, combined);
    }

    public int size() {
        return orders.size();
    }

    public int position() {
        return dial.normalize(dial.initial() + sum());
    }

    private int sum() {
        return orders.stream().mapToInt(Order::step).sum();
    }

    private int stepAt(int index) {
        return orders.get(index).step();
    }

    private int previous(int index) {
        return dial.normalize(dial.initial() + sumUntil(index));
    }

    private int sumUntil(int index) {
        return orders.subList(0, index).stream()
                .mapToInt(Order::step)
                .sum();
    }

    public int next(int index) {
        return dial.normalize(previous(index) + stepAt(index));
    }

    public int crossZeroAt(int index) {
        int position = previous(index);
        int step = stepAt(index);

        return (int) IntStream.rangeClosed(1, Math.abs(step))
                .map(i -> step > 0 ? position + i : position - i)
                .map(dial::normalize)
                .filter(p -> p == 0)
                .count();
    }

}