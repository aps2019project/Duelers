package client.models.comperessedData;

import client.models.map.Position;

public class CompressedTroop {
    private CompressedCard card;
    private int currentAp;
    private int currentHp;
    private int enemyHitChanges;
    private Position position;
    private boolean canMove = true;
    private boolean canAttack = true;
    private boolean isDisarm;
    private boolean noAttackFromWeakerOnes;
    private int numberOfCollectedFlags;
    private int playerNumber;

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

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isCanAttack() {
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
