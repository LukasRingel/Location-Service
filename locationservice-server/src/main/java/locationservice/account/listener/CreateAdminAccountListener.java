package locationservice.account.listener;

import locationservice.account.AccountRepository;
import locationservice.account.ApiAccount;
import locationservice.account.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.util.Collection;

@Component
@AllArgsConstructor
public class CreateAdminAccountListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Clock clock;
    private final AccountRepository repository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent refresh) {
        repository.findAll()
              .collectList()
              .subscribe(this::createAdminAccountIfNoneExists);
    }

    private static final Duration year = Duration.ofDays(365);

    private void createAdminAccountIfNoneExists(Collection<ApiAccount> existingAccounts) {
        if (existingAccounts.stream().noneMatch(ApiAccount::isStillValid)) {
            createAdminAccount();
        }
    }

    private void createAdminAccount() {
        var expireTime = clock.instant().plus(year);
        var account = ApiAccount.create("system-default", Role.ADMINISTRATOR, expireTime);
        repository.insert(account).subscribe();
    }
}