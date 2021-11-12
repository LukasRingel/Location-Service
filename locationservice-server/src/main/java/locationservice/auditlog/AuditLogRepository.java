package locationservice.auditlog;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditLogRepository extends ReactiveMongoRepository<AuditEntry, UUID> {

}
