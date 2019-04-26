package client.models.card;

import client.models.game.GameType;
import client.models.game.Story;

public class DeckInfo {
    private String deckName;
    private String HeroName;
    private GameType type;

    public DeckInfo(Story story) {
        this.deckName = story.getDeck().getName();
        this.HeroName = story.getDeck().getHero().getName();
        this.type = story.getGameType();
    }

    public String getDeckName() {
        return deckName;
    }

    public String getHeroName() {
        return HeroName;
    }

    public GameType getType() {
        return type;
    }
}
