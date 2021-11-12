package locationservice.account;

import java.util.Objects;

public final class AccountExpired extends Exception {
  public static AccountExpired withMissingKey() {
    return new AccountExpired("");
  }

  public static AccountExpired withKey(String key) {
    Objects.requireNonNull(key, "key");
    return new AccountExpired(key);
  }

  private final String key;

  private AccountExpired(String key) {
    super("the account is expired");
    this.key = key;
  }

  public String key() {
    return key;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }

  @Override
  public StackTraceElement[] getStackTrace() {
    return new StackTraceElement[0];
  }

  @Override
  public String toString() {
    return "AccountExpired(key=%s)".formatted(key);
  }
}