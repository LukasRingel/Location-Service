package locationservice.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    @PostConstruct
    public void printAccounts() {
        repository.findAll().collectList().subscribe(apiAccounts ->
              log.info("Found " + apiAccounts.size() + " accounts in our database: " +
                    apiAccounts.stream().map(account -> account.name() + "(" +
                          account.key().toString() + ")").collect(Collectors.joining(", "))
              )
        );
    }

    public ApiAccount findValidAccount(String apiKey) throws AccountExpired {
        if (apiKey == null) throw AccountExpired.withMissingKey();
        var account = repository.findById(UUID.fromString(apiKey)).block();
        if (account == null || !account.isStillValid()) {
            throw AccountExpired.withKey(apiKey);
        }
        return account;
    }
}