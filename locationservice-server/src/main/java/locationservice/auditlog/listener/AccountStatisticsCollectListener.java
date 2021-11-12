package locationservice.auditlog.listener;

import locationservice.auditlog.AuditEntry;
import locationservice.auditlog.AuditLogService;
import locationservice.statistics.event.AccountStatisticsCollectEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountStatisticsCollectListener implements ApplicationListener<AccountStatisticsCollectEvent> {

    private final AuditLogService auditLogs;

    @Override
    public void onApplicationEvent(AccountStatisticsCollectEvent event) {
        var example = Example.of(AuditEntry.exampleWithLicense(event.account().key()));
        var count = auditLogs.getRepository().count(example).block();
        event.collect("auditlog_count", count);
    }
}