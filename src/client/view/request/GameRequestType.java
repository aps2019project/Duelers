package client.view.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameRequestType {
    GAME_INFO("^game info$"),
    SHOW_MY_MINIONS("^show my minions$"),
    SHOW_OPP_MINIONS("^show opponent minions$"),
    SHOW_CARD_INFO("^show card info (\\w+)$"),
    SELECT_CARD("^select (\\w+)$"),
    MOVE("^move to \\((\\d+), (\\d+)\\)$"),
    ATTACK("^attack (\\w+)$"),
    ATTACK_COMBO("^attack combo (\\w+)(.+)"),
    USE_SPECIAL_POWER("^use special power \\((\\d+), (\\d+)\\)"),
    SHOW_HAND("^show hand$"),
    SHOW_NEXT_CARD("^show next card$"),
    INSERT_CARD("^insert (\\w+) in \\((\\d+), (\\d+)\\)$"),
    SHOW_COLLECTIBLES("^show collectibles$"),
    SHOW_INFO_OF_ITEM("^show info$"),
    USE_ITEM("^use \\((\\d+), (\\d+)\\)$"),
    ENTER_GRAVE_YARD("^enter graveyard"),
    END_TURN("^end turn$"),
    HELP("^help$"),
    SHOW_MENU_HELP("^show menu$"),
    EXIT("^exit$"),
    SHOW_INFO_OF_CARD_IN_GRAVEYARD("^show info (\\w+)$"),
    SHOW_CARDS_IN_GRAVE_YARD("^show cards$"),
    END_GAME("^end game$");
    private Pattern pattern;
    private Matcher matcher;

    GameRequestType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public boolean matches(String command) {
        this.matcher = pattern.matcher(command);
        return this.matcher.find();
    }

    public Matcher getMatcher() {
        return matcher;
    }
}
