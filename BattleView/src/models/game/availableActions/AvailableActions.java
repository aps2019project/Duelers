package models.game.availableActions;

import models.card.AttackType;
import models.comperessedData.*;
import models.map.Position;

import java.util.ArrayList;

public class AvailableActions {
    private ArrayList<Insert> handInserts = new ArrayList<>();
    private ArrayList<Insert> collectibleInserts = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private ArrayList<Combo> combos = new ArrayList<>();
    private SpecialPower specialPower;
    private ArrayList<Move> moves = new ArrayList<>();

    public void calculate(CompressedGame game) {
        clearEverything();
        CompressedPlayer ownPlayer = game.getCurrentTurnPlayer();
        CompressedPlayer otherPlayer = game.getOtherTurnPlayer();

        calculateCardInserts(ownPlayer);
        calculateCollectibles(ownPlayer);
        calculateAttacks(ownPlayer, otherPlayer);
        calculateCombo(ownPlayer, otherPlayer);
        calculateSpecialPower(game, ownPlayer);
        calculateMoves(game, ownPlayer);
    }

    private void calculateCardInserts(CompressedPlayer ownPlayer) {
        for (CompressedCard card : ownPlayer.getHand()) {
            if (ownPlayer.getCurrentMP() >= card.getMannaPoint()) {
                handInserts.add(new Insert(card));
            }
        }
    }

    private void calculateCollectibles(CompressedPlayer ownPlayer) {
        for (CompressedCard item : ownPlayer.getCollectedItems()) {
            collectibleInserts.add(new Insert(item));
        }
    }

    private void calculateAttacks(CompressedPlayer ownPlayer, CompressedPlayer otherPlayer) {
        for (CompressedTroop myTroop : ownPlayer.getTroops()) {
            if (!myTroop.canAttack()) continue;

            ArrayList<CompressedTroop> targets = new ArrayList<>();
            for (CompressedTroop enemyTroop : otherPlayer.getTroops()) {
                if (enemyTroop.isNoAttackFromWeakerOnes() && myTroop.getCurrentAp() < enemyTroop.getCurrentAp())
                    continue;

                if (checkRangeForAttack(myTroop, enemyTroop)) continue;

                targets.add(enemyTroop);
            }

            if (targets.size() == 0) continue;

            attacks.add(new Attack(myTroop, targets));
        }
    }

    private void calculateCombo(CompressedPlayer ownPlayer, CompressedPlayer otherPlayer) {
        for (CompressedTroop enemyTroop : otherPlayer.getTroops()) {

            ArrayList<CompressedTroop> attackers = new ArrayList<>();
            for (CompressedTroop myTroop : ownPlayer.getTroops()) {
                if (!myTroop.getCard().isHasCombo() || !myTroop.canAttack()) continue;

                if (enemyTroop.isNoAttackFromWeakerOnes() && myTroop.getCurrentAp() < enemyTroop.getCurrentAp())
                    continue;

                if (checkRangeForAttack(myTroop, enemyTroop)) continue;

                attackers.add(myTroop);
            }

            if (attackers.size() == 0) continue;

            combos.add(new Combo(attackers, enemyTroop));
        }
    }

    private void calculateSpecialPower(CompressedGame game, CompressedPlayer ownPlayer) {
        CompressedTroop hero = ownPlayer.getHero();

        if (hero != null) {
            CompressedSpell spell = hero.getCard().getSpell();

            if (spell != null && !spell.isCoolDown(game.getTurnNumber()) && spell.getMannaPoint() <= ownPlayer.getCurrentMP()) {
                specialPower = new SpecialPower(hero);
            }
        }
    }

    private void calculateMoves(CompressedGame game, CompressedPlayer ownPlayer) {
        for (CompressedTroop troop : ownPlayer.getTroops()) {
            if (!troop.canMove()) continue;

            Position currentPosition = troop.getPosition();
            ArrayList<Position> targets = new ArrayList<>();

            for (int column = currentPosition.getColumn() - 2; column <= currentPosition.getColumn() + 2; column++) {
                int rowDown = currentPosition.getRow() + (2 - Math.abs(column - currentPosition.getColumn()));
                int rowUp = currentPosition.getRow() - (2 - Math.abs(column - currentPosition.getColumn()));

                for (int row = rowUp; row <= rowDown; row++) {
                    if (!game.getGameMap().isInMap(row, column)) continue;

                    Position cell = game.getGameMap().getCell(row, column).toPosition();
                    if (currentPosition.equals(cell)) continue;

                    if (game.getGameMap().getTroop(cell) == null) {
                        targets.add(cell);
                    }
                }
            }

            if (targets.size() == 0) continue;

            moves.add(new Move(troop, targets));
        }
    }

    private void clearEverything() {
        handInserts.clear();
        collectibleInserts.clear();
        attacks.clear();
        combos.clear();
        moves.clear();
        specialPower = null;
    }

    private boolean checkRangeForAttack(CompressedTroop myTroop, CompressedTroop enemyTroop) {
        if (myTroop.getCard().getAttackType() == AttackType.MELEE) {
            return !myTroop.getPosition().isNextTo(enemyTroop.getPosition());
        } else if (myTroop.getCard().getAttackType() == AttackType.RANGED) {
            return myTroop.getPosition().isNextTo(enemyTroop.getPosition()) ||
                    myTroop.getPosition().manhattanDistance(enemyTroop.getPosition()) > myTroop.getCard().getRange();
        } else { // HYBRID
            return myTroop.getPosition().manhattanDistance(enemyTroop.getPosition()) > myTroop.getCard().getRange();
        }
    }

    public ArrayList<Insert> getHandInserts() {
        return handInserts;
    }

    public ArrayList<Insert> getCollectibleInserts() {
        return collectibleInserts;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public ArrayList<Combo> getCombos() {
        return combos;
    }

    public SpecialPower getSpecialPower() {
        return specialPower;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public boolean canInsertCard(String cardId, int row, int column, CompressedGameMap map) {
        if (!map.isInMap(row, column)) return false;
        for (Insert insert : handInserts) {
            if (insert.getCard().getCardId().equalsIgnoreCase(cardId)) return true;
        }
        return false;
    }
}
