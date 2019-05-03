package server.models.message;

import server.models.account.Collection;

public class OriginalCardsCopyMessage {
    private Collection originalCards;

    public OriginalCardsCopyMessage(Collection originalCards) {
        this.originalCards = originalCards;
    }
}
