package server.dataCenter.models.db;

import server.dataCenter.DataBase;
import server.dataCenter.models.account.Collection;
import server.dataCenter.models.card.Card;
import server.gameCenter.models.game.Story;

import java.util.List;

public class Rest implements DataBase {
    @Override
    public Card getCard(String cardName) {
        return null;
    }

    @Override
    public Collection getOriginalCards() {
        return null;
    }

    @Override
    public List<Story> getStories() {
        return null;
    }

    @Override
    public List<Card> getCollectibleItems() {
        return null;
    }

    @Override
    public Collection getNewCustomCards() {
        return null;
    }

    @Override
    public Card getOriginalFlag() {
        return null;
    }

    @Override
    public void addNewCustomCards(Card card) {

    }

    @Override
    public void removeCustomCards(Card card) {

    }

    @Override
    public void addOriginalCard(Card card) {

    }

    @Override
    public void addNewCollectible(Card card) {

    }

    @Override
    public void setOriginalFlag(Card loadFile) {

    }

    @Override
    public void addStory(Story story) {

    }
}
