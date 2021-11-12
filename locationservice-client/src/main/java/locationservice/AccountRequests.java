package locationservice;

import io.reactivex.rxjava3.core.Observable;
import locationservice.account.Account;
import locationservice.account.AccountExpired;
import retrofit2.http.GET;

public interface AccountRequests {

    @GET("account")
    Observable<Account> showAccountInformation();

}
