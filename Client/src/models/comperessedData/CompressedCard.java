package models.comperessedData;

import client.models.card.AttackType;
import client.models.card.CardType;

public class CompressedCard {
    private String name;
    private String description;
    private String cardId;
    private CardType type;
    private CompressedSpell spell;//just for hero
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCardId() {
        return cardId;
    }

    public CardType getType() {
        return type;
    }

    public CompressedSpell getSpell() {
        return spell;
    }

    public int getDefaultAp() {
        return defaultAp;
    }

    public int getDefaultHp() {
        return defaultHp;
    }

    public int getMannaPoint() {
        return mannaPoint;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public int getRange() {
        return range;
    }

    public boolean isHasCombo() {
        return hasCombo;
    }
}
