package locationservice.geolocation;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import locationservice.account.ApiAccount;
import locationservice.geo.GeoLocation;
import locationservice.geolocation.event.GeoLocationServedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

@Service
@EnableAsync
public class GeoLocationService {

    private final String language;
    private final DatabaseReader databaseReader;
    private final ApplicationEventPublisher eventPublisher;

    public GeoLocationService(Properties properties, ApplicationEventPublisher eventPublisher) {
        var downloadUrl = properties.getProperty("locationservice.downloadUrl");
        this.databaseReader = GeoResolver.updateAndCreate(downloadUrl).databaseReader();
        this.language = properties.getProperty("locationservice.language", "en");
        this.eventPublisher = eventPublisher;
    }

    public GeoLocation resolveGeoLocation(InetAddress inetAddress) throws IOException, GeoIp2Exception {
        var response = databaseReader.city(inetAddress);
        return GeoLocation.fromRaw(
              inetAddress,
              response.getTraits().isLegitimateProxy(),
              response.getCountry().getNames().get(language),
              response.getContinent().getNames().get(language),
              response.getMostSpecificSubdivision().getNames().get(language),
              response.getCity().getNames().get(language),
              Integer.parseInt(response.getPostal().getCode())
        );
    }

    @Async
    public void triggerAsyncServedEvent(ApiAccount account, GeoLocation location) {
        eventPublisher.publishEvent(new GeoLocationServedEvent(this, account, location));
    }
}