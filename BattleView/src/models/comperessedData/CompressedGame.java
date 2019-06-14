package models.comperessedData;

import models.card.CardType;
import models.game.GameType;

public class  CompressedGame {
    private CompressedPlayer playerOne;
    private CompressedPlayer playerTwo;
    private CompressedGameMap gameMap;
    private int turnNumber;
    private GameType gameType;

    //just for testing BattleView
    public CompressedGame(CompressedPlayer playerOne, CompressedPlayer playerTwo, CompressedGameMap gameMap, int turnNumber, GameType gameType) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameMap = gameMap;
        this.turnNumber = turnNumber;
        this.gameType = gameType;
    }

    public void moveCardToHand() {
        CompressedPlayer player = getCurrentTurnPlayer();
        player.addNextCardToHand();
        player.removeCardFromNext();
    }

    public void moveCardToNext(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();
        player.addCardToNext(card);
    }

    public void moveCardToMap(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();
        player.removeCardFromHand(card.getCardId());
    }

    public void moveCardToGraveYard(CompressedCard card) {
        CompressedPlayer player;
        if (card.getType() == CardType.HERO || card.getType() == CardType.MINION) {
            CompressedTroop troop = gameMap.getTroop(card.getCardId());
            if (troop == null) {
                System.out.println("Client Game Error!");
            } else {
                player = getPlayer(troop.getPlayerNumber());
                player.removeTroop(card.getCardId());
                player.addCardToGraveYard(card);
                gameMap.killTroop(card.getCardId());
            }
        } else {
            player = getCurrentTurnPlayer();
            player.removeCardFromHand(card.getCardId());
            player.removeCardFromCollectedItems(card.getCardId());
            player.addCardToGraveYard(card);
        }
    }

    public void moveCardToCollectedItems(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();
        gameMap.removeItem(card.getCardId());
        player.addCardToCollectedItems(card);
    }

    public void troopUpdate(CompressedTroop troop) {
        CompressedPlayer player;
        player = getPlayer(troop.getPlayerNumber());
        player.troopUpdate(troop);
        gameMap.updateTroop(troop);
    }

    public void gameUpdate(int turnNumber, int player1CurrentMP, int player1NumberOfCollectedFlags,
                           int player2CurrentMP, int player2NumberOfCollectedFlags) {
        this.turnNumber = turnNumber;
        playerOne.setCurrentMP(player1CurrentMP,turnNumber);
        playerOne.setNumberOfCollectedFlags(player1NumberOfCollectedFlags);
        playerTwo.setCurrentMP(player2CurrentMP,turnNumber);
        playerTwo.setNumberOfCollectedFlags(player2NumberOfCollectedFlags);
    }

    public CompressedPlayer getPlayerOne() {
        return playerOne;
    }

    public CompressedPlayer getPlayerTwo() {
        return playerTwo;
    }

    public CompressedGameMap getGameMap() {
        return gameMap;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public GameType getGameType() {
        return gameType;
    }

    public CompressedPlayer getCurrentTurnPlayer() {
        return getPlayer(turnNumber % 2);
    }

    public CompressedPlayer getOtherTurnPlayer() {
        return getPlayer(turnNumber % 2 + 1);
    }

    private CompressedPlayer getPlayer(int number) {
        if (number == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }
}
