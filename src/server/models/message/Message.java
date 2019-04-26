package server.models.message;

import com.google.gson.Gson;
import server.models.account.Account;
import server.models.account.AccountInfo;
import server.models.account.Collection;
import server.models.account.TempAccount;
import server.models.card.Deck;
import server.models.card.spell.DeckInfo;
import server.models.game.Game;
import server.models.game.GameType;
import server.models.game.Story;
import server.models.map.Position;

public class Message {
    private MessageType messageType;
    //serverName || clientName
    private String sender;
    private String receiver;
    private int messageId;

    private Game game;
    private Collection shopCards;
    private TempAccount account;
    private DeckInfo[] customDecks;
    private AccountInfo[] leaderBoard;
    private DeckInfo[] stories;
    private Position[] positions;
    private String exceptionString;
    private String cardId;
    private String[] cardIds;
    private String spellId;
    private int turnNum;
    private int numberOfFlags;
    private String cardName;
    private int newValue;
    private int stage;
    private Position position;
    private String userName, passWord;
    private String deckName;
    private String opponentUserName;
    private GameType gameType;
    private String sudoCommand;

    private Message(String sender, String receiver, int messageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageId = messageId;
    }

    public static Message convertJsonToMessage(String messageJson) {
        return new Gson().fromJson(messageJson, Message.class);
    }

    public static Message makeGameCopyMessage(String sender, String receiver, Game game, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.game = game;
        message.messageType = MessageType.GAME_COPY;
        return message;
    }

    public static Message makeOriginalCardsCopyMessage(String sender, String receiver, Collection shopCards, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.shopCards = shopCards;
        message.messageType = MessageType.ORIGINAL_CARDS_COPY;
        return message;
    }

    public static Message makeAccountCopyMessage(String sender, String receiver, Account account, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.account = new TempAccount(account);
        message.messageType = MessageType.ACCOUNT_COPY;
        return message;
    }

    public static Message makeCustomDecksCopyMessage(String sender, String receiver, Deck[] customDecks, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.customDecks = new DeckInfo[customDecks.length];
        for (int i = 0; i < customDecks.length; i++) {
            message.customDecks[i] = new DeckInfo(customDecks[i]);
        }
        message.messageType = MessageType.CUSTOM_DECKS_COPY;
        return message;
    }

    public static Message makeLeaderBoardCopyMessage(String sender, String receiver, Account[] leaderBoard, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.leaderBoard = new AccountInfo[leaderBoard.length];
        for (int i = 0; i < leaderBoard.length; i++) {
            message.leaderBoard[i] = new AccountInfo(leaderBoard[i]);
        }
        message.messageType = MessageType.LEADERBOARD_COPY;
        return message;
    }

    public static Message makeStoriesCopyMessage(String sender, String receiver, Story[] stories, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.stories = new DeckInfo[stories.length];
        for (int i = 0; i < stories.length; i++) {
            message.stories[i] = new DeckInfo(stories[i]);
        }        message.messageType = MessageType.STORIES_COPY;
        return message;
    }

    public static Message makePositionsCopyMessage(String sender, String receiver, Position[] positions, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.positions = positions;
        message.messageType = MessageType.POSITIONS_COPY;
        return message;
    }

