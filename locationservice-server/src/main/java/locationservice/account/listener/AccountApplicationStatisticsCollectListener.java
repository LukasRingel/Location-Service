package locationservice.account.listener;

import locationservice.account.AccountService;
import locationservice.account.ApiAccount;
import locationservice.account.Role;
import locationservice.statistics.event.ApplicationStatisticsCollectEvent;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class AccountApplicationStatisticsCollectListener implements ApplicationListener<ApplicationStatisticsCollectEvent> {

    private final AccountService accounts;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStatisticsCollectEvent collectEvent) {
        List<ApiAccount> apiAccounts = accounts.getRepository().findAll().collectList().block();
        assert apiAccounts != null;
        collectEvent.collect(
              "accounts_count_global",
              apiAccounts.size()
        );
        for (var role : Role.values()) {
            collectEvent.collect(
                  "accounts_count_role_" + role.name().toLowerCase(),
                  countByFilter(apiAccounts, apiAccount -> apiAccount.role() == role)
            );
        }
        collectEvent.collect(
              "accounts_count_active",
              countByFilter(apiAccounts, ApiAccount::isStillValid)
        );
        collectEvent.collect(
              "accounts_count_expired",
              countByFilter(apiAccounts, apiAccount -> !apiAccount.isStillValid())
        );
    }

    private long countByFilter(Collection<ApiAccount> apiAccounts, Predicate<ApiAccount> predicate) {
        return apiAccounts.stream().filter(predicate).count();
    }
}