package server.models.card;

import server.models.card.spell.Spell;

import java.util.ArrayList;

public class Card {
    private String name;
    private String description;
    private String cardId;
    private CardType type;
    private ArrayList<Spell> spells = new ArrayList<>();
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private int price;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;

    public Card(Card referenceCard) {
        this.name = referenceCard.name;
        this.description = referenceCard.description;
        this.cardId = referenceCard.cardId;
        this.type = referenceCard.type;
        if (referenceCard.spells != null){
            for (Spell spell : referenceCard.spells) {
                spells.add(new Spell(spell));
            }
        }
        this.defaultAp = referenceCard.defaultAp;
        this.defaultHp = referenceCard.defaultHp;
        this.mannaPoint = referenceCard.mannaPoint;
        this.price = referenceCard.price;
        this.attackType = referenceCard.attackType;
        this.hasCombo = referenceCard.hasCombo;
    }

    public Card(String name, String description, CardType type, ArrayList<Spell> spells, int defaultAp, int defaultHp, int mannaPoint, int price, AttackType attackType, int range, boolean hasCombo) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.spells = spells;
        this.defaultAp = defaultAp;
        this.defaultHp = defaultHp;
        this.mannaPoint = mannaPoint;
        this.price = price;
        this.attackType = attackType;
        this.range = range;
        this.hasCombo = hasCombo;
    }

    public boolean areSame(String cardName) {
        return this.name.equals(cardName);
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().getName().equals(obj.getClass().getName())) return false;
        Card card = (Card) obj;
        return this.cardId.equals(card.cardId);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public CardType getType() {
        return this.type;
    }

    public ArrayList<Spell> getSpells() {
        return this.spells;
    }

    public int getDefaultAp() {
        return this.defaultAp;
    }

    public int getDefaultHp() {
        return this.defaultHp;
    }

    public int getMannaPoint() {
        return this.mannaPoint;
    }

    public AttackType getAttackType() {
        return this.attackType;
    }

    public int getRange() {
        return this.range;
    }

    public int getPrice() {
        return price;
    }

    public boolean hasCombo() {
        return hasCombo;
    }
}