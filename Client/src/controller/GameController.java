package controller;

import javafx.application.Platform;
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
import models.message.SpellAnimation;
import view.BattleView.BattleScene;

import java.util.ArrayList;

import static models.Constants.SERVER_NAME;


public class GameController implements GameActions {
    private static GameController ourInstance;
    BattleScene battleScene;
    private CompressedGame currentGame;
    private AvailableActions availableActions = new AvailableActions();

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

    public AvailableActions getAvailableActions() {
        return availableActions;
    }

    public CompressedGame getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(CompressedGame currentGame) {
        this.currentGame = currentGame;
        currentGame.getPlayerOne().setTroops(currentGame.getGameMap().getPlayerTroop(1));
        currentGame.getPlayerTwo().setTroops(currentGame.getGameMap().getPlayerTroop(2));
        int playerNumber = getPlayerNumber(currentGame);
        Platform.runLater(() -> {
            try {
                battleScene = new BattleScene(this, currentGame, playerNumber, "battlemap6_middleground@2x");
                battleScene.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

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
                    Client.getInstance().getClientName(), SERVER_NAME, attackerTroop.getCard().getCardId(), defenderTroop.getCard().getCardId(), 0
            );
            Client.getInstance().addToSendingMessagesAndSend(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void comboAttack(ArrayList<CompressedTroop> comboTroops, CompressedTroop defenderTroop) {
        String[] cardIDs= new String[comboTroops.size()];
        int i = 0;
        try {
            for (CompressedTroop attackerTroop:
                 comboTroops) {
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
                cardIDs[i] = attackerTroop.getCard().getCardId();
                i++;
            }

            Client.getInstance().addToSendingMessagesAndSend(Message.makeComboAttackMessage(
                    Client.getInstance().getClientName(), SERVER_NAME,defenderTroop.getCard().getCardId(),cardIDs,0));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
                    Client.getInstance().getClientName(), SERVER_NAME, selectedTroop.getCard().getCardId(), target, 0);
            Client.getInstance().addToSendingMessagesAndSend(message);
        } catch (InputException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void endTurn() {
        Client.getInstance().addToSendingMessagesAndSend(Message.makeEndTurnMessage(Client.getInstance().getClientName(), SERVER_NAME, 0));
    }

    public void forceFinish() {
        Client.getInstance().addToSendingMessagesAndSend(Message.makeForceFinishGameMessage(Client.getInstance().getClientName(), SERVER_NAME,0));
    }

    @Override
    public void insert(CompressedCard card, int row, int column) {
        if (validatePositionForInsert(card, row, column))
            Client.getInstance().addToSendingMessagesAndSend(
                    Message.makeInsertMessage(
                            Client.getInstance().getClientName(), SERVER_NAME, card.getCardId(), new Position(row, column), 0
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
                        Client.getInstance().getClientName(), SERVER_NAME, currentGame.getCurrentTurnPlayer().getHero().getCard().getCardId(), new Position(row, column), 0
                )
        );
    }

    public void showAnimation(GameAnimations gameAnimations) {
        new Thread(() -> {
            for (SpellAnimation cardAnimation :
                    gameAnimations.getSpellAnimations()) {
                battleScene.spell(cardAnimation.getSpellID(), cardAnimation.getPosition());
            }
            for (CardAnimation cardAnimation :
                    gameAnimations.getAttacks()) {
                battleScene.attack(cardAnimation.getAttacker(), cardAnimation.getDefender());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {
            }
            for (CardAnimation cardAnimation :
                    gameAnimations.getCounterAttacks()) {
                battleScene.attack(cardAnimation.getAttacker(), cardAnimation.getDefender());
            }
        }).start();
    }

    public void sendChat(String text) {
        Client.getInstance().addToSendingMessagesAndSend(
                Message.makeChatMessage(
                        Client.getInstance().getClientName(), SERVER_NAME,
                        battleScene.getMyUserName(), battleScene.getOppUserName(), text, 0
                )
        );
    }
}
