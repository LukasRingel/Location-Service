package locationservice;

import retrofit2.Retrofit;

public class LocationServiceClient {

    private final AccountRequests accountRequests;
    private final GeoLocationRequests geoLocationRequests;

    public LocationServiceClient(Retrofit retrofit) {
        this.accountRequests = retrofit.create(AccountRequests.class);
        this.geoLocationRequests = retrofit.create(GeoLocationRequests.class);
    }

    public AccountRequests accountRequests() {
        return accountRequests;
    }

    public GeoLocationRequests locationRequests() {
        return geoLocationRequests;
    }
}