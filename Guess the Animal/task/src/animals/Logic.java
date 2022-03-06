package animals;

public class Logic {
    public static int randomIntBetween(int start, int end) {
        return ((Long) Math.round(Math.random() * (end - 1))).intValue() + start;
    }
}