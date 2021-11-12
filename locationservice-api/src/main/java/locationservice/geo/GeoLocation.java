package locationservice.geo;

import java.net.InetAddress;
public record GeoLocation(
  NetworkAddress address,
  GeoCountry country,
  GeoRegion region,
  GeoCity city
) {
  public static GeoLocation fromRaw(
    InetAddress inetAddress,
    boolean isProxy,
    String countryName,
    String continent,
    String regionName,
    String cityName,
    int cityPostCode
  ) {
    return new GeoLocation(
      new NetworkAddress(inetAddress.getHostAddress(), isProxy),
      new GeoCountry(countryName, continent),
      new GeoRegion(regionName),
      new GeoCity(cityName, cityPostCode)
    );
  }
}