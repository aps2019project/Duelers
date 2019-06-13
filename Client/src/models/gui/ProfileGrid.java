package models.gui;

import javafx.scene.layout.GridPane;
import models.account.Account;

import java.io.FileNotFoundException;

public class ProfileGrid extends GridPane {
    private static final String DEFAULT_PROFILE_PIC_URL = "resources/ui/default_profile.jpg";
    private static final String GENERAL_ICON_URL = "resources/ui/icon_general.png";
    private static final String USERNAME_TEXT = "Username:";
    private static final String GOLD_ICON_URL = "resources/ui/icon_gold.png";
    private static final String MONEY_TEXT = "Your Money:";
    private static final String HISTORY_ICON_URL = "resources/ui/icon_history.png";
    private static final String MATCH_HISTORY_TEXT = "Match Histories:";

    public ProfileGrid(Account account) throws FileNotFoundException {
        setHgap(UIConstants.DEFAULT_SPACING * 4);
        setVgap(UIConstants.DEFAULT_SPACING * 4);

        add(new ProfilePicture(DEFAULT_PROFILE_PIC_URL), 0, 0, 3, 3);

        add(ImageLoader.loadImage(GENERAL_ICON_URL, UIConstants.PROFILE_ICON_SIZE, UIConstants.PROFILE_ICON_SIZE), 0, 3);
        add(new DialogText(USERNAME_TEXT), 1, 3);
        add(new DialogText(account.getUsername()), 2, 3);

        add(ImageLoader.loadImage(GOLD_ICON_URL, UIConstants.PROFILE_ICON_SIZE, UIConstants.PROFILE_ICON_SIZE), 0, 4);
        add(new DialogText(MONEY_TEXT), 1, 4);
        add(new DialogText(account.getMoney() + "$"), 2, 4);

        add(ImageLoader.loadImage(HISTORY_ICON_URL, UIConstants.PROFILE_ICON_SIZE, UIConstants.PROFILE_ICON_SIZE), 3, 0);
        add(new DialogText(MATCH_HISTORY_TEXT), 4, 0);

        add(new MatchHistoryTable(account.getMatchHistories()), 3, 1, 3, 4);
    }
}
