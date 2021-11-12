package locationservice.statistics;

public record KeyDoublePair(
      String key,
      double value
) {

    public boolean is(double target) {
        return value == target;
    }

    public static KeyDoublePair create(String key, double value) {
        return new KeyDoublePair(key, value);
    }
}