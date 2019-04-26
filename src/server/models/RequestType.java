package server.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RequestType {
    SHOW_ACCOUNTS("sudo show accounts");
    Pattern pattern;
    Matcher matcher;

    RequestType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public boolean maches(String command) {
        this.matcher = pattern.matcher(command);
        return matcher.find();
    }

    public Matcher getMatcher() {
        return matcher;
    }

}
