package client.view.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameRequestType {
    GAME_INFO("^game info$"),
    SHOW_MY_MINIONS("^show my minions$"),
    SHOW_OPP_MINIONS("^show opponent minions$"),
    SHOW_CARD_INFO("^show card info (\\w+)$"),
    SELECT_CARD("^select (\\w+)$"),
    MOVE("^move Troop to (\\d+) (\\d+)$"),
    ATTACK("^attack (\\w+)$"),
    ATTACK_COMBO("^attack combo (\\w+)(.+)"),
    USE_SPECIAL_POWER("^use special power \\((\\d+), (\\d+)\\)"),
    SHOW_HAND("^show hand$"),
    INSERT_CARD("^inset (\\w+) in \\((\\d+), (\\d+)\\)$"),
    END_TURN("^end turn$"),
    SHOW_COLLECTIBLES("^show collectibles$"),
    SHOW_INFO_OF_ITEM("^show info$"),
    USE_ITEM("^use \\((\\d+), (\\d+)\\)$"),
    SHOW_NEXT_CARD("^show next card$"),
    ENTER_GRAVE_YARD("^enter graveyard"),
    SHOW_INFO_OF_CARD_INGRAVEYARD("^show info (\\w+)$"),
    SHOW_CRADS_IN_GRAVE_YARD("^show cards$"),
    HELP("^help$"),
    EXIT("^exit$"),
    SHOW_MENU_HELP("^show menu$"),
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
