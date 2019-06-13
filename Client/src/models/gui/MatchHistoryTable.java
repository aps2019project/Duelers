package models.gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.account.MatchHistory;

import java.util.ArrayList;

class MatchHistoryTable extends ScrollPane {
    MatchHistoryTable(ArrayList<MatchHistory> matchHistories) {
        setMinWidth(UIConstants.MATCH_HISTORY_TABLE_WIDTH);
        setPadding(new Insets(UIConstants.DEFAULT_SPACING));
        GridPane labelsGrid = new GridPane();
        labelsGrid.setAlignment(Pos.CENTER);
        labelsGrid.setHgap(UIConstants.DEFAULT_SPACING);
        labelsGrid.setVgap(UIConstants.DEFAULT_SPACING);

        for (int i = 0; i < matchHistories.size(); i++) {
            MatchHistory history = matchHistories.get(i);
            Label opponentLabel = new Label(history.getOppName());
            opponentLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            opponentLabel.setFont(UIConstants.DEFAULT_FONT);
            opponentLabel.setTextFill(Color.BLACK);

            Label dateLabel = new Label(history.getDate());
            dateLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            dateLabel.setFont(UIConstants.DEFAULT_FONT);
            dateLabel.setTextFill(Color.BLACK);

            Label stateLabel = new Label(history.amIWinner() ? "Win" : "Lose");
            stateLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            stateLabel.setFont(UIConstants.DEFAULT_FONT);
            stateLabel.setTextFill(Color.BLACK);

            Button showButton = new Button("SHOW GAME");
            showButton.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            showButton.setFont(UIConstants.DEFAULT_FONT);
            showButton.setTextFill(Color.BLACK);

            labelsGrid.addRow(2 * i,
                    opponentLabel, new DefaultSeparator(Orientation.VERTICAL, 1),
                    dateLabel, new DefaultSeparator(Orientation.VERTICAL, 1),
                    stateLabel, new DefaultSeparator(Orientation.VERTICAL, 1),
                    showButton, new DefaultSeparator(Orientation.VERTICAL, 1)
            );
            labelsGrid.addRow(2 * i + 1, new DefaultSeparator(Orientation.HORIZONTAL, 1));
        }
        setContent(labelsGrid);
    }
}
