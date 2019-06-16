package models.gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.account.MatchHistory;

import java.util.ArrayList;

class MatchHistoryTable extends ScrollPane {
    private static final Insets PADDING = new Insets(UIConstants.DEFAULT_SPACING * 2);
    private static final String ID = "profile_scroll";
    private static final double SEPARATOR_OPACITY = 0.3;
    private static final Background BACKGROUND = new Background(
            new BackgroundFill(
                    Color.rgb(57, 63, 74, 0.5),
                    new CornerRadii(UIConstants.PROFILE_PIC_CORNER_RADIUS), Insets.EMPTY
            )
    );

    MatchHistoryTable(ArrayList<MatchHistory> matchHistories) {
        setMinWidth(UIConstants.MATCH_HISTORY_TABLE_WIDTH * 1.03);
        setId(ID);
        GridPane labelsGrid = new GridPane();
        labelsGrid.setMinSize(UIConstants.MATCH_HISTORY_TABLE_WIDTH, UIConstants.MATCH_HISTORY_TABLE_HEIGHT);
        labelsGrid.setPadding(PADDING);
        labelsGrid.setBackground(BACKGROUND);
        labelsGrid.setAlignment(Pos.CENTER);
        labelsGrid.setHgap(UIConstants.DEFAULT_SPACING);
        labelsGrid.setVgap(UIConstants.DEFAULT_SPACING);

        for (int i = 0; i < matchHistories.size(); i++) {
            MatchHistory history = matchHistories.get(i);
            Label opponentLabel = new Label(history.getOppName());
            opponentLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            opponentLabel.setFont(UIConstants.DEFAULT_FONT);
            opponentLabel.setTextFill(Color.WHITE);

            Label dateLabel = new Label(history.getDate());
            dateLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            dateLabel.setFont(UIConstants.DEFAULT_FONT);
            dateLabel.setTextFill(Color.WHITE);

            Label stateLabel = new Label(history.amIWinner() ? "Win" : "Lose");
            stateLabel.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            stateLabel.setFont(UIConstants.DEFAULT_FONT);
            stateLabel.setTextFill(Color.WHITE);

            Button showButton = new Button("SHOW GAME");
            showButton.setPadding(new Insets(UIConstants.DEFAULT_SPACING));
            showButton.setFont(UIConstants.DEFAULT_FONT);
            showButton.setTextFill(Color.BLACK);

            labelsGrid.addRow(2 * i,
                    opponentLabel, new DefaultSeparator(Orientation.VERTICAL, SEPARATOR_OPACITY),
                    dateLabel, new DefaultSeparator(Orientation.VERTICAL, SEPARATOR_OPACITY),
                    stateLabel, new DefaultSeparator(Orientation.VERTICAL, SEPARATOR_OPACITY),
                    showButton
            );
            if (i < matchHistories.size() - 1) {
                labelsGrid.add(
                        new DefaultSeparator(Orientation.HORIZONTAL, SEPARATOR_OPACITY),
                        0, 2 * i + 1, 7, 1
                );
            }
        }
        setContent(labelsGrid);
    }
}
