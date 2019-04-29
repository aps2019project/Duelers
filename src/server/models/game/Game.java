package server.models.game;

import server.models.account.Account;
import server.models.card.Card;
import server.models.card.spell.Spell;
import server.models.card.spell.SpellAction;
import server.models.map.Cell;
import server.models.map.GameMap;
import server.models.map.Position;

import java.util.ArrayList;

public abstract class Game {
    private GameType gameType;
    private Player playerOne;
    private Player playerTwo;
//    private ArrayList<CellEffect> cellEffects;
    private ArrayList<Buff> buffs;
    private GameMap gameMap;
    private int turnNumber;
    private int lastTurnChangingTime;
    private boolean finished = false;

    protected Game(GameType gameType, Account accountOne, Account accountTwo, GameMap gameMap) {
        this.gameType = gameType;
        this.gameMap = gameMap;
        this.playerOne = new Player(accountOne);
        this.playerTwo = new Player(accountTwo);
    }

    public String getUsernameOne() {
        return this.playerOne.getUserName();
    }

    public String getUsernameTwo() {
        return playerTwo.getUserName();
    }

    private boolean canCommand(String username) {
        if (turnNumber % 2 == 0 && username.equalsIgnoreCase(playerTwo.getUserName()))
            return true;
        if (turnNumber % 2 == 1 && username.equalsIgnoreCase(playerOne.getUserName()))
            return true;
        return false;
    }

    public void changeTurn(String username) {
        if (canCommand(username)) {
            turnNumber++;
            //TODO:Send Message
        }
    }

    public void move(String username, String cardId, Position position) {

    }

    public void insert(String username, String cardId, Position target) {

    }

    public void attack(String username, String attackerCardId, String defenderCardId) {

    }

    public void useSpell(String username, String CardId, String spellId, Position target) {

    }

    public abstract void finishCheck();

    public Troop[] getAttackableTroops(String cardId) {
        return new Troop[]{};
    }

    public Cell[] getSpellableCells(String cardId, String spellId) {
        return new Cell[]{};
    }

    public Cell[] getMovableCells(String cardId) {
        return new Cell[]{};
    }

    public boolean canAttack(String attackerCardId, String defenderCardId) {
        return false;
    }

    public boolean canSpell(String cardId, Position position) {
        return false;
    }

    public boolean canInsert(String cardId, Position position) {
        return false;
    }

    private void applySpell(Spell spell, TargetData target) {
        spell.setLastTurnUsed(turnNumber);
        Buff buff = new Buff(spell.getAction(), target);
        buffs.add(buff);
        applyBuff(buff);
    }

    private void applyBuff(Buff buff) {
        SpellAction action = buff.getAction();
        TargetData target = buff.getTarget();
        if (action.getDelay() > 0) {
            action.decreaseDelay();
            return;
        }
        for (Card card : target.getCards()) {
            if (action.isAddSpell()) {
                card.addSpell(action.getCarryingSpell());
            }
        }
        for (Troop troop : target.getTroops()) {

        }
        ArrayList<Troop> inCellTroops = getInCellTargetTroops(target.getCells());
        for (Player player: target.getPlayers()) {

        }
        action.decreaseDuration();
        if (action.getDuration() == 0) {
            buffs.remove(buff);
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
}