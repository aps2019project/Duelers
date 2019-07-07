package server.gameCenter.models;

import server.dataCenter.models.account.Account;

public class Invitation {
    private final Account inviter;
    private final Account invited;

    public Invitation(Account inviter, Account invited) {
        this.inviter = inviter;
        this.invited = invited;
    }

    public Account getInviter() {
        return inviter;
    }

    public Account getInvited() {
        return invited;
    }
}
