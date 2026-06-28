package software.ulpgc.aoc.day01.model;

import java.util.ArrayList;
import java.util.List;

public class DialStatus {
    private final Dial dial;
    private final List<Order> orders;
    private static final int INITIAL_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    private DialStatus(Dial dial, List<Order> orders) {
        this.dial = dial;
        this.orders = List.copyOf(orders);
    }

    public static DialStatus initial(Dial dial) {
        return new DialStatus(dial, List.of());
    }

    public DialStatus execute(List<Order> newOrders) {
        List<Order> combined = new ArrayList<>(this.orders);
        combined.addAll(newOrders);
        return new DialStatus(dial, combined);
    }

    public List<Order> orders() {
        return List.copyOf(orders);
    }

    public int position() {
        return normalize(calculateSum(orders));
    }

    private int calculateSum(List<Order> orderList) {
        return orderList.stream().mapToInt(Order::step).sum() + INITIAL_POSITION;
    }

    private int normalize(int value) {
        return ((value % DIAL_SIZE) + DIAL_SIZE) % DIAL_SIZE;
    }
}
