package locationservice.statistics;

import java.util.Collection;

public record ApplicationStatistics(
      Collection<KeyDoublePair> statistics,
      long computingTimeInMillis
) {
    public static ApplicationStatistics create(Collection<KeyDoublePair> statistics, long computingTimeInMillis) {
        return new ApplicationStatistics(statistics, computingTimeInMillis);
    }
}