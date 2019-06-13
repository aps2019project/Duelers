package BattleView;

import javafx.application.Application;
import javafx.stage.Stage;
import models.comperessedData.*;
import models.map.Position;

import java.util.ArrayList;

public class Controller extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setScene(new BattleScene(this, generateAGame()).getScene());
        primaryStage.show();
    }

    private CompressedGame generateAGame() {
        final CompressedCell[][] cells = new CompressedCell[5][9];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                cells[j][i] = new CompressedCell(j, i, null, j + i);
            }
        }
        final CompressedPlayer player1 = new CompressedPlayer("Ali", 2, null, null,
                null, null, 1, 0, null, null);
        final CompressedPlayer player2 = new CompressedPlayer("Ali1", 1, null, null,
                null, null, 1, 0, null, null);
        final ArrayList<CompressedTroop> troops = new ArrayList<>();
        final CompressedGameMap map = new CompressedGameMap(cells, troops);
        final CompressedGame game = new CompressedGame(player1, player2, map, 7, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                CompressedCard card = new CompressedCard("neutral_zenrui", null, "a1", null,
                        null, 0, 0, 0, null, 2, true);
                CompressedTroop troop = new CompressedTroop(card, 5, 6, 5, new Position(2, 2),
                        true, true, false, false, 1, 1);
                map.updateTroop(troop);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                card = new CompressedCard("boss_chaosknight", null, "a2", null,
                        null, 0, 0, 0, null, 2, true);
                troop = new CompressedTroop(card, 5, 6, 5, new Position(4, 4),
                        true, true, false, false, 1, 2);
                map.updateTroop(troop);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                troop = new CompressedTroop(card, 5, 6, 5, new Position(4, 8),
                        true, true, false, false, 1, 2);
                map.updateTroop(troop);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                troop = new CompressedTroop(card, 5, 6, 5, new Position(1, 4),
                        true, true, false, false, 1, 2);
                map.updateTroop(troop);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                troop = new CompressedTroop(card, 5, 6, 5, new Position(4, 4),
                        true, true, false, false, 1, 2);
                map.killTroop(card.getCardId());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return game;
    }

    public void sendMessage(String string) {
        System.out.println(string);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
