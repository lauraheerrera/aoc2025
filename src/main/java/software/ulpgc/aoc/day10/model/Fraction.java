package software.ulpgc.aoc.day10.model;

public record Fraction(long num, long den) {
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);

    public static Fraction of(long num, long den) {
        return ofReduced(
            den < 0 ? -num : num,
            den < 0 ? -den : den,
            gcd(Math.abs(num), Math.abs(den))
        );
    }

    private static Fraction ofReduced(long num, long den, long g) {
        return new Fraction(num / g, den / g);
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public Fraction add(Fraction o) {
        return Fraction.of(num * o.den + o.num * den, den * o.den);
    }

    public Fraction subtract(Fraction o) {
        return Fraction.of(num * o.den - o.num * den, den * o.den);
    }

    public Fraction multiply(Fraction o) {
        return Fraction.of(num * o.num, den * o.den);
    }

    public Fraction divide(Fraction o) {
        return Fraction.of(num * o.den, den * o.num);
    }

    public boolean isInteger() {
        return den == 1;
    }
}
