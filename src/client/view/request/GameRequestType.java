package client.view.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameRequestType {
    GAME_INFO("^game info$"),
    SHOW_MY_MINIONS("^show my minions$"),
    SHOW_OPP_MINIONS("^show opponent minions$"),
    SHOW_CARD_INFO("^show card info (\\w+)$"),
    SELECT_CARD("^select (\\w+)$"),
    MOVE("^move to |(\\d+)| |(\\d+)|$"),
    ATTACK("^attack (\\w+)$"),
    ATTACK_COMBO("^attack combo (\\w+)( +(\\w+))+"),
    USE_SPECIAL_POWER("^use special power \\((\\d+), (\\d+)\\)"),
    SHOW_HAND("^show hand$"),
    INSERT_CARD("^inset (\\w+) in \\((\\d+), (\\d+)\\)$"),
    END_TURN("^end turn$"),

    ;
    private Pattern pattern;
    private Matcher matcher;

    GameRequestType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public Matcher setMatcher(String command) {
        this.matcher = pattern.matcher(command);
        return matcher;
    }

    public Matcher getMatcher() {
        return matcher;
    }
}
