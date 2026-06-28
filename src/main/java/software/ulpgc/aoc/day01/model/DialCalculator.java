package software.ulpgc.aoc.day01.model;

import java.util.List;
import java.util.stream.IntStream;

public class DialCalculator {
    private static final int INITIAL_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    private final List<Order> orders;

    private DialCalculator(List<Order> orders) {
        this.orders = List.copyOf(orders);
    }

    public static DialCalculator of(DialStatus status) {
        return new DialCalculator(status.orders());
    }

    public int countEndingInZero() {
        return (int) IntStream.rangeClosed(1, orders.size())
                .parallel()
                .map(this::calculatePartialSum)
                .filter(s -> s == 0)
                .count();
    }

    public int countCrossingZero() {
        return IntStream.range(0, orders.size())
                .map(this::calculateZerosCrossed)
                .sum();
    }

    private int calculatePartialSum(int size) {
        return normalize(calculateSum(orders.subList(0, size)));
    }

    private int calculateSum(List<Order> orderList) {
        return orderList.stream().mapToInt(Order::step).sum() + INITIAL_POSITION;
    }

    private int normalize(int value) {
        return ((value % DIAL_SIZE) + DIAL_SIZE) % DIAL_SIZE;
    }

    private int previousPosition(int index) {
        return calculateSum(orders.subList(0, index));
    }

    private int nextPosition(int index) {
        return previousPosition(index) + orders.get(index).step();
    }

    private int calculateZerosCrossed(int index) {
        return nextPosition(index) > previousPosition(index)
                ? Math.floorDiv(nextPosition(index), 100) - Math.floorDiv(previousPosition(index), 100)
                : Math.floorDiv(previousPosition(index) - 1, 100) - Math.floorDiv(nextPosition(index) - 1, 100);
    }
}
