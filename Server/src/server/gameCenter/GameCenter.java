package server.gameCenter;

import server.Server;
import server.clientPortal.models.message.Message;
import server.detaCenter.DataCenter;
import server.detaCenter.models.account.Account;
import server.detaCenter.models.account.MatchHistory;
import server.detaCenter.models.card.Deck;
import server.exceptions.ClientException;
import server.exceptions.LogicException;
import server.gameCenter.models.game.*;
import server.gameCenter.models.map.GameMap;

import java.util.HashMap;

public class GameCenter extends Thread {
    private static GameCenter ourInstance = new GameCenter();

    public static GameCenter getInstance() {
        return ourInstance;
    }

    private HashMap<Account, Game> onlineGames = new HashMap<>();//Account -> Game

    private GameCenter() {
    }

    @Override
    public void run() {
        Server.getInstance().serverPrint("Starting GameCenter...");

    }

    private Game getGame(String clientName) throws ClientException {
        Account account = DataCenter.getInstance().getClients().get(clientName);
        if (account == null) {
            throw new ClientException("your client hasn't logged in!");
        }
        Game game = onlineGames.get(account);
        if (game == null) {
            throw new ClientException("you don't have online game!");
        }
        return game;
    }

    private void checkOpponentAccountValidation(Message message) throws LogicException {
        if (message.getNewGameFields().getOpponentUsername() == null) {
            throw new ClientException("invalid opponentAccount!");
        }
        Account opponentAccount = DataCenter.getInstance().getAccount(message.getNewGameFields().getOpponentUsername());
        if (opponentAccount == null) {
            throw new ClientException("invalid opponent username!");
        }
        if (DataCenter.getInstance().getAccounts().get(opponentAccount) == null) {
            throw new ClientException("opponentAccount has not logged in!");
        }
        if (onlineGames.get(opponentAccount) != null) {
            throw new ClientException("opponentAccount has online game!");
        }
        //TODO:Validation
    }

    public void newDeckGame(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        Account myAccount = DataCenter.getInstance().getClients().get(message.getSender());
        if (!myAccount.hasValidMainDeck()) {
            throw new ClientException("you don't have valid main deck!");
        }
        if (onlineGames.get(myAccount) != null) {
            throw new ClientException("you have online game!");
        }
        Deck deck = new Deck(myAccount.getDeck(message.getNewGameFields().getCustomDeckName()));
        deck.makeCustomGameDeck();
        if (deck == null || !deck.isValid()) {
            throw new ClientException("selected deck is not valid");
        }
        Game game = null;
        GameMap gameMap = new GameMap(DataCenter.getInstance().getCollectibleItems(), message.getNewGameFields().getNumberOfFlags(), DataCenter.getInstance().getOriginalFlag());
        switch (message.getNewGameFields().getGameType()) {
            case KILL_HERO:
                game = new KillHeroBattle(myAccount, deck, gameMap);
                break;
            case A_FLAG:
                game = new SingleFlagBattle(myAccount, deck, gameMap);
                break;
            case SOME_FLAG:
                game = new MultiFlagBattle(myAccount, deck, gameMap, message.getNewGameFields().getNumberOfFlags());
                break;
        }
        game.setReward(Game.getDefaultReward());
        onlineGames.put(myAccount, game);
        Server.getInstance().addToSendingMessages(Message.makeGameCopyMessage
                (Server.getInstance().serverName, message.getSender(), game, 0));
        game.startGame();
    }

    public void newStoryGame(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        Account myAccount = DataCenter.getInstance().getClients().get(message.getSender());
        if (!myAccount.hasValidMainDeck()) {
            throw new ClientException("you don't have valid main deck!");
        }
        if (onlineGames.get(myAccount) != null) {
            throw new ClientException("you have online game!");
        }
        Game game = null;
        Story story = DataCenter.getInstance().getStories().get(message.getNewGameFields().getStage());
        GameMap gameMap = new GameMap(DataCenter.getInstance().getCollectibleItems(), story.getNumberOfFlags(), DataCenter.getInstance().getOriginalFlag());
        switch (story.getGameType()) {
            case KILL_HERO:
                game = new KillHeroBattle(myAccount, story.getDeck(), gameMap);
                break;
            case A_FLAG:
                game = new SingleFlagBattle(myAccount, story.getDeck(), gameMap);
                break;
            case SOME_FLAG:
                game = new MultiFlagBattle(myAccount, story.getDeck(), gameMap, story.getNumberOfFlags());
                break;
        }
        game.setReward(story.getReward());
        onlineGames.put(myAccount, game);
        Server.getInstance().addToSendingMessages(Message.makeGameCopyMessage
                (Server.getInstance().serverName, message.getSender(), game, 0));
        game.startGame();
    }

