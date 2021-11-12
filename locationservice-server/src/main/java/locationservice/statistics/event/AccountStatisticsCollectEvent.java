package locationservice.statistics.event;

import locationservice.account.ApiAccount;

public class AccountStatisticsCollectEvent extends AbstractStatisticsCollectEvent {

    private final ApiAccount apiAccount;

    public AccountStatisticsCollectEvent(Object source, ApiAccount apiAccount) {
        super(source);
        this.apiAccount = apiAccount;
    }

    public ApiAccount account() {
        return apiAccount;
    }
}
