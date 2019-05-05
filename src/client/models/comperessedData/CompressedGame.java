package client.models.comperessedData;

import client.models.card.CardType;
import client.models.game.GameType;

public class CompressedGame {
    private CompressedPlayer playerOne;
    private CompressedPlayer playerTwo;
    private CompressedGameMap gameMap;
    private int turnNumber;
    private GameType gameType;
    private boolean isFinished;


    public void moveCardToHand(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();//TODO:Ahmad Check
        player.removeCardFromNext(card.getCardId());
        player.addNextCardToHand();
    }

    public void moveCardToNext(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();//TODO:Ahmad Check
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
                if (troop.getPlayerNumber() == 1) {//TODO:Ahmad check
                    player = playerOne;
                } else {
                    player = playerTwo;
                }
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

    public void moveCardToCollecteds(CompressedCard card) {
        CompressedPlayer player = getCurrentTurnPlayer();
        gameMap.removeItem(card.getCardId());
        player.addCardToCollectedItems(card);
    }

    public void troopUpdate(CompressedTroop troop) {//check both + map flag
        CompressedPlayer player = null;
        if (troop.getPlayerNumber() == 1) {//TODO:Ahmad check
            player = playerOne;
        } else {
            player = playerTwo;
        }
        player.troopUpdate(troop);
        gameMap.updateTroop(troop);
    }

    public void gameUpdate(int turnNumber, int player1CurrentMP, int player1NumberOfCollectedFlags,
                           int player2CurrentMP, int player2NumberOfCollectedFlags) {
        this.turnNumber = turnNumber;
        playerOne.setCurrentMP(player1CurrentMP);
        playerOne.setNumberOfCollectedFlags(player2NumberOfCollectedFlags);
        playerTwo.setCurrentMP(player1CurrentMP);
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
        if (turnNumber % 2 == 1) {
            return playerOne;
        } else {
            return playerTwo;
        }
    }

    public CompressedPlayer getOtherTurnPlayer() {
        if (turnNumber % 2 == 1) {
            return playerTwo;
        } else {
            return playerOne;
        }
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished() {
        isFinished = true;
    }
}
