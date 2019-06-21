package view.BattleView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.card.CardType;
import models.comperessedData.*;
import models.game.map.Position;

import java.util.ArrayList;

public class Controller extends Application {
    private final int playerNumber = 2;
    private final int oppPlayerNumber = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(new BattleScene(this, generateAGame(), playerNumber).root, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private CompressedGame generateAGame() {
        final CompressedCell[][] cells = new CompressedCell[5][9];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 9; i++) {
                cells[j][i] = new CompressedCell(j, i, null, j + i);
            }
        }
        final CompressedPlayer player1 = new CompressedPlayer("Ali", 2, new ArrayList<>(), null,
                null, null, 1, 0, null, null);
        final CompressedPlayer player2 = new CompressedPlayer("Ali1", 1, new ArrayList<>(), null,
                null, null, 2, 0, null, null);
        final CompressedPlayer myPlayer;
        if (playerNumber == 1)
            myPlayer = player1;
        else
            myPlayer = player2;
        final ArrayList<CompressedTroop> troops = new ArrayList<>();
        final CompressedGameMap map = new CompressedGameMap(cells, troops);
        final CompressedGame game = new CompressedGame(player1, player2, map, 7, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CompressedCard card = new CompressedCard("generalspell_f5_overload", null, "a1", CardType.SPELL,
                        null, 0, 0, 0, null, 2, true);
                myPlayer.addCardToNext(card);
                game.gameUpdate(10,1,0,2,0);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myPlayer.addNextCardToHand();
                myPlayer.removeCardFromNext();
                game.gameUpdate(11,4,0,4,0);
                card = new CompressedCard("boss_wujin", null, "a1", CardType.MINION,
                        null, 0, 0, 0, null, 2, true);
                CompressedTroop troop = new CompressedTroop(card, 5, 6, 5, new Position(2, 2),
                        true, true, false, false, 1, 1);
                myPlayer.addCardToNext(card);
                map.updateTroop(troop);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myPlayer.addNextCardToHand();
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
