package controller;

import models.Constants;
import models.card.AttackType;
import models.card.CardType;
import models.comperessedData.CompressedCard;
import models.comperessedData.CompressedGame;
import models.comperessedData.CompressedTroop;
import models.exceptions.InputException;
import models.game.GameActions;
import models.game.availableActions.AvailableActions;
import models.game.map.Position;
import models.message.CardAnimation;
import models.message.GameAnimations;
import models.message.Message;
import view.BattleView.BattleScene;

import java.util.ArrayList;


public class GameController implements GameActions {
    private static GameController ourInstance;
    private CompressedGame currentGame;
    private AvailableActions availableActions = new AvailableActions();
    BattleScene battleScene;

    private GameController() {
    }

    public static GameController getInstance() {
        if (ourInstance == null) {
            ourInstance = new GameController();
        }
        return ourInstance;
    }

    public void calculateAvailableActions() {
        availableActions.calculate(currentGame);
    }

    public CompressedGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CompressedGame currentGame) {
        this.currentGame = currentGame;
        currentGame.getPlayerOne().setTroops(currentGame.getGameMap().getPlayerTroop(1));
        currentGame.getPlayerTwo().setTroops(currentGame.getGameMap().getPlayerTroop(2));
        int playerNumber = getPlayerNumber(currentGame);
        try {
            battleScene = new BattleScene(this, currentGame, playerNumber);
            battleScene.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPlayerNumber(CompressedGame currentGame) {
        int playerNumber = 1;
        if (currentGame.getPlayerTwo().getUserName().equals(Client.getInstance().getAccount().getUsername()))
            playerNumber = 2;
        return playerNumber;
    }

    @Override
    public void attack(CompressedTroop attackerTroop, CompressedTroop defenderTroop) {
        try {
            if (!attackerTroop.canAttack())
                throw new InputException("you can not attack");
            if (attackerTroop.getCard().getAttackType() == AttackType.MELEE) {
                if (!attackerTroop.getPosition().isNextTo(defenderTroop.getPosition())) {
                    throw new InputException("you can not attack to this target");
                }
            } else if (attackerTroop.getCard().getAttackType() == AttackType.RANGED) {
                if (attackerTroop.getPosition().isNextTo(defenderTroop.getPosition()) ||
                        attackerTroop.getPosition().manhattanDistance(defenderTroop.getPosition()) > attackerTroop.getCard().getRange()) {
                    throw new InputException("you can not attack to this target");
                }
            } else { // HYBRID
                if (attackerTroop.getPosition().manhattanDistance(defenderTroop.getPosition()) > attackerTroop.getCard().getRange()) {
                    throw new InputException("you can not attack to this target");
                }
            }

            Message message = Message.makeAttackMessage(
                    Client.getInstance().getClientName(), Constants.SERVER_NAME, attackerTroop.getCard().getCardId(), defenderTroop.getCard().getCardId(), 0
            );
            Client.getInstance().addToSendingMessagesAndSend(message);

        } catch (Exception ignored) {
        }

    }

    @Override
    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop troop) {

    }

    @Override
    public void move(CompressedTroop selectedTroop, int row, int column) {
        try {
            Position target = new Position(row, column);
            if (!selectedTroop.canMove()) {
                throw new InputException("troop can not move");
            }

            if (currentGame.getGameMap().getTroop(new Position(row, column)) != null) {
                throw new InputException("cell is not empty");
            }

            if (selectedTroop.getPosition().manhattanDistance(row, column) > 2) {
                throw new InputException("too far to go");
            }

            Message message = Message.makeMoveTroopMessage(
                    Client.getInstance().getClientName(), Constants.SERVER_NAME, selectedTroop.getCard().getCardId(), target, 0);
            Client.getInstance().addToSendingMessagesAndSend(message);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void endTurn() {
        Client.getInstance().addToSendingMessagesAndSend(Message.makeEndTurnMessage(Client.getInstance().getClientName(), Constants.SERVER_NAME, 0));
    }

    @Override
    public void insert(CompressedCard card, int row, int column) {
        if (validatePositionForInsert(card, row, column))
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeInsertMessage(
                            Client.getInstance().getClientName(), Constants.SERVER_NAME, card.getCardId(), new Position(row, column), 0
                    )
            );
    }

    private boolean validatePositionForInsert(CompressedCard card, int row, int column) {
        return (card.getType() == CardType.SPELL || card.getType() == CardType.COLLECTIBLE_ITEM) || (currentGame.getGameMap().getTroop(new Position(row, column)) == null);
    }

    @Override
    public void useSpecialPower(int row, int column) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeUseSpecialPowerMessage(
                        Client.getInstance().getClientName(), Constants.SERVER_NAME, currentGame.getCurrentTurnPlayer().getHero().getCard().getCardId(), new Position(row, column), 0
                )
        );
    }

    public void showAnimation(GameAnimations gameAnimations) {
        for (CardAnimation cardAnimation :
                gameAnimations.getAttackers()) {
            battleScene.attack(cardAnimation.getID(), cardAnimation.getPosition());
        }
        for (CardAnimation cardAnimation :
                gameAnimations.getDefenders()) {
            battleScene.defend(cardAnimation.getID(), cardAnimation.getPosition());
        }
        for (CardAnimation cardAnimation :
                gameAnimations.getSpellAnimations()) {
            battleScene.spell(cardAnimation.getID(), cardAnimation.getPosition());
        }
    }
}
