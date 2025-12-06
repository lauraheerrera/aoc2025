package software.ulpgc.aoc.day02.model;

public record Id(long id) {

    public static Id create(long id){
        return new Id(id);
    }

    public long id(){
        return this.id;
    }

    public int getDigitCount() {
        return String.valueOf(Math.abs(id)).length();
    }

    public boolean isValid() {
        return getDigitCount() % 2 != 0 || getLeftHalf() != getRightHalf();
    }

    public long getLeftHalf() {
        return id() / getMiddleIndex();
    }

    public long getRightHalf() {
        return id() % getMiddleIndex();
    }

    private int getMiddleIndex() {
        return (int) Math.pow(10, (double) getDigitCount() /2);
    }

}
