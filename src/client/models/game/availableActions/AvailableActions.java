package client.models.game.availableActions;

import client.models.card.AttackType;
import client.models.comperessedData.*;
import client.models.map.Position;

import java.util.ArrayList;

public class AvailableActions {
    private ArrayList<Insert> handInserts = new ArrayList<>();
    private ArrayList<Insert> collectibleInserts = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private ArrayList<Combo> combos = new ArrayList<>();
    private SpecialPower specialPower;
    private ArrayList<Move> moves = new ArrayList<>();

    public void calculateAvailableActions(CompressedGame game) {
        CompressedPlayer ownPlayer = game.getCurrentTurnPlayer();
        CompressedPlayer otherPlayer = game.getOtherTurnPlayer();

        for (CompressedCard card : ownPlayer.getHand()) {
            if (ownPlayer.getCurrentMP() >= card.getMannaPoint()) {
                handInserts.add(new Insert(card));
            }
        }

        for (CompressedCard item : ownPlayer.getCollectedItems()) {
            collectibleInserts.add(new Insert(item));
        }

        for (CompressedTroop myTroop : ownPlayer.getTroops()) {
            if (!myTroop.isCanAttack()) continue;

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

        for (CompressedTroop enemyTroop : otherPlayer.getTroops()) {

            ArrayList<CompressedTroop> attackers = new ArrayList<>();
            for (CompressedTroop myTroop : ownPlayer.getTroops()) {
                if (!myTroop.getCard().isHasCombo() || !myTroop.isCanAttack()) continue;

                if (enemyTroop.isNoAttackFromWeakerOnes() && myTroop.getCurrentAp() < enemyTroop.getCurrentAp())
                    continue;

                if (checkRangeForAttack(myTroop, enemyTroop)) continue;

                attackers.add(myTroop);
            }

            if (attackers.size() == 0) continue;

            combos.add(new Combo(attackers, enemyTroop));
        }

        CompressedTroop hero = ownPlayer.getHero();

        if (hero != null) {
            CompressedSpell spell = hero.getCard().getSpell();

            if (spell != null && !spell.isCoolDown(game.getTurnNumber()) && spell.getMannaPoint() <= ownPlayer.getCurrentMP()) {
                specialPower = new SpecialPower(hero);
            }
        }

        for (CompressedTroop troop : ownPlayer.getTroops()) {
            Position currentPosition = troop.getPosition();
            ArrayList<Position> targets = new ArrayList<>();

            for (int column = currentPosition.getColumn() - 2; column <= currentPosition.getColumn() + 2; column++) {
                int rowDown = currentPosition.getRow() + (4 - Math.abs(column - currentPosition.getColumn()));
                int rowUp = currentPosition.getRow() - (4 - Math.abs(column - currentPosition.getColumn()));

                for (int row = rowUp; row <= rowDown; row++) {
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
}