    public void newMultiplayerGame(Message message) throws LogicException {
        DataCenter.getInstance().loginCheck(message);
        checkOpponentAccountValidation(message);
        Account myAccount = DataCenter.getInstance().getClients().get(message.getSender());
        Account opponentAccount = DataCenter.getInstance().getAccount(message.getNewGameFields().getOpponentUsername());
        if (!myAccount.hasValidMainDeck()) {
            throw new ClientException("you don't have valid main deck!");
        }
        if (opponentAccount == null || !opponentAccount.hasValidMainDeck()) {
            throw new ClientException("opponent's main deck is not valid");
        }
        if (onlineGames.get(myAccount) != null) {
            throw new ClientException("you have online game!");
        }
        if (onlineGames.get(opponentAccount) != null) {
            throw new ClientException("opponent has online game!");
        }
        /*accounts.replace(opponentAccount, onlineClients.get(1).getClientName());
        clients.replace(onlineClients.get(1).getClientName(), opponentAccount);*/
        Game game = null;
        GameMap gameMap = new GameMap(DataCenter.getInstance().getCollectibleItems(), message.getNewGameFields().getNumberOfFlags(), DataCenter.getInstance().getOriginalFlag());
        if (message.getNewGameFields().getGameType() == null) {
            throw new ClientException("invalid gameType!");
        }
        switch (message.getNewGameFields().getGameType()) {
            case KILL_HERO:
                game = new KillHeroBattle(myAccount, opponentAccount, gameMap);
                break;
            case A_FLAG:
                game = new SingleFlagBattle(myAccount, opponentAccount, gameMap);
                break;
            case SOME_FLAG:
                game = new MultiFlagBattle(myAccount, opponentAccount, gameMap, message.getNewGameFields().getNumberOfFlags());
                break;
        }
        game.setReward(Game.getDefaultReward());
        onlineGames.put(myAccount, game);
        onlineGames.put(opponentAccount, game);
        Server.getInstance().addToSendingMessages(Message.makeGameCopyMessage
                (Server.getInstance().serverName, message.getSender(), game, 0));
        Server.getInstance().addToSendingMessages(Message.makeGameCopyMessage
                (Server.getInstance().serverName, DataCenter.getInstance().getAccounts().get(opponentAccount), game, 0));
        game.startGame();
    }

    private void removeGame(Game game) {
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            Account account1 = DataCenter.getInstance().getAccount(game.getPlayerOne().getUserName());
            if (account1 != null) {
                onlineGames.remove(account1);
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            Account account2 = DataCenter.getInstance().getAccount(game.getPlayerTwo().getUserName());
            if (account2 != null) {
                onlineGames.remove(account2);
                //DataCenter.getInstance().getAccounts().replace(account2, null);
            }
        }
        // DataCenter.getInstance().getClients().replace(onlineClients.get(1).getClientName(), null);
    }

    public void insertCard(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.insert(DataCenter.getInstance().getClients().get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());
            Server.getInstance().sendGameUpdateMessage(game);
        } finally {
            checkGameFinish(game);
        }
    }

    public void attack(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.attack(DataCenter.getInstance().getClients().get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getOpponentCardId());
        } finally {
            checkGameFinish(game);
        }
    }

    public void combo(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.comboAttack(DataCenter.getInstance().getClients().get(message.getSender()).getUsername(), message.getOtherFields().getMyCardIds(), message.getOtherFields().getOpponentCardId());

        } finally {
            checkGameFinish(game);

        }
    }

    public void useSpecialPower(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.useSpecialPower(DataCenter.getInstance().getClients().get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());
            Server.getInstance().sendGameUpdateMessage(game);
        } finally {
            checkGameFinish(game);

        }
    }

    public void moveTroop(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.moveTroop(DataCenter.getInstance().getClients().get(message.getSender()).getUsername(), message.getOtherFields().getMyCardId(), message.getOtherFields().getPosition());

        } finally {
            checkGameFinish(game);

        }
    }

    public void endTurn(Message message) throws LogicException {
        Game game = getGame(message.getSender());
        try {
            game.changeTurn(DataCenter.getInstance().getClients().get(message.getSender()).getUsername());

        } finally {
            checkGameFinish(game);

        }
    }

    private void checkGameFinish(Game game) {
        if (game.finishCheck()) {
            finish(game);
        }
    }

    private void finish(Game game) {
        MatchHistory playerOneHistory = game.getPlayerOne().getMatchHistory();
        MatchHistory playerTwoHistory = game.getPlayerTwo().getMatchHistory();
        if (!game.getPlayerOne().getUserName().equalsIgnoreCase("AI")) {
            Account account = DataCenter.getInstance().getAccount(game.getPlayerOne().getUserName());
            if (account == null)
                Server.getInstance().serverPrint("Error");
            else {
                account.addMatchHistory(playerOneHistory, game.getReward());
                DataCenter.getInstance().saveAccount(account);
            }
        }
        if (!game.getPlayerTwo().getUserName().equalsIgnoreCase("AI")) {
            Account account = DataCenter.getInstance().getAccount(game.getPlayerTwo().getUserName());
            if (account == null)
                Server.getInstance().serverPrint("Error");
            else {
                account.addMatchHistory(playerTwoHistory, game.getReward());
                DataCenter.getInstance().saveAccount(account);
            }
        }
        Server.getInstance().sendGameFinishMessages(game);
        removeGame(game);
    }

    public void forceFinishGame(Message message) throws LogicException {
        Game game = getGame(message.getSender());

        if (game == null) {
            Server.getInstance().serverPrint("Error forceGameFinish!");
            return;
        }
        DataCenter.getInstance().loginCheck(message);
        String username = DataCenter.getInstance().getClients().get(message.getSender()).getUsername();

        game.forceFinish(username);
        finish(game);


    }
}
