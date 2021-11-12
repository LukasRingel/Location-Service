package locationservice.account;

import java.time.Instant;
import java.util.UUID;

public record Account(
  UUID licenseKey,
  String name,
  Instant expireTime,
  Instant createTime
) { }