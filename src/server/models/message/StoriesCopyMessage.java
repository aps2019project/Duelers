package server.models.message;

import server.models.card.spell.DeckInfo;
import server.models.game.Story;

public class StoriesCopyMessage {
    private DeckInfo[] stories;

    public StoriesCopyMessage(Story[] stories) {
        this.stories = new DeckInfo[stories.length];//TODO:reCode Story
        for (int i = 0; i < stories.length; i++) {
            this.stories[i] = new DeckInfo(stories[i]);
        }
    }
}
