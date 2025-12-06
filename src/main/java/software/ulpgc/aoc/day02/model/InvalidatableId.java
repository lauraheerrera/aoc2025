package software.ulpgc.aoc.day02.model;

public interface InvalidatableId {
    long id();
    boolean isInvalid();

    default int getDigitCount() {
        return String.valueOf(Math.abs(id())).length();
    }

}
