package models.message;

import models.account.Account;
import models.card.Card;
import models.card.Deck;
import models.game.Game;
import models.game.Story;
import models.map.Position;

public class Message {
    //TODO:Have ServerMessage & clientMessage

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


}
