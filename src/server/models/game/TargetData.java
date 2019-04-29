package server.models.game;

import server.models.card.Card;
import server.models.map.Cell;

import java.util.ArrayList;

public class TargetData {
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Troop> troops = new ArrayList<>();
    ArrayList<Cell> cells = new ArrayList<>();
}