    public static Message makeTroopPositionMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.position = position;
        message.messageType = MessageType.MOVE_TROOP;
        return message;
    }

    public static Message makeToHandMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.TO_HAND;
        return message;
    }

    public static Message makeToNextMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.TO_NEXT;
        return message;
    }

    public static Message makeToGraveYardMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.TO_GRAVEYARD;
        return message;
    }

    public static Message makeToCollectedItemsMessage(String sender, String receiver, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.TO_COLLECTED_CARDS;
        return message;
    }

    public static Message makeToMapMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.position = position;
        message.messageType = MessageType.TO_MAP;
        return message;
    }

    public static Message makeChangeAPMessage(String sender, String receiver, String cardId, int newValue, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.newValue = newValue;
        message.messageType = MessageType.TROOP_AP;
        return message;
    }

    public static Message makeChangeHPMessage(String sender, String receiver, String cardId, int newValue, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.newValue = newValue;
        message.messageType = MessageType.TROOP_HP;
        return message;
    }

    public static Message makeExceptionMessage(String sender, String receiver, String exceptionString, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.exceptionString = exceptionString;
        message.messageType = MessageType.SEND_EXCEPTION;
        return message;
    }

    public static Message makeEndTurnMessage(String sender, String receiver, int turnNum, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.turnNum = turnNum;
        message.messageType = MessageType.END_TURN;
        return message;
    }

    public static Message makeGetOriginalCardsMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.GET_ORIGINAL_CARDS;
        return message;
    }

    public static Message makeGetLeaderBoardMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.GET_LEADERBOARD;
        return message;
    }

    public static Message makeSaveAccountMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.SAVE_CHANGES;
        return message;
    }

    public static Message makeCreateDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.messageType = MessageType.CREATE_DECK;
        return message;
    }

    public static Message makeRemoveDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.messageType = MessageType.REMOVE_DECK;
        return message;
    }

    public static Message makeNewMultiPlayerGameMessage(String sender, String receiver,GameType gameType, String opponentAccount, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.opponentUserName =opponentAccount;
        message.gameType=gameType;
        message.messageType = MessageType.NEW_GAME;
        return message;
    }

    public static Message makeAddCardToDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.ADD_TO_DECK;
        return message;
    }

    public static Message makeRemoveCardFromDeckMessage(String sender, String receiver, String deckName, String cardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.messageType = MessageType.REMOVE_FROM_DECK;
        return message;
    }

    public static Message makeSelectDeckMessage(String sender, String receiver, String deckName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.deckName = deckName;
        message.messageType = MessageType.SELECT_DECK;
        return message;
    }

    public static Message makeBuyCardMessage(String sender, String receiver, String cardName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardName = cardName;
        message.messageType = MessageType.BUY_CARD;
        return message;
    }

    public static Message makeSellCardMessage(String sender, String receiver, String cardName, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardName = cardName;
        message.messageType = MessageType.SELL_CARD;
        return message;
    }

    public static Message makeEndTurnMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.END_TURN;
        return message;
    }

    public static Message makeInsertMessage(String sender, String receiver, String cardId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.position = position;
        message.messageType = MessageType.INSERT;
        return message;
    }

    public static Message makeAttackMessage(String sender, String receiver, String myCardId, String opponentCardId, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[2];
        message.cardIds[0] = myCardId;
        message.cardIds[1] = opponentCardId;
        message.messageType = MessageType.ATTACK;
        return message;
    }

    public static Message makeComboAttackMessage(String sender, String receiver, int messageId, String... opponentAndMyCardIds) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = opponentAndMyCardIds;
        message.messageType = MessageType.COMBO;
        return message;
    }

    public static Message makeUseSpellMessage(String sender, String receiver, String cardId, String spellId, Position position, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.cardIds = new String[1];
        message.cardIds[0] = cardId;
        message.spellId = spellId;
        message.position = position;
        message.messageType = MessageType.USE_SPELL;
        return message;
    }

    public static Message makeRegisterMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.userName = userName;
        message.passWord = passWord;
        message.messageType = MessageType.REGISTER;
        return message;
    }

    public static Message makeLogInMessage(String sender, String receiver, String userName, String passWord, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.userName = userName;
        message.passWord = passWord;
        message.messageType = MessageType.LOG_IN;
        return message;
    }

    public static Message makeLogOutMessage(String sender, String receiver, int messageId) {
        Message message = new Message(sender, receiver, messageId);
        message.messageType = MessageType.LOG_OUT;
        return message;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getMessageId() {
        return messageId;
    }

    public Game getGame() {
        return game;
    }

    public Collection getShopCards() {
        return shopCards;
    }

    public TempAccount getAccount() {
        return account;
    }

    public AccountInfo[] getLeaderBoard() {
        return leaderBoard;
    }

    public DeckInfo[] getCustomDecks() {
        return customDecks;
    }

    public DeckInfo[] getStories() {
        return stories;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getStage() {
        return stage;
    }

    public Position[] getPositions() {
        return positions;
    }

    public String getExceptionString() {
        return exceptionString;
    }

    public String getCardId() {
        return cardId;
    }

    public String[] getCardIds() {
        return cardIds;
    }

    public String getSpellId() {
        return spellId;
    }

    public int getTurnNum() {
        return turnNum;
    }

    public String getCardName() {
        return cardName;
    }

    public int getNewValue() {
        return newValue;
    }

    public Position getPosition() {
        return position;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getDeckName() {
        return deckName;
    }

    public String getOpponentUserName() {
        return opponentUserName;
    }

    public GameType getGameType() {
        return gameType;
    }

    public String getSudoCommand() {
        return sudoCommand;
    }
}
