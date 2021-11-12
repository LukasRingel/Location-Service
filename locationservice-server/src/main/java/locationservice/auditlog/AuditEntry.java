package locationservice.auditlog;

import locationservice.geo.NetworkAddress;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "auditLog")
public record AuditEntry(@Id UUID id,
                         UUID license,
                         NetworkAddress address,
                         Instant timeStamp) {


    public static AuditEntry createNewEntry(UUID license, NetworkAddress inetAddress) {
        return new AuditEntry(generateNewId(), license, inetAddress, Instant.now());
    }

    public static AuditEntry exampleWithLicense(UUID license) {
        return new AuditEntry(null, license, null, null);
    }

    /**
     * Generates a new log id by using the UUID.randomUUID method
     *
     * @return - a new log id
     */
    private static UUID generateNewId() {
        return UUID.randomUUID();
    }

}
