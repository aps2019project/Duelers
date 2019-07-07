package server.clientPortal.models.message;

import server.dataCenter.models.account.Collection;

class OriginalCardsCopyMessage {
    private Collection originalCards;

    OriginalCardsCopyMessage(Collection originalCards) {
        this.originalCards = originalCards;
    }
}
