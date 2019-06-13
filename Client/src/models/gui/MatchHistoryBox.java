package models.gui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.account.MatchHistory;

public class MatchHistoryBox extends HBox {

    public MatchHistoryBox(MatchHistory matchHistory) {
        super(UIConstants.DEFAULT_SPACING * 2);

        Label oppLabel = new Label(matchHistory.getOppName());
        oppLabel.setFont(UIConstants.DEFAULT_FONT);

        Label date = new Label(matchHistory.getDate());
        date.setFont(UIConstants.DEFAULT_FONT);


        Label winnerLabel = new Label(
                matchHistory.amIWinner() ? "Win" : "Lose"
        );

        winnerLabel.setFont(UIConstants.DEFAULT_FONT);

        getChildren().addAll(oppLabel, date, winnerLabel);
    }
}
