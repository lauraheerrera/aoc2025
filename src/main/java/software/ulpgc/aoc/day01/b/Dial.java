package software.ulpgc.aoc.day01.b;

import software.ulpgc.aoc.day01.a.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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

    private int previousPosition(int index) {
        return orders.subList(0, index)
                .stream()
                .mapToInt(Order::step)
                .sum() + 50;
    }

    private int nextPosition(int index) {
        return previousPosition(index) + orders.get(index).step();
    }

    private int zerosCrossed(int index) {
        return nextPosition(index) > previousPosition(index) ?
                Math.floorDiv(nextPosition(index) , 100) - Math.floorDiv(previousPosition(index) , 100)
                : Math.floorDiv(previousPosition(index)  - 1, 100) - Math.floorDiv(nextPosition(index)  - 1, 100);
    }


    public int countTotalZeros() {
        return IntStream.range(0, orders.size())
                .map(this::zerosCrossed)
                .sum();
    }


}