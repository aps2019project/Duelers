package server.models.game;

import server.Server;
import server.models.card.Card;
import server.models.map.Cell;

public class Troop {
    private Card card;
    private int currentAp;
    private int currentHp;
    private int enemyHitChanges;
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

    public boolean canGetPoison() {
        return !cantGetPoison;
    }

    public boolean canGetDisarm() {
        return !cantGetDisarm;
    }

    public boolean canGetStun() {
        return !cantGetStun;
    }

    public boolean canGiveBadEffect() {
        return !dontGiveBadEffect;
    }

    public boolean canBeAttackedFromWeakerOnes() {
        return !noAttackFromWeakerOnes;
    }

    public boolean isHolyBuffDisabling() {
        return disableHolyBuff;
    }

    public void setDisarm(boolean disarm) {
        this.isDisarm = disarm;
        //TODO:Send Message
    }

    public boolean hasFlag() {
        return this.flagNumber > 0;
    }

    public int getEnemyHitChanges() {
        return enemyHitChanges;
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

    public void changeCurrentAp(int change) {
        currentAp += change;
        if (currentAp < 0) {
            currentAp = 0;
        }
    }

    public void changeCurrentHp(int change) {
        currentHp += change;
    }

    public void changeEnemyHit(int change) {
        enemyHitChanges += change;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setCantGetPoison(boolean cantGetPoison) {
        this.cantGetPoison = cantGetPoison;
    }

    public void setCantGetDisarm(boolean cantGetDisarm) {
        this.cantGetDisarm = cantGetDisarm;
    }

    public void setCantGetStun(boolean cantGetStun) {
        this.cantGetStun = cantGetStun;
    }

    public void setDontGiveBadEffect(boolean dontGiveBadEffect) {
        this.dontGiveBadEffect = dontGiveBadEffect;
    }

    public void setNoAttackFromWeakerOnes(boolean noAttackFromWeakerOnes) {
        this.noAttackFromWeakerOnes = noAttackFromWeakerOnes;
    }

    public void setDisableHolyBuff(boolean disableHolyBuff) {
        this.disableHolyBuff = disableHolyBuff;
    }


}