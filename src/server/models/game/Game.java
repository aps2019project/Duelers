package server.models.game;

import server.Server;
import server.models.account.Account;
import server.models.card.AttackType;
import server.models.card.Card;
import server.models.card.CardType;
import server.models.card.spell.Spell;
import server.models.card.spell.SpellAction;
import server.models.map.Cell;
import server.models.map.GameMap;
import server.models.map.Position;

import java.util.ArrayList;

public abstract class Game {
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<Buff> buffs = new ArrayList<>();
    private GameMap gameMap;
    private int turnNumber;
    private int lastTurnChangingTime;
    private boolean finished = false;

    protected Game(Account accountOne, Account accountTwo, GameMap gameMap) {
        this.gameMap = gameMap;
        this.playerOne = new Player(accountOne, 1);
        this.playerTwo = new Player(accountTwo, 2);
        this.turnNumber = 1;
        put(1, playerOne.getHero(), gameMap.getCell(2, 0));
        this.turnNumber = 2;
        put(2, playerTwo.getHero(), gameMap.getCell(2, 8));
        this.turnNumber = 1;
        applyOnStartSpells(playerOne);
        applyOnStartSpells(playerTwo);
    }

    private void applyOnStartSpells(Player player) {
        for (Card card : player.getDeck().getOthers()) {
            for (Spell spell : card.getSpells()) {
                if (spell.getAvailabilityType().isOnStart())
                    applySpell(spell, detectTarget(
                            spell, gameMap.getCell(0, 0), gameMap.getCell(0, 0), gameMap.getCell(0, 0))
                    );
            }
        }
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    private Player getCurrentTurnPlayer() {
        if (turnNumber % 2 == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    private Player getOtherTurnPlayer() {
        if (turnNumber % 2 == 0) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    private boolean canCommand(String username) {
        return (turnNumber % 2 == 0 && username.equalsIgnoreCase(playerTwo.getUserName()))
                || (turnNumber % 2 == 1 && username.equalsIgnoreCase(playerOne.getUserName()));
    }

    public void changeTurn(String username) throws Exception {
        if (canCommand(username)) {
            getCurrentTurnPlayer().addNextCardToHand();
            revertNotDurableBuffs();
            turnNumber++;
            Server.getInstance().sendChangeTurnMessage(this, turnNumber);
            applyAllBuffs();
            selAllTroopsCanAttack();
            if (turnNumber < 14)
                getCurrentTurnPlayer().setCurrentMP(turnNumber / 2 + 2);
            else
                getCurrentTurnPlayer().setCurrentMP(9);
        } else {
            throw new Exception("it isn't your turn!");
        }
    }

    private void selAllTroopsCanAttack() {
        for (Troop troop : gameMap.getTroops()) {
            troop.setCanAttack(true);
        }
    }

    private void applyAllBuffs() {
        for (Buff buff : buffs) {
            applyBuff(buff);
        }
    }

    private void revertNotDurableBuffs() {
        for (Buff buff : buffs) {
            if (!buff.getAction().isDurable()) {
                revertBuff(buff);
            }
        }
    }

    private void revertBuff(Buff buff) {
        SpellAction action = buff.getAction();

        for (Troop troop : buff.getTarget().getTroops()) {
            if (!(buff.isPositive() || troop.canGiveBadEffect())) continue;

            troop.changeEnemyHit(-action.getEnemyHitChanges());
            troop.changeCurrentAp(-action.getApChange());
            if (!action.isPoison() || troop.canGetPoison()) {
                troop.changeCurrentHp(-action.getHpChange());
                if (troop.getCurrentHp() <= 0) {
                    killTroop(troop);
                }
            }
            if (action.isMakeStun() && troop.canGetStun()) {
                troop.setCanMove(true);
            }
            if (action.isMakeDisarm() && troop.canGetDisarm()) {
                troop.setDisarm(false);
            }
            if (action.isNoDisarm()) {
                troop.setCantGetDisarm(false);
            }
            if (action.isNoPoison()) {
                troop.setCantGetPoison(false);
            }
            if (action.isNoStun()) {
                troop.setCantGetStun(false);
            }
            if (action.isNoBadEffect()) {
                troop.setDontGiveBadEffect(false);
            }
            if (action.isNoAttackFromWeakerOnes()) {
                troop.setNoAttackFromWeakerOnes(false);
            }
            if (action.isDisableHolyBuff()) {
                troop.setDisableHolyBuff(false);
            }
        }
    }



    public void insert(String username, String cardId, Position position) throws Exception {
        if (!canCommand(username)) {
            throw new Exception("its not your turn");
        }
        Player player = getCurrentTurnPlayer();
        put(
                player.getPlayerNumber(),
                player.insert(cardId, gameMap.getCell(position)),
                gameMap.getCell(position)
        );
    }

    private void put(int playerNumber, Troop troop, Cell cell) {
        troop.setCell(cell);
        gameMap.addTroop(playerNumber, troop);
        applyOnPutSpells(troop, cell);
    }

    private void applyOnPutSpells(Troop troop, Cell cell) {
        for (Spell spell : troop.getCard().getSpells()) {
            if (spell.getAvailabilityType().isOnPut())
                applySpell(spell, detectTarget(spell, cell, cell, getCurrentTurnPlayer().getHero().getCell()));
        }
    }
    public void moveTroop(String username, String cardId, Position position) throws Exception {
        if (!canCommand(username)) {
            throw new Exception("its not your turn");
        }

        if (!gameMap.checkCoordination(position)) {
            throw new Exception("coordination is not valid");
        }

        Troop troop = gameMap.getTroop(cardId);
        if (troop == null) {
            throw new Exception("select a valid card");
        }
        if (troop.getCell().manhattanDistance(position) > 2) {
            throw new Exception("too far to go");
        }
        Cell cell = gameMap.getCell(position);
        troop.setCell(cell);
        for (Card card : cell.getItems()) {
            if (card.getType() == CardType.FLAG) {
                troop.addFlag(card);
                getCurrentTurnPlayer().increaseNumberOfCollectedFlags();
                getCurrentTurnPlayer().addFlagCarrier(troop);
            } else if (card.getType() == CardType.COLLECTIBLE_ITEM) {
                getCurrentTurnPlayer().addCollectibleItems(card);
            }
        }
        cell.clearItems();
    }
    public void attack(String username, String attackerCardId, String defenderCardId) throws Exception {
        if (!canCommand(username)) {
            throw new Exception("its not your turn");
        }

        Troop attackerTroop = getAndValidateTroop(attackerCardId, getCurrentTurnPlayer());
        Troop defenderTroop = getAndValidateTroop(defenderCardId, getOtherTurnPlayer());

        if (!attackerTroop.canAttack()) {
            throw new Exception("attacker can not attack");
        }

        checkRangeForAttack(attackerTroop, defenderTroop);

        if (defenderTroop.canGiveBadEffect() &&
                (defenderTroop.canBeAttackedFromWeakerOnes() || attackerTroop.getCurrentAp() > defenderTroop.getCurrentAp())
        ) {
            damage(attackerTroop, defenderTroop);

            attackerTroop.setCanAttack(false);
            applyOnAttackSpells(attackerTroop, defenderTroop);
            applyOnDefendSpells(defenderTroop, attackerTroop);
            counterAttack(defenderTroop, attackerTroop);
        }
    }

    private void applyOnAttackSpells(Troop attackerTroop, Troop defenderTroop) {
        for (Spell spell : attackerTroop.getCard().getSpells()) {
            if (spell.getAvailabilityType().isOnAttack())
                applySpell(
                        spell,
                        detectTarget(spell, defenderTroop.getCell(), defenderTroop.getCell(), getCurrentTurnPlayer().getHero().getCell())
                );
        }
    }

    private void applyOnDefendSpells(Troop defenderTroop, Troop attackerTroop) {
        for (Spell spell : defenderTroop.getCard().getSpells()) {
            if (spell.getAvailabilityType().isOnDefend())
                applySpell(
                        spell,
                        detectTarget(spell, attackerTroop.getCell(), attackerTroop.getCell(), getOtherTurnPlayer().getHero().getCell())
                );
        }
    }

    private void counterAttack(Troop defenderTroop, Troop attackerTroop) throws Exception {
        if (defenderTroop.isDisarm()) {
            throw new Exception("defender is disarm");
        }

        checkRangeForAttack(defenderTroop, attackerTroop);

        if (attackerTroop.canGiveBadEffect() &&
                (attackerTroop.canBeAttackedFromWeakerOnes() || defenderTroop.getCurrentAp() > attackerTroop.getCurrentAp())
        ) {
            damage(defenderTroop, attackerTroop);
        }
    }

    private void damage(Troop attackerTroop, Troop defenderTroop) {
        int attackPower = calculateAp(attackerTroop, defenderTroop);

        defenderTroop.changeCurrentHp(-attackPower);

        if (defenderTroop.getCurrentHp() <= 0) {
            killTroop(defenderTroop);
        }
    }

    private int calculateAp(Troop attackerTroop, Troop defenderTroop) {
        int attackPower = attackerTroop.getCurrentAp();
        if (!attackerTroop.isHolyBuffDisabling() || defenderTroop.getEnemyHitChanges() > 0) {
            attackPower += defenderTroop.getEnemyHitChanges();
        }
        return attackPower;
    }

    public void useSpecialPower(String username, String cardId, Position target) throws Exception {
        if (!canCommand(username)) {
            throw new Exception("its not your turn");
        }

        Troop hero = getAndValidateHero(cardId);
        Spell specialPower = getAndValidateSpecialPower(hero);

        applySpell(
                specialPower,
                detectTarget(specialPower, hero.getCell(), gameMap.getCell(target), hero.getCell())
        );
    }

    private Troop getAndValidateHero(String cardId) throws Exception {
        Troop hero = getCurrentTurnPlayer().getHero();
        if (hero == null || !hero.getCard().getCardId().equalsIgnoreCase(cardId)) {
            throw new Exception("hero id is not valid");
        }
        return hero;
    }

    private Spell getAndValidateSpecialPower(Troop hero) throws Exception {
        Spell specialPower = hero.getCard().getSpells().get(0);
        if (specialPower == null || specialPower.getAvailabilityType().isSpecialPower()) {
            throw new Exception("special power is not available");
        }

        if (specialPower.isCoolDown(turnNumber)) {
            throw new Exception("special power is cool down");
        }

        if (getCurrentTurnPlayer().getCurrentMP() < specialPower.getMannaPoint()) {
            throw new Exception("insufficient manna");
        }
        return specialPower;
    }

    public void comboAttack(String username, String[] attackerCardIds, String defenderCardId) throws Exception {
        if (!canCommand(username)) {
            throw new Exception("its not your turn");
        }

        Troop defenderTroop = getAndValidateTroop(defenderCardId, getOtherTurnPlayer());
        Troop[] attackerTroops = getAndValidateAttackerTroops(attackerCardIds, defenderTroop);

        damageFromAllAttackers(defenderTroop, attackerTroops);

        applyOnDefendSpells(defenderTroop, attackerTroops[0]);
        counterAttack(defenderTroop, attackerTroops[0]);
    }

    private Troop getAndValidateTroop(String defenderCardId, Player otherTurnPlayer) throws Exception {
        Troop troop = otherTurnPlayer.getTroop(defenderCardId);
        if (troop == null) {
            throw new Exception("card id is not valid");
        }
        return troop;
    }

    private Troop[] getAndValidateAttackerTroops(String[] attackerCardIds, Troop defenderTroop) throws Exception {
        Troop[] attackerTroops = new Troop[attackerCardIds.length];
        for (int i = 0; i < attackerTroops.length; i++) {
            attackerTroops[i] = getCurrentTurnPlayer().getTroop(attackerCardIds[i]);
            if (attackerTroops[i] == null || !attackerTroops[i].getCard().hasCombo()) {
                throw new Exception("invalid attacker troop");
            }

            checkRangeForAttack(attackerTroops[i], defenderTroop);
        }
        return attackerTroops;
    }

    private void checkRangeForAttack(Troop attackerTroop, Troop defenderTroop) throws Exception {
        if (attackerTroop.getCard().getAttackType() == AttackType.MELEE) {
            if (!attackerTroop.getCell().isNextTo(defenderTroop.getCell())) {
                throw new Exception("can not attack to this target");
            }
        } else if (attackerTroop.getCard().getAttackType() == AttackType.RANGED) {
            if (attackerTroop.getCell().isNextTo(defenderTroop.getCell()) ||
                    attackerTroop.getCell().manhattanDistance(defenderTroop.getCell()) > attackerTroop.getCard().getRange()) {
                throw new Exception("can not attack to this target");
            }
        } else { // HYBRID
            if (attackerTroop.getCell().manhattanDistance(defenderTroop.getCell()) > attackerTroop.getCard().getRange()) {
                throw new Exception("can not attack to this target");
            }
        }
    }

    private void damageFromAllAttackers(Troop defenderTroop, Troop[] attackerTroops) {
        for (Troop attackerTroop : attackerTroops) {
            if (defenderTroop.canGiveBadEffect() &&
                    (defenderTroop.canBeAttackedFromWeakerOnes() || attackerTroop.getCurrentAp() > defenderTroop.getCurrentAp())
            ) {
                damage(attackerTroop, defenderTroop);

                attackerTroop.setCanAttack(false);
                applyOnAttackSpells(attackerTroop, defenderTroop);
            }
        }
    }

    public abstract void finishCheck();

    private void applySpell(Spell spell, TargetData target) {
        spell.setLastTurnUsed(turnNumber);
        Buff buff = new Buff(spell.getAction(), target);
        buffs.add(buff);
        applyBuff(buff);
    }

    private void applyBuff(Buff buff) {
        TargetData target = buff.getTarget();
        if (haveDelay(buff)) return;

        applyBuffOnCards(buff, target.getCards());
        applyBuffOnCellTroops(buff, target.getCells());
        applyBuffOnTroops(buff, target.getTroops());
        applyBuffOnPlayers(buff, target.getPlayers());

        decreaseDuration(buff);
    }

    private void applyBuffOnPlayers(Buff buff, ArrayList<Player> players) {
        SpellAction action = buff.getAction();
        for (Player player : players) {
            player.changeCurrentMP(action.getMpChange());
        }
    }

    private void decreaseDuration(Buff buff) {
        SpellAction action = buff.getAction();
        if (action.getDuration() > 0) {
            action.decreaseDuration();
        }
        if (action.getDuration() == 0) {
            buffs.remove(buff);
        }
    }

    private boolean haveDelay(Buff buff) {
        SpellAction action = buff.getAction();
        if (action.getDelay() > 0) {
            action.decreaseDelay();
            return true;
        }
        return false;
    }

    private void applyBuffOnCards(Buff buff, ArrayList<Card> cards) {
        SpellAction action = buff.getAction();
        for (Card card : cards) {
            if (action.isAddSpell()) {
                card.addSpell(action.getCarryingSpell());
            }
        }
    }

    private void applyBuffOnCellTroops(Buff buff, ArrayList<Cell> cells) {
        ArrayList<Troop> inCellTroops = getInCellTargetTroops(cells);
        Buff troopBuff = new Buff(
                buff.getAction().makeCopyAction(1, 0), new TargetData(inCellTroops)
        );
        buffs.add(troopBuff);
        applyBuffOnTroops(troopBuff, inCellTroops);
    }

    private void applyBuffOnTroops(Buff buff, ArrayList<Troop> targetTroops) {
        SpellAction action = buff.getAction();
        for (Troop troop : targetTroops) {
            if (!(buff.isPositive() || troop.canGiveBadEffect())) continue;

            troop.changeEnemyHit(action.getEnemyHitChanges());
            troop.changeCurrentAp(action.getApChange());
            if (!action.isPoison() || troop.canGetPoison()) {
                troop.changeCurrentHp(action.getHpChange());
                if (troop.getCurrentHp() <= 0) {
                    killTroop(troop);
                }
            }
            if (action.isMakeStun() && troop.canGetStun()) {
                troop.setCanMove(false);
            }
            if (action.isMakeDisarm() && troop.canGetDisarm()) {
                troop.setDisarm(true);
            }
            if (action.isNoDisarm()) {
                troop.setCantGetDisarm(true);
            }
            if (action.isNoPoison()) {
                troop.setCantGetPoison(true);
            }
            if (action.isNoStun()) {
                troop.setCantGetStun(true);
            }
            if (action.isNoBadEffect()) {
                troop.setDontGiveBadEffect(true);
            }
            if (action.isNoAttackFromWeakerOnes()) {
                troop.setNoAttackFromWeakerOnes(true);
            }
            if (action.isDisableHolyBuff()) {
                troop.setDisableHolyBuff(true);
            }
            if (action.isKillsTarget()) {
                killTroop(troop);
            }
            if (action.getRemoveBuffs() > 0) {
                removePositiveBuffs(troop);
            }
            if (action.getRemoveBuffs() < 0) {
                removeNegativeBuffs(troop);
            }
        }
    }

    private void removePositiveBuffs(Troop troop) {
        for (Buff buff : buffs) {
            if (buff.isPositive() && buff.getAction().getDuration() >= 0) {
                buff.getTarget().getTroops().remove(troop);
            }
        }
    }

    private void removeNegativeBuffs(Troop troop) {
        for (Buff buff : buffs) {
            if (!buff.isPositive() && buff.getAction().getDuration() >= 0) {
                buff.getTarget().getTroops().remove(troop);
            }
        }
    }

    private void killTroop(Troop troop) {
        applyOnDeathSpells(troop);
        if (troop.getPlayerNumber() == 1) {
            playerOne.killTroop(troop);
            gameMap.removeTroop(playerOne, troop);
        } else if (troop.getPlayerNumber() == 2) {
            playerTwo.killTroop(troop);
            gameMap.removeTroop(playerTwo, troop);
        }
    }

    private void applyOnDeathSpells(Troop troop) {
        for (Spell spell : troop.getCard().getSpells()) {
            if (spell.getAvailabilityType().isOnDefend())
                applySpell(
                        spell,
                        detectTarget(spell, troop.getCell(), gameMap.getCell(0, 0), getOtherTurnPlayer().getHero().getCell())
                );
        }
    }

    private ArrayList<Troop> getInCellTargetTroops(ArrayList<Cell> cells) {
        ArrayList<Troop> inCellTroops = new ArrayList<>();
        for (Cell cell : cells) {
            Troop troop = playerOne.getTroop(cell);
            if (troop == null) {
                troop = playerTwo.getTroop(cell);
            }
            if (troop != null) {
                inCellTroops.add(troop);
            }
        }
        return inCellTroops;
    }

    private TargetData detectTarget(Spell spell, Cell cardCell, Cell clickCell, Cell heroCell) {
        TargetData targetData = new TargetData();
        Player player = getCurrentTurnPlayer();
        if (spell.getTarget().getCardType().isPlayer()) {
            targetData.getPlayers().add(player);
            return targetData;
        }
        Position centerPosition;
        if (spell.getTarget().isRelatedToCardOwnerPosition()) {
            centerPosition = new Position(cardCell);
        } else if (spell.getTarget().isForAroundOwnHero()) {
            centerPosition = new Position(heroCell);
        } else {
            centerPosition = new Position(clickCell);
        }
        ArrayList<Cell> targetCells = detectCells(centerPosition, spell.getTarget().getDimensions());
        detectTargets(spell, targetData, player, targetCells);
        return targetData;
    }

    private void detectTargets(Spell spell, TargetData targetData, Player player, ArrayList<Cell> targetCells) {
        for (Cell cell : targetCells) {
            if (spell.getTarget().getCardType().isCell()) {
                targetData.getCells().add(cell);
            }
            if (spell.getTarget().getCardType().isHero()) {
                Troop troop = player.getTroop(cell);
                if (troop == null) {
                    troop = getOtherTurnPlayer().getTroop(cell);
                }
                if (troop != null) {
                    if (troop.getCard().getType() == CardType.HERO) {
                        targetData.getTroops().add(troop);
                    }
                }
            }
            if (spell.getTarget().getCardType().isMinion()) {
                Troop troop = player.getTroop(cell);
                if (troop == null) {
                    troop = getOtherTurnPlayer().getTroop(cell);
                }
                if (troop != null) {
                    if (troop.getCard().getType() == CardType.MINION) {
                        targetData.getTroops().add(troop);
                    }
                }
            }
        }
    }

    private ArrayList<Cell> detectCells(Position centerPosition, Position dimensions) {
        int firstRow = centerPosition.getRow() - (dimensions.getRow() - 1) / 2;
        int lastRow = centerPosition.getRow() + dimensions.getRow();
        int firstColumn = centerPosition.getColumn() - (dimensions.getColumn() - 1) / 2;
        int lastColumn = centerPosition.getColumn() + dimensions.getColumn();
        if (firstRow < 0)
            firstRow = 0;
        if (lastRow > GameMap.getRowNumber())
            lastRow = GameMap.getRowNumber();
        if (firstColumn < 0)
            firstColumn = 0;
        if (lastColumn > GameMap.getColumnNumber())
            lastColumn = GameMap.getColumnNumber();
        ArrayList<Cell> targetCells = new ArrayList<>();
        for (int i = firstRow; i <= lastRow; i++) {
            for (int j = firstColumn; j <= lastColumn; j++) {
                targetCells.add(gameMap.getCells()[i][j]);
            }
        }
        return targetCells;
    }
}