package models.comperessedData;

import models.map.Position;

public class CompressedTroop {
    private CompressedCard card;
    private int currentAp;
    private int currentHp;
    private int enemyHitChanges;
    private Position position;
    private boolean canMove;
    private boolean canAttack;
    private boolean isDisarm;
    private boolean noAttackFromWeakerOnes;
    private int numberOfCollectedFlags;
    private int playerNumber;

    //just for testing BattleView


    public CompressedTroop(CompressedCard card, int currentAp, int currentHp, int enemyHitChanges, Position position,
                           boolean canMove, boolean canAttack, boolean isDisarm, boolean noAttackFromWeakerOnes,
                           int numberOfCollectedFlags, int playerNumber) {
        this.card = card;
        this.currentAp = currentAp;
        this.currentHp = currentHp;
        this.enemyHitChanges = enemyHitChanges;
        this.position = position;
        this.canMove = canMove;
        this.canAttack = canAttack;
        this.isDisarm = isDisarm;
        this.noAttackFromWeakerOnes = noAttackFromWeakerOnes;
        this.numberOfCollectedFlags = numberOfCollectedFlags;
        this.playerNumber = playerNumber;
    }

    public CompressedCard getCard() {
        return card;
    }

    public int getCurrentAp() {
        return currentAp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getEnemyHitChanges() {
        return enemyHitChanges;
    }

    public Position getPosition() {
        return position;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public boolean isDisarm() {
        return isDisarm;
    }

    public boolean isNoAttackFromWeakerOnes() {
        return noAttackFromWeakerOnes;
    }

    public int getNumberOfCollectedFlags() {
        return numberOfCollectedFlags;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
