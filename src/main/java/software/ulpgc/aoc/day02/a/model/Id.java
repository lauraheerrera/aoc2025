package software.ulpgc.aoc.day02.a.model;

import software.ulpgc.aoc.day02.model.InvalidatableId;

public class Id implements InvalidatableId {

    private final long id;

    private Id(long id) {
        this.id = id;
    }

    public static Id create(long id) {
        return new Id(id);
    }

    @Override
    public boolean isInvalid() {
        return getDigitCount() % 2 == 0 && getLeftHalf() == getRightHalf();
    }

    public long getLeftHalf() {
        return id / getMiddleIndex();
    }

    public long getRightHalf() {
        return id % getMiddleIndex();
    }

    private int getMiddleIndex() {
        return (int) Math.pow(10, getDigitCount() / 2.0);
    }

    public long id() {
        return this.id;
    }

}
