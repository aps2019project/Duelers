package server.gameCenter.models.game;

import server.detaCenter.models.card.Card;
import server.gameCenter.models.map.Cell;

import java.util.ArrayList;
import java.util.List;

class TargetData {
    private List<Card> cards = new ArrayList<>();
    private List<Troop> troops = new ArrayList<>();
    private List<Cell> cells = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    TargetData(List<Troop> troops) {
        this.troops = troops;
    }

    TargetData() {
    }

    List<Card> getCards() {
        return cards;
    }

    List<Troop> getTroops() {
        return troops;
    }

    List<Cell> getCells() {
        return cells;
    }

    List<Player> getPlayers() {
        return players;
    }
}
