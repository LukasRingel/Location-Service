package locationservice.geolocation.rest;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import locationservice.account.AccountExpired;
import locationservice.account.AccountService;
import locationservice.auditlog.AuditLogService;
import locationservice.geo.GeoLocation;
import locationservice.geolocation.GeoLocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;

@Component
@AllArgsConstructor
public class GeoLocationRestControllerImplementation implements GeoLocationRestController {

    private final AuditLogService auditLog;
    private final GeoLocationService locations;
    private final AccountService accounts;

    @Override
    public ResponseEntity<GeoLocation> locateAddress(String address, String apiKey)
          throws IOException, GeoIp2Exception, AccountExpired {
        var account = accounts.findValidAccount(apiKey);
        var location = locations.resolveGeoLocation(InetAddress.getByName(address));
        locations.triggerAsyncServedEvent(account, location);
        return ResponseEntity.ok(location);
    }
}