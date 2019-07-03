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
        String old = this.name;
        this.name = name;
        this.cardId = name.replaceAll(" ", "");
        support.firePropertyChange("name", old, name);
    }

    public void setDescription(String description) {
        String old = this.description;
        this.description = description;
        support.firePropertyChange("description", old, description);
    }

    public void setSpriteName(String spriteName) {
        String old = this.spriteName;
        this.spriteName = spriteName;
        support.firePropertyChange("spriteName", old, spriteName);
    }

    public void setType(CardType type) {
        CardType old = this.type;
        this.type = type;
        support.firePropertyChange("type", old, type);
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
        support.firePropertyChange("spells", null, spells);
    }

    public void removeSpell(Spell spell) {
        spells.remove(spell);
        support.firePropertyChange("spells", null, spells);
    }

    public void setDefaultAp(int defaultAp) {
        int old = this.defaultAp;
        this.defaultAp = defaultAp;
        support.firePropertyChange("defaultAp", old, defaultAp);
    }

    public void setDefaultHp(int defaultHp) {
        int old = this.defaultHp;
        this.defaultHp = defaultHp;
        support.firePropertyChange("defaultHp", old, defaultHp);
    }

    public void setMannaPoint(int mannaPoint) {
        int old = this.mannaPoint;
        this.mannaPoint = mannaPoint;
        support.firePropertyChange("mannaPoint", old, mannaPoint);
    }

    public void setPrice(int price) {
        int old = this.price;
        this.price = price;
        support.firePropertyChange("price", old, price);
    }

    public void setAttackType(AttackType attackType) {
        AttackType old = this.attackType;
        this.attackType = attackType;
        support.firePropertyChange("attackType", old, attackType);
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
