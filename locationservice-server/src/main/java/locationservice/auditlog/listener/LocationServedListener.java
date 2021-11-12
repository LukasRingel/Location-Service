package locationservice.auditlog.listener;

import locationservice.auditlog.AuditLogService;
import locationservice.geolocation.event.GeoLocationServedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationServedListener implements ApplicationListener<GeoLocationServedEvent> {

    private final AuditLogService auditLogService;

    @Override
    public void onApplicationEvent(GeoLocationServedEvent event) {
        auditLogService.log(event.getAccount(), event.getLocation().address());
    }
}