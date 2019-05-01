package client.models.game;

import client.models.card.AttackType;
import client.models.card.CardType;
import client.models.card.spell.Spell;

import java.util.ArrayList;

public class CompressedCard {
    private String name;
    private String description;
    private String cardId;
    private CardType type;
    private ArrayList<CompressesSpell> spells = new ArrayList<>();//just for hero
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;
}
