package locationservice.geo;

public record NetworkAddress(String address, boolean isProxy) {
  public static NetworkAddress of(String address) {
    return new NetworkAddress(address, false);
  }

  public static NetworkAddress proxyOf(String address) {
    return new NetworkAddress(address, true);
  }

  public NetworkAddress asProxy() {
    return new NetworkAddress(address, true);
  }
}