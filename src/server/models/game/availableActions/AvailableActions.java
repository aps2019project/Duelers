package server.models.game.availableActions;

import server.models.card.AttackType;
import server.models.card.Card;
import server.models.card.spell.Spell;
import server.models.game.Game;
import server.models.game.Player;
import server.models.game.Troop;
import server.models.map.Cell;
import server.models.map.Position;

import java.util.ArrayList;

public class AvailableActions {
    private ArrayList<Insert> handInserts = new ArrayList<>();
    private ArrayList<Insert> collectibleInserts = new ArrayList<>();
    private ArrayList<Attack> attacks = new ArrayList<>();
    private ArrayList<Combo> combos = new ArrayList<>();
    private SpecialPower specialPower;
    private ArrayList<Move> moves = new ArrayList<>();

    public void calculateAvailableActions(Game game) {

        calculateAvailableInsets(game);

        calculateAvailableAttacks(game);

        calculateAvailableCombos(game);

        calculateAvailableSpecialPower(game);

        calculateAvailableMoves(game);
    }

    public void calculateAvailableInsets(Game game) {
        Player ownPlayer = game.getCurrentTurnPlayer();
        collectibleInserts.clear();
        handInserts.clear();

        for (Card card : ownPlayer.getHand()) {
            if (ownPlayer.getCurrentMP() >= card.getMannaPoint()) {
                handInserts.add(new Insert(card));
            }
        }

        for (Card item : ownPlayer.getCollectedItems()) {
            collectibleInserts.add(new Insert(item));
        }
    }

    public void calculateAvailableAttacks(Game game) {
        Player ownPlayer = game.getCurrentTurnPlayer();
        Player otherPlayer = game.getOtherTurnPlayer();
        attacks.clear();
        for (Troop myTroop : ownPlayer.getTroops()) {
            if (!myTroop.canAttack()) continue;

            ArrayList<Troop> targets = new ArrayList<>();
            for (Troop enemyTroop : otherPlayer.getTroops()) {
                if (enemyTroop.canBeAttackedFromWeakerOnes() && myTroop.getCurrentAp() < enemyTroop.getCurrentAp())
                    continue;

                if (checkRangeForAttack(myTroop, enemyTroop)) continue;

                targets.add(enemyTroop);
            }

            if (targets.size() == 0) continue;

            attacks.add(new Attack(myTroop, targets));
        }
    }

    public void calculateAvailableCombos(Game game) {
        Player ownPlayer = game.getCurrentTurnPlayer();
        Player otherPlayer = game.getOtherTurnPlayer();
        combos.clear();
        for (Troop enemyTroop : otherPlayer.getTroops()) {
            ArrayList<Troop> attackers = new ArrayList<>();
            for (Troop myTroop : ownPlayer.getTroops()) {
                if (!myTroop.getCard().hasCombo() || !myTroop.canAttack()) continue;

                if (enemyTroop.canBeAttackedFromWeakerOnes() && myTroop.getCurrentAp() < enemyTroop.getCurrentAp())
                    continue;

                if (checkRangeForAttack(myTroop, enemyTroop)) continue;

                attackers.add(myTroop);
            }

            if (attackers.size() == 0) continue;

            combos.add(new Combo(attackers, enemyTroop));
        }
    }

    public void calculateAvailableSpecialPower(Game game) {
        Player ownPlayer = game.getCurrentTurnPlayer();
        Troop hero = ownPlayer.getHero();

        if (hero != null) {
            Spell spell = hero.getCard().getSpells().get(0);

            if (spell != null && !spell.isCoolDown(game.getTurnNumber()) && spell.getMannaPoint() <= ownPlayer.getCurrentMP()) {
                specialPower = new SpecialPower(hero);
            }
        }
    }

    public void calculateAvailableMoves(Game game) {
        Player ownPlayer = game.getCurrentTurnPlayer();
        moves.clear();
        for (Troop troop : ownPlayer.getTroops()) {
            if (!troop.canMove()) continue;

            Position currentPosition = new Position(troop.getCell());
            ArrayList<Position> targets = new ArrayList<>();

            for (int column = currentPosition.getColumn() - 2; column <= currentPosition.getColumn() + 2; column++) {
                int rowDown = currentPosition.getRow() + (4 - Math.abs(column - currentPosition.getColumn()));
                int rowUp = currentPosition.getRow() - (4 - Math.abs(column - currentPosition.getColumn()));

                for (int row = rowUp; row <= rowDown; row++) {
                    Cell cell = game.getGameMap().getCell(row, column);
                    if (currentPosition.equals(cell)) continue;

                    if (game.getGameMap().getTroop(cell) == null) {
                        targets.add(new Position(cell));
                    }
                }
            }

            if (targets.size() == 0) continue;

            moves.add(new Move(troop, targets));
        }
    }

    private boolean checkRangeForAttack(Troop myTroop, Troop enemyTroop) {
        if (myTroop.getCard().getAttackType() == AttackType.MELEE) {
            return !myTroop.getCell().isNextTo(enemyTroop.getCell());
        } else if (myTroop.getCard().getAttackType() == AttackType.RANGED) {
            return myTroop.getCell().isNextTo(enemyTroop.getCell()) ||
                    myTroop.getCell().manhattanDistance(enemyTroop.getCell()) > myTroop.getCard().getRange();
        } else { // HYBRID
            return myTroop.getCell().manhattanDistance(enemyTroop.getCell()) > myTroop.getCard().getRange();
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
}
