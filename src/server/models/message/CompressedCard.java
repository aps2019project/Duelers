package server.models.message;

import server.models.card.AttackType;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.spell.Spell;

import java.util.ArrayList;

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
