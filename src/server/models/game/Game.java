package server.models.game;

import server.models.account.Account;
import server.models.card.spell.Spell;
import server.models.map.Cell;
import server.models.map.GameMap;
import server.models.map.Position;

import java.util.ArrayList;

public abstract class Game {
    private GameType gameType;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<CellEffect> cellEffects;
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
        return turnNumber % 2 == 1 && username.equalsIgnoreCase(playerOne.getUserName());
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

    }

    private TargetData detectTarget(Spell spell, Cell cardCell, Cell clickCell, Cell heroCell) {
        TargetData targetData = new TargetData();
        if (spell.getTarget().getCardType().isPlayer()) {
            if (turnNumber % 2 == 1)
                targetData.getPlayers().add(playerOne);
            else
                targetData.getPlayers().add(playerTwo);
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
        for (Cell cell : targetCells) {

        }
        return null;
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