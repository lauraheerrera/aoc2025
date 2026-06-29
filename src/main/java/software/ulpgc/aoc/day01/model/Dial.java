package software.ulpgc.aoc.day01.model;

public class Dial {

    private static final int INITIAL_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    private Dial() {
    }

    public static Dial create() {
        return new Dial();
    }

    public int normalize(int value) {
        return ((value % DIAL_SIZE) + DIAL_SIZE) % DIAL_SIZE;
    }

    public int initial() {
        return INITIAL_POSITION;
    }
}