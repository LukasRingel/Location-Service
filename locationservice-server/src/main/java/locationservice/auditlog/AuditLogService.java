package locationservice.auditlog;

import locationservice.account.ApiAccount;
import locationservice.geo.NetworkAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@AllArgsConstructor
public class AuditLogService {

    private final AuditLogRepository repository;

    public void log(ApiAccount license, NetworkAddress address) {
        repository.save(AuditEntry.createNewEntry(license.key(), address)).subscribe();
        log.info(license.name() + " requested geo-data for address " + address.address());
    }
}