package models.card;

import models.ICard;
import models.card.spell.Spell;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class EditableCard implements ICard {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String name;
    private String description;
    private String cardId;
    private String spriteName;
    private CardType type;
    private ArrayList<Spell> spells = new ArrayList<>();
    private int defaultAp;
    private int defaultHp;
    private int mannaPoint;
    private int price;
    private AttackType attackType;
    private int range;
    private boolean hasCombo;

    public void setName(String name) {
        this.name = name;
        this.cardId = name.replaceAll(" ", "");
    }

    public void setDescription(String description) {
        support.firePropertyChange("description", this.description, description);
        this.description = description;
    }

    public void setSpriteName(String spriteName) {
        support.firePropertyChange("spriteName", this.spriteName, spriteName);
        this.spriteName = spriteName;
    }

    public void setType(CardType type) {
        support.firePropertyChange("type", this.type, type);
        this.type = type;
    }

    public void addSpell(Spell spell) {
        support.firePropertyChange("spells", null, spells);
        spells.add(spell);
    }

    public void removeSpell(Spell spell) {
        support.firePropertyChange("spells", null, spells);
        spells.remove(spell);
    }

    public void setDefaultAp(int defaultAp) {
        support.firePropertyChange("defaultAp", this.defaultAp, defaultAp);
        this.defaultAp = defaultAp;
    }

    public void setDefaultHp(int defaultHp) {
        support.firePropertyChange("defaultHp", this.defaultHp, defaultHp);
        this.defaultHp = defaultHp;
    }

    public void setMannaPoint(int mannaPoint) {
        support.firePropertyChange("mannaPoint", this.mannaPoint, mannaPoint);
        this.mannaPoint = mannaPoint;
    }

    public void setPrice(int price) {
        support.firePropertyChange("price", this.price, price);
        this.price = price;
    }

    public void setAttackType(AttackType attackType) {
        this.attackType = attackType;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void setHasCombo(boolean hasCombo) {
        this.hasCombo = hasCombo;
    }

    @Override
    public CardType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSpriteName() {
        return spriteName;
    }

    @Override
    public int getDefaultAp() {
        return defaultAp;
    }

    @Override
    public int getDefaultHp() {
        return defaultHp;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getMannaPoint() {
        return mannaPoint;
    }

    public void addListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}
