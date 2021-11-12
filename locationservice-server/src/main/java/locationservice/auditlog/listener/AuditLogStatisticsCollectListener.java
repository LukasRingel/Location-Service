package locationservice.auditlog.listener;

import locationservice.auditlog.AuditLogService;
import locationservice.statistics.event.ApplicationStatisticsCollectEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuditLogStatisticsCollectListener implements ApplicationListener<ApplicationStatisticsCollectEvent> {

    private final AuditLogService logService;

    @Override
    public void onApplicationEvent(ApplicationStatisticsCollectEvent event) {
        event.collect("auditlog_count_global", logService.getRepository().count().block());
    }
}