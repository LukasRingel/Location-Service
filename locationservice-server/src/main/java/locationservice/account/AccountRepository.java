package locationservice.account;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends ReactiveMongoRepository<ApiAccount, UUID> {
}