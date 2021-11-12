package locationservice.statistics.rest;

import locationservice.account.AccountExpired;
import locationservice.account.AccountInvalidPermissions;
import locationservice.statistics.ApplicationStatistics;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface StatisticsRestController {

    @GetMapping("statistics/application")
    ResponseEntity<ApplicationStatistics> collectApplicationStatistics(@RequestHeader("X-API-KEY") String apiKey)
          throws AccountExpired, AccountInvalidPermissions;

    @GetMapping("statistics/account")
    ResponseEntity<ApplicationStatistics> collectAccountStatistics(@RequestHeader("X-API-KEY") String apiKey)
          throws AccountExpired, AccountInvalidPermissions;

}
