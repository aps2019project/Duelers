package models.message;

import models.account.Account;
import models.card.Card;
import models.card.Deck;
import models.game.Game;
import models.game.Story;
import models.map.Position;

public class Message {
    //TODO:Have ServerMessage & SystemMessage

    private MessageType messageType;
    private String sender;
    private String receiver;

    private Game game;
    private Card[] shopCards;
    private Account account;
    private Deck[] customDecks;
    private Account[] leaderBoard;
    private Story[] stories;
    private Position[] positions;
    private String exceptionString;
    private String[] cardIds;
    private String spellId;
    private int turnNum;
    private String cardName;
    private int newValue;
    private Position position;
    private String userName,passWord;
    private String deckName;

    private Message(String sender, String receiver){
        this.sender = sender;
        this.receiver = receiver;
    }

    public static Message gameCopyMessage(String sender,String receiver,Game game){
        Message message=new Message(sender,receiver);
        message.game=game;
        message.messageType=MessageType.GAME_COPY;
        return message;
    }

    public static Message shopCopyMessage(String sender,String receiver,Card[] shopCards){
        Message message=new Message(sender,receiver);
        message.shopCards=shopCards;
        message.messageType=MessageType.SHOP_COPY;
        return message;
    }

    public static Message accountCopyMessage(String sender,String receiver,Account account){
        Message message=new Message(sender,receiver);
        message.account=account;
        message.messageType=MessageType.ACCOUNT_COPY;
        return message;
    }

    public static Message customDecksCopyMessage(String sender,String receiver,Deck[] customDecks){
        Message message=new Message(sender,receiver);
        message.customDecks=customDecks;
        message.messageType=MessageType.CUSTOMDECKS_COPY;
        return message;
    }

    public static Message leaderBoardCopyMessage(String sender,String receiver,Account[] leaderBoard){
        Message message=new Message(sender,receiver);
        message.leaderBoard=leaderBoard;
        message.messageType=MessageType.LEADERBOARD_COPY;
        return message;
    }

    public static Message storiesCopyMessage(String sender,String receiver,Story[] stories){
        Message message=new Message(sender,receiver);
        message.stories=stories;
        message.messageType=MessageType.STORIES_COPY;
        return message;
    }

    public static Message positionsCopyMessage(String sender,String receiver,Position[] positions){
        Message message=new Message(sender,receiver);
        message.positions=positions;
        message.messageType=MessageType.POSITIONS_COPY;
        return message;
    }

    public static Message troopChangePosition(String sender,String receiver,String cardId,Position position){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.position=position;
        message.messageType=MessageType.MOVE_TROOP;
        return message;
    }

    public static Message toHand(String sender,String receiver,String cardId){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.TO_HAND;
        return message;
    }

    public static Message toNext(String sender,String receiver,String cardId){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.TO_NEXT;
        return message;
    }

    public static Message toGraveYard(String sender,String receiver,String cardId){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.TO_GRAVEYARD;
        return message;
    }

    public static Message toCollecteds(String sender,String receiver,String cardId){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.TO_COLLECTEDS;
        return message;
    }

    public static Message toMap(String sender,String receiver,String cardId,Position position){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.position=position;
        message.messageType=MessageType.TO_MAP;
        return message;
    }

    public static Message changeAP(String sender,String receiver,String cardId,int newValue){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.newValue=newValue;
        message.messageType=MessageType.TROOP_AP;
        return message;
    }

    public static Message changeHP(String sender,String receiver,String cardId,int newValue){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.newValue=newValue;
        message.messageType=MessageType.TROOP_HP;
        return message;
    }

    public static Message changeHP(String sender,String receiver,String exceptionString){
        Message message=new Message(sender,receiver);
        message.exceptionString=exceptionString;
        message.messageType=MessageType.SEND_EXCEPTION;
        return message;
    }

    public static Message getShop(String sender,String receiver){
        Message message=new Message(sender,receiver);
        message.messageType=MessageType.GET_SHOP;
        return message;
    }

    public static Message getLeaderBoard(String sender,String receiver){
        Message message=new Message(sender,receiver);
        message.messageType=MessageType.GET_LEADERBOARD;
        return message;
    }

    public static Message saveAccount(String sender,String receiver){
        Message message=new Message(sender,receiver);
        message.messageType=MessageType.SAVE_CHANGES;
        return message;
    }

    public static Message createDeck(String sender,String receiver,String deckName){
        Message message=new Message(sender,receiver);
        message.deckName=deckName;
        message.messageType=MessageType.CREATE_DECK;
        return message;
    }

    public static Message removeDeck(String sender,String receiver,String deckName){
        Message message=new Message(sender,receiver);
        message.deckName=deckName;
        message.messageType=MessageType.REMOVE_DECK;
        return message;
    }

    public static Message addCardToDeck(String sender,String receiver,String deckName,String cardId){
        Message message=new Message(sender,receiver);
        message.deckName=deckName;
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.ADD_TO_DECK;
        return message;
    }

    public static Message removeCardFromDeck(String sender,String receiver,String deckName,String cardId){
        Message message=new Message(sender,receiver);
        message.deckName=deckName;
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.messageType=MessageType.REMOVE_FROM_DECK;
        return message;
    }

    public static Message selectDeck(String sender,String receiver,String deckName){
        Message message=new Message(sender,receiver);
        message.deckName=deckName;
        message.messageType=MessageType.SELECT_DECK;
        return message;
    }

    public static Message buyCard(String sender,String receiver,String cardName){
        Message message=new Message(sender,receiver);
        message.cardName=cardName;
        message.messageType=MessageType.BUY_CARD;
        return message;
    }

    public static Message sellCard(String sender,String receiver,String cardName){
        Message message=new Message(sender,receiver);
        message.cardName=cardName;
        message.messageType=MessageType.SELL_CARD;
        return message;
    }

    public static Message endTurn(String sender,String receiver){
        Message message=new Message(sender,receiver);
        message.messageType=MessageType.END_TURN;
        return message;
    }

    public static Message insert(String sender,String receiver,String cardId,Position position){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.position=position;
        message.messageType=MessageType.INSERT;
        return message;
    }

    public static Message attack(String sender,String receiver,String myCardId,String opponentCardId){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[2];
        message.cardIds[0]=myCardId;
        message.cardIds[1]=opponentCardId;
        message.messageType=MessageType.ATTACK;
        return message;
    }

    public static Message comboAttack(String sender,String receiver,String... opponentAndMyCardIds){
        Message message=new Message(sender,receiver);
        message.cardIds=opponentAndMyCardIds;
        message.messageType=MessageType.COMBO;
        return message;
    }

    public static Message useSpell(String sender,String receiver,String cardId,Position position){
        Message message=new Message(sender,receiver);
        message.cardIds=new String[1];
        message.cardIds[0]=cardId;
        message.position=position;
        message.messageType=MessageType.USE_SPELL;
        return message;
    }

    public static Message logIn(String sender,String receiver,String userName,String passWord){
        Message message=new Message(sender,receiver);
        message.userName=userName;
        message.passWord=passWord;
        message.messageType=MessageType.LOG_IN;
        return message;
    }

    public static Message logOut(String sender,String receiver){
        Message message=new Message(sender,receiver);
        message.messageType=MessageType.LOG_OUT;
        return message;
    }
}
