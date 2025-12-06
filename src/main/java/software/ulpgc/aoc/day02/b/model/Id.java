package software.ulpgc.aoc.day02.b.model;

import software.ulpgc.aoc.day02.model.InvalidatableId;

import java.util.stream.IntStream;

public record Id(long id) implements InvalidatableId {

    public static Id create(long id){
        return new Id(id);
    }

    @Override
    public boolean isInvalid() {
        return hasRepeatedSequence(idToString(), stringLength());
    }

    private String idToString() {
        return Long.toString(this.id);
    }

    private int stringLength() {
        return idToString().length();
    }

    private boolean hasRepeatedSequence(String s, int n) {
        return IntStream.rangeClosed(1, n / 2)
                .filter(len -> validLength(n, len))
                .anyMatch(len -> isPatternRepeated(s, len));
    }

    private static boolean validLength(int n, int len) {
        return n % len == 0;
    }

    private boolean isPatternRepeated(String s, int len) {
        String pattern = s.substring(0, len);
        return IntStream.iterate(len, i -> i + len)
                .limit(s.length() / len - 1)
                .allMatch(i -> s.substring(i, i + len).equals(pattern));
    }


}
