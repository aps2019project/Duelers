package server.models.message;

import client.models.map.Position;
import server.models.card.Card;

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

    public CompressedTroop(Card card, int currentAp, int currentHp, int enemyHitChanges, Position position,
                           boolean canMove, boolean canAttack, boolean isDisarm, boolean noAttackFromWeakerOnes,
                           int numberOfCollectedFlags, int playerNumber) {
        this.card = card.toCompressedCard();
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
}
