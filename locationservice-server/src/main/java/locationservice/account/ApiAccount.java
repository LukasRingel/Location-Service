package locationservice.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "accounts")
public record ApiAccount(@Id UUID key,
                         String name,
                         Role role,
                         Instant activeUntil,
                         Instant activationDate) {

    public static ApiAccount create(String name, Role role, Instant activeUntil) {
        return new ApiAccount(generateNewKey(), name, role, activeUntil, Instant.now());
    }

    private static UUID generateNewKey() {
        return UUID.randomUUID();
    }

    public boolean isStillValid() {
        return Instant.now().isBefore(activeUntil);
    }
}