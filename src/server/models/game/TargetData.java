package server.models.game;

import server.models.card.Card;
import server.models.map.Cell;

import java.util.ArrayList;

public class TargetData {
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Troop> troops = new ArrayList<>();
    private ArrayList<Cell> cells = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    TargetData(ArrayList<Troop> troops) {
        this.troops = troops;
    }

    TargetData() {
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
