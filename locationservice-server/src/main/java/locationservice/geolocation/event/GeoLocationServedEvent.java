package locationservice.geolocation.event;

import locationservice.account.ApiAccount;
import locationservice.geo.GeoLocation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GeoLocationServedEvent extends ApplicationEvent {

    private final ApiAccount account;
    private final GeoLocation location;

    public GeoLocationServedEvent(Object source, ApiAccount account, GeoLocation location) {
        super(source);
        this.account = account;
        this.location = location;
    }

}
