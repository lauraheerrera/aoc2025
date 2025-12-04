package software.ulpgc.aoc.day01.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Dial {
    private final List<Order> orders;

    private Dial() {
        this.orders = new ArrayList<>();
    }

    public static Dial create() {
        return new Dial();
    }

    private int sumAll(List<Order> orders) {
        return orders.stream().mapToInt(Order::step).sum() + 50;
    }

    public Dial add(String... orders) {
        Arrays.stream(orders).
                map(this::parse).
                forEach(this::add);
        return this;
    }

    public void add(Order order) {
        orders.add(order);
    }

    private Order parse(String order) {
        return new Order(signOf(order) * valueOf(order));
    }

    private int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

    private int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }

    private int normalize(int value) {
        return ((value % 100) + 100) % 100;
    }

    public int position() {
        return normalize(sumAll(orders));
    }

    public Dial execute(String orders) {
        return add(orders.split("\n"));
    }

    public int count() {
        int pos = 50;
        int zeros = 0;

        for (Order order : orders) {
            pos = normalize(pos + order.step());
            if (pos == 0) zeros++;
        }

        return zeros;
    }

}