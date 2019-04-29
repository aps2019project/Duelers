package server.models.game;

import server.Server;
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
    private boolean canMove;
    private boolean canAttack;
    private boolean isDisarm;
    private boolean cantGetPoison;
    private boolean cantGetDisarm;
    private boolean cantGetStun;
    private boolean dontGiveBadEffect;
    private boolean noAttackFromWeakerOnes;
    private boolean disableHolyBuff;
    private int flagNumber;

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
        return this.canMove;
    }

    public void setCanMove(boolean can) {
        this.canMove = can;
        //TODO:Send Message
    }

    public boolean canAttack() {
        return this.canAttack;
    }

    public void setCanAttack(boolean can) {
        this.canAttack = can;
        //TODO:Send Message
    }

    public boolean isDisarm() {
        return this.isDisarm;
    }

    public void setDisarm(boolean disarm) {
        this.isDisarm = disarm;
        //TODO:Send Message
    }

    public boolean hasFlag() {
        return this.flagNumber > 0;
    }

    public void increaseFlagNumber() {

    }

    public void decreaseFlagNumber() {
        flagNumber--;
        if (flagNumber < 0){
            flagNumber=0;
            Server.getInstance().serverPrint("Error!");
        }
    }
}