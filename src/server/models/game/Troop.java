package server.models.game;

import server.models.card.Card;
import server.models.card.spell.Spell;
import server.models.map.Cell;

import java.util.ArrayList;

public class Troop {
    private Card card;
    private ArrayList<Spell> excessiveSpells;
    private int currentAp;
    private int currentHp;
    private Cell cell;
    private boolean cantMove;
    private boolean cantAttack;
    private boolean isDisarm;
    private boolean cantBePoison;
    private boolean cantBeDisarm;
    private boolean cantBeStun;
    private boolean dontGiveBadEffect;
    private boolean noAttackFromWeakerOnes;
    private boolean disableHolyBuff;
    private Card flag;

    public Troop(Card card, Cell cell) {

    }

    public Card getCard() {
        return this.card;
    }

    public int getCurrentAp() {
        return this.currentAp;
    }

    public int getCurrentHp() {
        return this.currentHp;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void moveTo(Cell cell) {

    }

    public boolean canMove() {
        return this.cantMove;
    }

    public void setCantMove(boolean can) {
        this.cantMove = can;
    }

    public boolean canAttack() {
        return this.cantAttack;
    }

    public void setCantAttack(boolean can) {
        this.cantAttack = can;
    }

    public boolean isDisarm() {
        return this.isDisarm;
    }

    public void setDisarm(boolean disarm) {
        this.isDisarm = disarm;
    }

    public boolean hasFlag() {
        return this.flag != null;
    }

    public void collectFlag() {

    }

}