package models.game;

import models.card.Card;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedTroop;

import java.util.ArrayList;

public interface GameActions {

    public void attack(CompressedTroop selectedTroop, CompressedTroop troop);

    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop troop);

    public void move(CompressedTroop selectedTroop, int j, int i);

    public void endTurn();

    public void insert(CompressedCard card, int row, int column);

    public void useSpecialPower(int row, int column);
}
