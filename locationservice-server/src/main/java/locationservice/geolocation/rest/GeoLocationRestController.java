package locationservice.geolocation.rest;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import locationservice.account.AccountExpired;
import locationservice.geo.GeoLocation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public interface GeoLocationRestController {

    @GetMapping("locate/{address}")
    ResponseEntity<GeoLocation> locateAddress(@PathVariable String address, @RequestHeader("X-API-KEY") String apiKey) throws IOException, GeoIp2Exception, AccountExpired;

}
