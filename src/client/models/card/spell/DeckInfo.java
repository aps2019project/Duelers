package client.models.card.spell;

import server.models.card.Deck;
import server.models.game.GameType;
import server.models.game.Story;

public class DeckInfo {
    private String deckName;
    private String HeroName;
    private GameType type;

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
