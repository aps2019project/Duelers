package models.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;

import java.util.List;

public class LeaderboardScroll extends TableView {
    public LeaderboardScroll(List<LeaderBoardView> leaderboard) {
        super(FXCollections.observableArrayList(leaderboard));

        TableColumn<LeaderBoardView, Integer> index = new TableColumn<>("Rank");
        index.setSortable(false);
        index.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(getItems().indexOf(param.getValue()) + 1)
        );

        TableColumn<LeaderBoardView, String> username = new TableColumn<>("Username");
        username.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<LeaderBoardView, String> wins = new TableColumn<>("Wins");
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<LeaderBoardView, Circle> online = new TableColumn<>("Online");
        online.setCellValueFactory(param -> param.getValue().onlineViewProperty());
        online.setCellFactory(cell -> new CircleTableCell());

        getColumns().addAll(index, username, wins, online);
    }

    private class CircleTableCell extends TableCell<LeaderBoardView, Circle> {
        @Override
        protected void updateItem(Circle item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(item);
            }
        }
    }
}
