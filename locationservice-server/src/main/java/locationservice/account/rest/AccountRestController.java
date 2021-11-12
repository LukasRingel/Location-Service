package locationservice.account.rest;

import locationservice.account.Account;
import locationservice.account.AccountExpired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AccountRestController {

    @GetMapping("account")
    ResponseEntity<Account> showAccountInformation(@RequestHeader("X-API-KEY") String requestHeader) throws AccountExpired;

}