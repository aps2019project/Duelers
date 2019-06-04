package server.detaCenter.models.card.spell;

import server.detaCenter.models.card.Deck;
import server.gameCenter.models.game.GameType;
import server.gameCenter.models.game.Story;

public class DeckInfo {
    private String deckName;
    private String HeroName;
    private GameType type;

    public DeckInfo(Story story) {
        this.deckName = story.getDeck().getDeckName();
        this.HeroName = story.getDeck().getHero().getName();
        this.type = story.getGameType();
    }

    public DeckInfo(Deck deck) {
        this.deckName = deck.getDeckName();
        this.HeroName = deck.getHero().getName();
    }
}
