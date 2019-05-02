package client.models.comperessedData;

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
}
