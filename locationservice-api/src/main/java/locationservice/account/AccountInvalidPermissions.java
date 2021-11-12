package locationservice.account;

public class AccountInvalidPermissions extends Exception {

    private final Role hasRole;
    private final Role needRole;

    public static AccountInvalidPermissions createForHasUserNeedAdmin() {
        return new AccountInvalidPermissions(Role.USER, Role.ADMINISTRATOR);
    }

    private AccountInvalidPermissions(Role hasRole, Role needRole) {
        super("You only have role %s but need %s".formatted(hasRole.name(), needRole.name()));
        this.hasRole = hasRole;
        this.needRole = needRole;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }

    public Role hasRole() {
        return hasRole;
    }

    public Role needRole() {
        return needRole;
    }
}
