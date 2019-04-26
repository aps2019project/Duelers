package server.models.card.spell;

import server.models.card.Deck;
import server.models.game.GameType;
import server.models.game.Story;

public class DeckInfo {
    private String deckName;
    private String HeroName;
    private GameType type;

    public DeckInfo(Story story) {
        this.deckName = story.getDeck().getName();
        this.HeroName = story.getDeck().getHero().getName();
        this.type = story.getGameType();
    }

    public DeckInfo(Deck deck) {
        this.deckName = deck.getName();
        this.HeroName = deck.getHero().getName();
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
