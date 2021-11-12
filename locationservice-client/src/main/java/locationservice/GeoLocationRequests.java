package locationservice;

import io.reactivex.rxjava3.core.Observable;
import locationservice.geo.GeoLocation;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GeoLocationRequests {

    @GET("locate/{address}")
    Observable<GeoLocation> locateAddress(@Path("address") String address);

}
