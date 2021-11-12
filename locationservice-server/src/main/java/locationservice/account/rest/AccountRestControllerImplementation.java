package locationservice.account.rest;

import locationservice.account.Account;
import locationservice.account.AccountExpired;
import locationservice.account.AccountRepository;
import locationservice.account.ApiAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@AllArgsConstructor
public class AccountRestControllerImplementation implements AccountRestController {

    private final AccountRepository accountRepository;

    @Override
    public ResponseEntity<Account> showAccountInformation(String requestHeader) throws AccountExpired {
        ApiAccount account = accountRepository.findById(UUID.fromString(requestHeader)).block();
        if (account == null || !account.isStillValid()) {
            throw AccountExpired.withKey(requestHeader);
        }
        return ResponseEntity.ok(new Account(account.key(), account.name(), account.activeUntil(), account.activationDate()));
    }
}