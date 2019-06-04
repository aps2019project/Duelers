package server.clientPortal.models.message;

import server.detaCenter.models.account.Collection;

public class OriginalCardsCopyMessage {
    private Collection originalCards;

    public OriginalCardsCopyMessage(Collection originalCards) {
        this.originalCards = originalCards;
    }
}
