package locationservice.statistics.rest;

import locationservice.account.*;
import locationservice.statistics.ApplicationStatistics;
import locationservice.statistics.event.AbstractStatisticsCollectEvent;
import locationservice.statistics.event.AccountStatisticsCollectEvent;
import locationservice.statistics.event.ApplicationStatisticsCollectEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
@AllArgsConstructor
public class StatisticsRestControllerImplementation implements StatisticsRestController {

    private final Clock clock;
    private final AccountService accounts;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ResponseEntity<ApplicationStatistics> collectApplicationStatistics(String apiKey) throws AccountExpired, AccountInvalidPermissions {
        checkForValidAccountAndPermissions(apiKey);
        return collectStatistics(new ApplicationStatisticsCollectEvent(this));
    }

    @Override
    public ResponseEntity<ApplicationStatistics> collectAccountStatistics(String apiKey) throws AccountExpired, AccountInvalidPermissions {
        var apiAccount = checkForValidAccountAndPermissions(apiKey);
        return collectStatistics(new AccountStatisticsCollectEvent(this, apiAccount));
    }

    private ResponseEntity<ApplicationStatistics> collectStatistics(AbstractStatisticsCollectEvent collectEvent) {
        Instant startTime = clock.instant();
        eventPublisher.publishEvent(collectEvent);
        var neededTime = clock.instant().minusMillis(startTime.toEpochMilli()).toEpochMilli();
        return ResponseEntity.ok(ApplicationStatistics.create(collectEvent.collected(), neededTime));
    }

    private ApiAccount checkForValidAccountAndPermissions(String apiKey) throws AccountExpired, AccountInvalidPermissions {
        var account = accounts.findValidAccount(apiKey);
        if (account.role() != Role.ADMINISTRATOR) throw AccountInvalidPermissions.createForHasUserNeedAdmin();
        return account;
    }
}