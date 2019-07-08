package models.gui;

import javafx.beans.property.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import models.account.AccountInfo;

import java.util.HashMap;
import java.util.Map;

import static models.gui.UIConstants.SCALE;

public class LeaderBoardView {
    private static final Map<Boolean, Color> colors = new HashMap<>();
    private static final double circleRadius = 15 * SCALE;

    static {
        colors.put(true, Color.rgb(0, 234, 0));
        colors.put(false, Color.rgb(255, 0, 0));
    }

    private final String username;
    private final int wins;
    private final ObjectProperty<Circle> onlineView;

    public LeaderBoardView(AccountInfo accountInfo) {
        username = accountInfo.getUsername();
        wins = accountInfo.getWins();
        onlineView = new SimpleObjectProperty<>(new Circle(circleRadius, colors.get(accountInfo.isOnline())));
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }

    public Circle getOnlineView() {
        return onlineView.get();
    }

    ObjectProperty<Circle> onlineViewProperty() {
        return onlineView;
    }
}
