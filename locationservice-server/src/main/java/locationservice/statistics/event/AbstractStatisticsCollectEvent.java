package locationservice.statistics.event;

import locationservice.statistics.KeyDoublePair;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractStatisticsCollectEvent extends ApplicationEvent {
    private final Collection<KeyDoublePair> collected = new ArrayList<>();
    public AbstractStatisticsCollectEvent(Object source) {
        super(source);
    }
    public void collect(String key, double value) {
        collected.add(KeyDoublePair.create(key, value));
    }

    public Collection<KeyDoublePair> collected() {
        return collected;
    }
}
